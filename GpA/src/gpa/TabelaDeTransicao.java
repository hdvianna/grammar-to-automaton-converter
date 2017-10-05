/*
 * TabelaDeTransicao.java
 *
 * Created on October 26, 2005, 10:39 AM
 */

package gpa;

/**
 *
 * @author Henrique
 */
public class TabelaDeTransicao {
    
    private java.util.Vector producoes;
    private java.util.Vector producoesTabela;
    
    private java.util.Vector simbolos;
    private java.util.Vector estados;    
    private String estadoInicial;
    private java.util.Vector[][] transicoes;
    
    /** Creates a new instance of TabelaDeTransicao */
    public TabelaDeTransicao(java.util.Vector _producoes, String _estadoInicial) {
        producoes = _producoes;
        estadoInicial = _estadoInicial;
        
        simbolos = new java.util.Vector();
        mkSimbolos();
        
        producoesTabela = new java.util.Vector();
        estados = new java.util.Vector();         
        mkEstados();
        
        transicoes = new java.util.Vector[estados.size()][simbolos.size()];
        mkTransicoes();
        
    }
    
    private void mkSimbolos() {
        int i,j;        
        
        Producao producao;        
        Elemento elemento;
        
        for (i=0; i < producoes.size();i++) {
            producao = (Producao) producoes.get(i);
            for (j=0;j<producao.size();j++) {
                elemento = (Elemento) producao.get(j);
                if (!simbolos.contains(elemento.getTerminal())) 
                    simbolos.add(elemento.getTerminal());
            }
        }         
        
    }
    
    private void mkEstados() {
        
        int i,j;
       
        java.util.Vector tmpProducoes;
        
        Producao producao; 
        Elemento elemento;
        
        tmpProducoes = (java.util.Vector) producoes.clone();
        
        for (i=0; i < tmpProducoes.size();i++) {           
            producao = (Producao) tmpProducoes.get(i);
            if (producao.getProduto().equals(estadoInicial)) {                 
                producoesTabela.add(producao);
                estados.add(producao.getProduto());
                tmpProducoes.remove(i);
                break;
            }
        }
        
        for (i=0; i < producoes.size();i++) {
            producao = (Producao) producoes.get(i);
            for (j=0;j<producao.size();j++) {
                elemento = (Elemento) producao.get(j);
                if (!estados.contains(elemento.getNTerminal()) && (elemento.getNTerminal() != "£")) 
                    estados.add(elemento.getNTerminal());
            }
        }  
        
        for (i=0; i < tmpProducoes.size();i++) {
            producao = (Producao) tmpProducoes.get(i);
            producoesTabela.add(producao);            
            if (!estados.contains(producao.getProduto())) 
                estados.add(producao.getProduto());
        }
         
    }
    
    private void mkTransicoes() {
        int i, j;
        Producao producao;
        Elemento elemento;
        for (i=0;i<producoesTabela.size();i++) {
            producao = (Producao) producoesTabela.get(i);
            for (j=0;j<producao.size();j++) {
                elemento = (Elemento) producao.get(j);                
                if (transicoes[i][simbolos.indexOf(elemento.getTerminal())] == null)
                    transicoes[i][simbolos.indexOf(elemento.getTerminal())] = new java.util.Vector();
                if (elemento.getNTerminal() != "£")
                    transicoes[i][simbolos.indexOf(elemento.getTerminal())].add(elemento.getNTerminal());
                else 
                    transicoes[i][simbolos.indexOf(elemento.getTerminal())].add("#");                
                
            }
        }        
    }
    
    public java.util.Vector[][] getTransicoes() {
        return transicoes;
    }
    
    public void setTransicoes(java.util.Vector[][] _transicoes) {
        transicoes = _transicoes; 
    }
    
    public java.util.Vector getSimbolos() {
        return simbolos;
    }
    
    public java.util.Vector getEstados() {
        return estados;
    }
    
    public java.util.Vector getProducoes() {
        return producoes;
    }
    
    public String getEstadoInicial() {
         return estadoInicial;
    }
    
}
