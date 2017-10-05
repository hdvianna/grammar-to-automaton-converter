/*
 * Estado.java
 *
 * Created on October 29, 2005, 12:19 PM
 */

package gpa;

/**
 *
 * @author Henrique
 */
public class Estado extends Object{
    
    String descricao;
    java.util.Vector estados;
    
    /** Creates a new instance of Estado */
    public Estado(String _descricao, java.util.Vector _estados) {
        descricao = _descricao;
        estados = _estados;
    }
    
    public String toString() {
        return descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public java.util.Vector getEstados() {
        return estados;
    }   
    
}
