/*
 * Elemento.java
 *
 * Created on October 19, 2005, 8:56 PM
 */

package gpa;

/**
 *
 * @author Igor
 */
public class Elemento {
    
    String terminal;
    String nterminal;
    
    /** Creates a new instance of Elemento */
    public Elemento(String _terminal, String _nterminal) {
        terminal = _terminal;
        nterminal = _nterminal;
    }
    
    public void setTerminal(String _terminal) {
        terminal = _terminal;
    }    
    
    public String getTerminal() {
        return terminal;
    }
    
    public void setNTerminal(String _nterminal) {        
        nterminal = _nterminal;
    }
    
    public String getNTerminal() {
        return nterminal;
    }
    
    public String exibe() {                    
        return terminal.concat(nterminal.equals("£")?"":nterminal);
    }
    
    
}