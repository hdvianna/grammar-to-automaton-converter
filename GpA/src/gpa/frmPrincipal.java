/*
 * frmPrincipal.java
 *
 * Created on October 17, 2005, 7:04 PM
 */

package gpa;

/**
 *
 * @author  Igor
 */
public class frmPrincipal extends javax.swing.JFrame {
    
    private frmNTerminais janelaNTerminais;
    private frmTerminais janelaTerminais;
    private frmProducoes janelaProducoes;
    private frmTabelaTransicao janelaTabelaTransicao;
    private frmReconheceSentenca janelaReconheceSentenca;
    
    /** Creates new form frmPrincipal */
    public frmPrincipal() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        btnTabelaTransicao = new javax.swing.JButton();
        btnProducoes = new javax.swing.JButton();
        btnTerminais = new javax.swing.JButton();
        btnNTerminais = new javax.swing.JButton();
        btnTabelaTransicao1 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GpA - Gram\u00e1tica para Aut\u00f4mato");
        btnTabelaTransicao.setFont(new java.awt.Font("Arial", 0, 10));
        btnTabelaTransicao.setText("Tabela de Transi\u00e7\u00e3o");
        btnTabelaTransicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabelaTransicaoActionPerformed(evt);
            }
        });

        getContentPane().add(btnTabelaTransicao);
        btnTabelaTransicao.setBounds(190, 20, 160, 23);

        btnProducoes.setFont(new java.awt.Font("Arial", 0, 10));
        btnProducoes.setText("Adicionar Produ\u00e7\u00f5es");
        btnProducoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProducoesActionPerformed(evt);
            }
        });

        getContentPane().add(btnProducoes);
        btnProducoes.setBounds(20, 80, 160, 23);

        btnTerminais.setFont(new java.awt.Font("Arial", 0, 10));
        btnTerminais.setText("Adicionar Terminais");
        btnTerminais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminaisActionPerformed(evt);
            }
        });

        getContentPane().add(btnTerminais);
        btnTerminais.setBounds(20, 50, 160, 23);

        btnNTerminais.setFont(new java.awt.Font("Arial", 0, 10));
        btnNTerminais.setText("Adicionar N\u00e3o Terminais");
        btnNTerminais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNTerminaisActionPerformed(evt);
            }
        });

        getContentPane().add(btnNTerminais);
        btnNTerminais.setBounds(20, 20, 160, 23);

        btnTabelaTransicao1.setFont(new java.awt.Font("Arial", 0, 10));
        btnTabelaTransicao1.setText("Reconhece Senten\u00e7a");
        btnTabelaTransicao1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabelaTransicao1ActionPerformed(evt);
            }
        });

        getContentPane().add(btnTabelaTransicao1);
        btnTabelaTransicao1.setBounds(190, 50, 160, 23);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-377)/2, (screenSize.height-145)/2, 377, 145);
    }//GEN-END:initComponents

    private void btnTabelaTransicao1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabelaTransicao1ActionPerformed
        // TODO add your handling code here:
        janelaReconheceSentenca.setTabela(janelaTabelaTransicao.getTabela());
        janelaReconheceSentenca.setAutomato(janelaTabelaTransicao.getAutomato());
        janelaReconheceSentenca.createTable();
        janelaReconheceSentenca.show();
    }//GEN-LAST:event_btnTabelaTransicao1ActionPerformed

    private void btnTabelaTransicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabelaTransicaoActionPerformed
        // TODO add your handling code here:
        janelaTabelaTransicao.setProducoes(janelaProducoes.getProducoes());        
        janelaTabelaTransicao.show();
    }//GEN-LAST:event_btnTabelaTransicaoActionPerformed

    private void btnProducoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProducoesActionPerformed
        // TODO add your handling code here:
        janelaProducoes.setVetTerminais(janelaTerminais.getVetTerminais());        
        janelaProducoes.setVetNTerminais(janelaNTerminais.getVetNTerminais());        
        janelaProducoes.show();
    }//GEN-LAST:event_btnProducoesActionPerformed

    private void btnTerminaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminaisActionPerformed
        // TODO add your handling code here:
        janelaTerminais.show();
    }//GEN-LAST:event_btnTerminaisActionPerformed

    private void btnNTerminaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNTerminaisActionPerformed
        // TODO add your handling code here:        
        janelaNTerminais.show();
        
    }//GEN-LAST:event_btnNTerminaisActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }
    
    public void setJanelaNTerminais(frmNTerminais janela) {
        janelaNTerminais = janela;
    }
    
    public void setJanelaTerminais(frmTerminais janela) {
        janelaTerminais = janela;
    }
    
    public void setJanelaProducoes(frmProducoes janela) {
        janelaProducoes = janela;
    }
    
    public void setJanelaTabelaTransicao(frmTabelaTransicao janela) {
        janelaTabelaTransicao = janela;
    }
    
    public void setJanelaReconheceSentenca(frmReconheceSentenca janela) {
        janelaReconheceSentenca = janela;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNTerminais;
    private javax.swing.JButton btnProducoes;
    private javax.swing.JButton btnTabelaTransicao;
    private javax.swing.JButton btnTabelaTransicao1;
    private javax.swing.JButton btnTerminais;
    // End of variables declaration//GEN-END:variables
    
}