/*
 * frmReconheceSentenca.java
 *
 * Created on November 19, 2005, 7:18 PM
 */

package gpa;
import java.util.*;
import java.awt.*;
/**
 *
 * @author  Henrique
 */
public class frmReconheceSentenca extends javax.swing.JFrame {
    
    private Automato automato;
    private TabelaDeTransicao tabela;
    private java.util.Vector[][] transicoes; 
    
    /** Creates new form frmReconheceSentenca */
    public frmReconheceSentenca() {
        initComponents();        
        //tblTransicoes.setModel(new javax.swing.table.DefaultTableModel(0,0));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        scrollTabela = new javax.swing.JScrollPane();
        tblTransicoes = new javax.swing.JTable();
        btnMinimizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblTipo = new javax.swing.JLabel();
        txtSentenca = new javax.swing.JTextField();
        btnReconhecer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();

        setTitle("Reconhecer Senten\u00e7as");
        tblTransicoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollTabela.setViewportView(tblTransicoes);

        getContentPane().add(scrollTabela, java.awt.BorderLayout.CENTER);

        btnMinimizar.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        btnMinimizar.setText("Minimizar");
        getContentPane().add(btnMinimizar, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(null);

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 65));
        lblTipo.setFont(new java.awt.Font("Arial", 1, 11));
        lblTipo.setForeground(new java.awt.Color(255, 51, 51));
        jPanel1.add(lblTipo);
        lblTipo.setBounds(350, 7, 30, 20);

        txtSentenca.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        txtSentenca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSentencaActionPerformed(evt);
            }
        });
        txtSentenca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSentencaKeyTyped(evt);
            }
        });

        jPanel1.add(txtSentenca);
        txtSentenca.setBounds(10, 30, 250, 20);

        btnReconhecer.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        btnReconhecer.setText("Reconhecer");
        btnReconhecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReconhecerActionPerformed(evt);
            }
        });

        jPanel1.add(btnReconhecer);
        btnReconhecer.setBounds(270, 30, 100, 23);

        jLabel1.setFont(new java.awt.Font("MS Sans Serif", 0, 10));
        jLabel1.setText("Senten\u00e7a:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 120, 14);

        lblResultado.setFont(new java.awt.Font("MS Sans Serif", 1, 11));
        lblResultado.setForeground(new java.awt.Color(0, 0, 204));
        lblResultado.setText("  ");
        jPanel1.add(lblResultado);
        lblResultado.setBounds(270, 10, 100, 15);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-421)/2, (screenSize.height-346)/2, 421, 346);
    }//GEN-END:initComponents

    private void txtSentencaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSentencaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSentencaActionPerformed

    private void txtSentencaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSentencaKeyTyped
        // TODO add your handling code here:
        String chr = String.valueOf(evt.getKeyChar());
        Vector simbolos = tabela.getSimbolos();
        if (simbolos.contains(chr)) {
            if (txtSentenca.getText().length() < 1)
                evt.setKeyChar(chr.charAt(0));
        } else {
            if (evt.getKeyText(evt.getKeyChar()) != "Backspace")            
                evt.setKeyChar('\0');
        }
    }//GEN-LAST:event_txtSentencaKeyTyped

    private void btnReconhecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReconhecerActionPerformed
        // TODO add your handling code here:
        Reconhece reconhece = new Reconhece(500);
        reconhece.start();
    }//GEN-LAST:event_btnReconhecerActionPerformed
    
    private class Reconhece extends Thread {
        long tempo;
        
        public Reconhece (long _tempo) {
            tempo = _tempo;
        }
        
        public void run() {            
            boolean reconhece = true;
            int i = automato.indiceNovoEstadoInicial(tabela.getEstadoInicial()), j, k;
            String sentenca = txtSentenca.getText(), atm = "", estado = "";

            String [][] vetTrans = automato.getVetTrans();

            for (k=0;k<sentenca.length();k++) {            
                atm = sentenca.substring(k, (k+1));            
                j = tabela.getSimbolos().indexOf(atm);
                estado = vetTrans[i][j];
                
                /*MUSGUICE*/
                txtSentenca.select(k, (k+1));
                txtSentenca.grabFocus();
                tblTransicoes.setValueAt("("+ vetTrans[i][j]+ ")", i,(j+1));
                try {
                    sleep(tempo);
                } catch (Exception e) {
                    System.out.println(e);
                }                        
                tblTransicoes.setValueAt(vetTrans[i][j], i,(j+1));
                /*FIM DA MUSGUICE*/
                
                if (estado.length() > 0) 
                    i = automato.getEstados().indexOf(estado);                    
                else  {
                    reconhece = false;
                    break;
                }
            }            
            
            if (reconhece && (estado.indexOf("#") > -1))  {
                lblResultado.setForeground(new Color((float)0.0,(float)0.6,(float)0.0));
                lblResultado.setText("RECONHECIDO");
            } else {
                lblResultado.setForeground(Color.RED);
                lblResultado.setText("� RECONHECIDO");
            }
            
        }
        
    }
    
    public void setAutomato (Automato _automato) {
        automato = _automato;
    }
    
    public void setTabela (TabelaDeTransicao _tabela) {
        tabela = _tabela;
    }
    
    public void createTable () {
        String vetTrans[][] = automato.getVetTrans();//automato.getTransicoesEmString();
        criaJTabelaD(tabela.getSimbolos(), automato.getEstados(), vetTrans);        
 
        
    }
    
    private void criaJTabelaD(java.util.Vector _simbolos, java.util.Vector _estados, String[][] vetTrans) {
        int i,j;
        int colunas = (_estados.size() * _simbolos.size()) + 1;
        int linhas = _estados.size() + 1;
        //String[][] strtabela = new String[linhas][colunas];
        Color[][] strtabela = new Color[linhas][colunas];
        
        
        String[] header = new String[_simbolos.size() + 1];
        header[0] = "";
        for (i=1; i<=_simbolos.size();i++) {
            header[i] = (String) _simbolos.get(i-1);
        }
        
        tblTransicoes.setModel(new javax.swing.table.DefaultTableModel(
           strtabela, header           
        ));
        
        transicoes = tabela.getTransicoes();
        
        for (i=0;i<_estados.size();i++) {
            tblTransicoes.setValueAt(_estados.get(i) ,i,0);
        }
        
        for (i=0;i < vetTrans.length;i++){
            for (j=0;j < vetTrans[i].length;j++){
                tblTransicoes.setValueAt(vetTrans[i][j],i,j+1);
            }
        }                
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmReconheceSentenca().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMinimizar;
    private javax.swing.JButton btnReconhecer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JScrollPane scrollTabela;
    private javax.swing.JTable tblTransicoes;
    private javax.swing.JTextField txtSentenca;
    // End of variables declaration//GEN-END:variables
    
}