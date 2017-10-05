/*
 * Producao.java
 *
 * Created on October 19, 2005, 8:56 PM
 */

package gpa;

/**
 *
 * @author Igor
 */
public class Producao extends java.util.Vector {
    
    String produto;
    
    /** Creates a new instance of Producao */
    public Producao(String _produto) {
        super();
        produto = _produto;
    }
    
    public void add(Elemento elem) {
        if (!contains(elem))
            super.add(elem);        
    }
    
    public String getProduto() {
        return produto;       
    }
    
    public String toString() {
        int i;
        String producao = "";
        for (i=0;i < size(); i++) {
            Elemento elem = (Elemento) get(i);
            if (producao.length() == 0)
                producao = produto + "->" + elem.exibe();
            else
                producao = producao + " | " + elem.exibe();            
        }
        
        return producao;
    }
    
    public boolean temElemento(Elemento _elem) {
        int i;
        
        for (i=0;i < size(); i++) {
            Elemento elem = (Elemento) get(i);
            if (elem.exibe().equals(_elem.exibe()))
                return true;                
        }
        
        return false;
    }
}
