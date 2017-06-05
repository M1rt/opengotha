/*
 * JFrToolsMemory.java
 *
 * Created on 9 mars 2012, 17:28:27
 */
package info.vannier.gotha;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Luc Vannier
 */
public class JFrToolsMemory extends javax.swing.JFrame {
    private static final long REFRESH_DELAY = 500;

    /** Creates new form JFrToolsMemory */
    public JFrToolsMemory() {
        initComponents();
        customInitComponents();
        setupRefreshTimer();
    }

    
    private volatile boolean running = true;
    javax.swing.Timer timer = null;
    private void setupRefreshTimer() {
        ActionListener taskPerformer;
        taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (!running){
                    timer.stop();
                }
                updateComponents();
            }
        };
        timer = new javax.swing.Timer((int) REFRESH_DELAY, taskPerformer);
        timer.start();
    }

    private void customInitComponents(){
        updateComponents();
    }
    
    private void updateComponents(){
        long maxM   = Runtime.getRuntime().maxMemory();
        long freeM  = Runtime.getRuntime().freeMemory();
        long totalM = Runtime.getRuntime().totalMemory();
        long usedM  = totalM - freeM;
        
        this.txfMaxMem.setText("" + maxM/1024/1024 + " GiB");
        this.txfUsedMem.setText("" + usedM/1024/1024 + " GiB");
        
        if (usedM * 100 /maxM > 80) this.txfUsedMem.setBackground(Color.red);
        else this.txfUsedMem.setBackground(Color.white);
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txfMaxMem = new javax.swing.JTextField();
        txfUsedMem = new javax.swing.JTextField();
        btnRunGB = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Memory Manager");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setText("Max Memory : ");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(70, 30, 120, 14);

        jLabel2.setText("Used Memory : ");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(70, 60, 120, 14);

        txfMaxMem.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        getContentPane().add(txfMaxMem);
        txfMaxMem.setBounds(210, 30, 90, 20);

        txfUsedMem.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        getContentPane().add(txfUsedMem);
        txfUsedMem.setBounds(210, 60, 90, 20);

        btnRunGB.setText("Run Garbage Collector");
        btnRunGB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunGBActionPerformed(evt);
            }
        });
        getContentPane().add(btnRunGB);
        btnRunGB.setBounds(70, 110, 240, 23);

        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        getContentPane().add(btnClose);
        btnClose.setBounds(20, 160, 330, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        cleanClose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void cleanClose(){
        running = false;
        dispose();
    }

    private void btnRunGBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunGBActionPerformed
        Runtime.getRuntime().gc();
        this.updateComponents();
    }//GEN-LAST:event_btnRunGBActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        cleanClose();
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JFrToolsMemory().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnRunGB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txfMaxMem;
    private javax.swing.JTextField txfUsedMem;
    // End of variables declaration//GEN-END:variables
}
