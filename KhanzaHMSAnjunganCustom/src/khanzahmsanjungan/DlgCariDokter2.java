/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */
package khanzahmsanjungan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgCariDokter2 extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgCariDokter2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(656, 250);

        Object[] row = {"Kode Dokter", "Nama Dokter", "Jam Praktek"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(150);
            } else if (i == 1) {
                column.setPreferredWidth(500);
            } else if (i == 2) {
                column.setPreferredWidth(300);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Unit/Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if (tabMode.getRowCount() != 0) {
            if (evt.getClickCount() == 1) {
                dispose();
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed

    }//GEN-LAST:event_tbKamarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariDokter2 dialog = new DlgCariDokter2(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.ScrollPane Scroll;
    private widget.InternalFrame internalFrame1;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil(String harikerja, String kodepoli) {
        Valid.tabelKosong(tabMode);
        
        String query = "select dokter.kd_dokter, dokter.nm_dokter, concat(jadwal.jam_mulai, ' - ', jadwal.jam_selesai) as jam_kerja from dokter join jadwal on dokter.kd_dokter = jadwal.kd_dokter where jadwal.hari_kerja = ? and jadwal.kd_poli = ?";
        try {
            ps = koneksi.prepareStatement(query);
            ps.setString(1, harikerja);
            ps.setString(2, kodepoli);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                tabMode.addRow(new Object[]{
                    rs.getString("kd_dokter"),
                    rs.getString("nm_dokter"),
                    rs.getString("jam_kerja")
                });
            }
            
            rs.close();
            
            ps.close();
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }

    public void emptTeks() {

    }

    public JTable getTable() {
        return tbKamar;
    }

    public void isCek() {

    }

    private void tampil2() {

    }
}
