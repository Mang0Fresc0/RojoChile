/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rojochile;

import java.applet.AudioClip;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gusal
 */
public class MenuMuerte extends javax.swing.JFrame {

    /**
     * Creates new form TeMoriste
     */
    public MenuMuerte() throws IOException {
        initComponents();
        //Musica.setIcon(new javax.swing.ImageIcon("Resources/titulos/bocina2.jpg"));
        Fondo.setIcon(new javax.swing.ImageIcon("Resources/tiles/space.jpg"));
        jLabel2.setIcon(new javax.swing.ImageIcon("Resources/titulos/Moriste.png"));
        Salir.setIcon(new javax.swing.ImageIcon("Resources/titulos/Salir.png"));
        //VolveralMenu.setIcon(new javax.swing.ImageIcon("Resources/titulos/VolveralMenu.png"));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        Salir = new javax.swing.JLabel();
        VolveralMenu = new javax.swing.JLabel();
        Musica = new javax.swing.JLabel();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, -1, -1));

        Salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalirMouseClicked(evt);
            }
        });
        getContentPane().add(Salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 370, 110));

        VolveralMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VolveralMenuMouseClicked(evt);
            }
        });
        getContentPane().add(VolveralMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 610, 110));

        Musica.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MusicaMouseClicked(evt);
            }
        });
        getContentPane().add(Musica, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 260, 170));

        Fondo.setForeground(new java.awt.Color(51, 51, 51));
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -20, 1030, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalirMouseClicked
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_SalirMouseClicked

    private void VolveralMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VolveralMenuMouseClicked
        
    }//GEN-LAST:event_VolveralMenuMouseClicked

    private void MusicaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MusicaMouseClicked
    AudioClip MusiFondo;
    MusiFondo = java.applet.Applet.newAudioClip(getClass().getResource("/rojochile/Nokia.wav"));
    MusiFondo.play();
    }//GEN-LAST:event_MusicaMouseClicked

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuMuerte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuMuerte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuMuerte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuMuerte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MenuMuerte().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MenuMuerte.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel Musica;
    private javax.swing.JLabel Salir;
    private javax.swing.JLabel VolveralMenu;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
