/*
 * Main.java
 *
 * Created on October 16, 2005, 5:23 PM
 */

package gpa;

/**
 *
 * @author Igor
 */
public class Main {
      
    /** Creates a new instance of Main */
    public Main() {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        frmPrincipal janelaPrincipal = new frmPrincipal();
        frmNTerminais janelaNTerminais = new frmNTerminais();
        frmTerminais janelaTerminais = new frmTerminais();
        frmProducoes janelaProducoes = new frmProducoes();
        frmTabelaTransicao janelaTabelaTransicao = new frmTabelaTransicao();
        janelaPrincipal.setJanelaNTerminais(janelaNTerminais);
        janelaPrincipal.setJanelaTerminais(janelaTerminais);
        janelaPrincipal.setJanelaProducoes(janelaProducoes);
        janelaPrincipal.setJanelaTabelaTransicao(janelaTabelaTransicao);
        janelaPrincipal.setJanelaReconheceSentenca(new frmReconheceSentenca());
        janelaPrincipal.show();
    }
    
}
