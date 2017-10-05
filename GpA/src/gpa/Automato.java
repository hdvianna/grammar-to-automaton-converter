/*
 * Automato.java
 *
 * Created on October 26, 2005, 4:46 PM
 */

package gpa;

import java.util.*;

/**
 *
 * @author Henrique
 */
public class Automato {
    
    private TabelaDeTransicao tabela;
    private String[][] vetTrans;
    private String tipo = "";
    
    private Vector[] visitados;
    private Vector inacessiveis;
    private Vector mortos;
    
    /*Vetores para a classes de equivalência*/
    private Vector finais;
    private Vector outros;
    private Vector classes;
    
    private Vector estadosDeterminizados;    
    private Vector estadosEquivalentes;
    
    private Hashtable tabelaEstados = new Hashtable();    
    
    /** Creates a new instance of Automato */
    public Automato(TabelaDeTransicao _tabela) {
        tabela = _tabela;
        estadosDeterminizados = (Vector) _tabela.getEstados().clone();
    }
    
    public String getTipo() {
        int i,j;        
        
        if (tipo.length() == 0) {
            tipo = "AFD";
            Vector[][] transicoes = tabela.getTransicoes();
            for (i=0; i< transicoes.length;i++) {
                for (j=0; j < transicoes[i].length;j++){
                    if ((transicoes[i][j] != null) && (transicoes[i][j].size() > 1)) 
                        tipo = "AFND";                
                }
            }            
        }
        
        return tipo;
        
    }
    
    public Vector getEstados() {
        return estadosDeterminizados;
    }
    
    public String[][] determiniza() {
        int i,j;
        Vector estados = tabela.getEstados();
        Vector simbolos = tabela.getSimbolos();        
        
        Vector[][] transicoes = tabela.getTransicoes();
        
        estados.add("#");
        
        estadosDeterminizados = (Vector) estados.clone();
        iniciaTabelaEstados(estados);
        
        vetTrans = new String[estados.size()][simbolos.size()];
        
        for (i=0;i<transicoes.length;i++) {
            for (j=0;j<transicoes[i].length;j++) {                
                vetTrans[i][j] = getTransicao(transicoes[i][j]);               
                //cria a tabela de estados, usada para conhecer a 
                //formação dos estados
                if (!tabelaEstados.containsKey(vetTrans[i][j]) && (transicoes[i][j] != null))
                    tabelaEstados.put(vetTrans[i][j], transicoes[i][j]);                
            }
        }
        for (i=0;i<simbolos.size();i++) {
            vetTrans[estados.size()-1][i] = "";
        }
        
        determinizaVetor(vetTrans, estadosDeterminizados, simbolos);
        tipo = "AFD";
        return vetTrans;
        
    }
    
    public void iniciaTabelaEstados(Vector _estados) {
        Iterator it = _estados.iterator();        
        while (it.hasNext()) {
            String estado = (String) it.next();            
            Vector vestado = new Vector();
            vestado.add(estado);            
            tabelaEstados.put(estado, vestado);
        }
            
    }
    
    public String[][] getTransicoesEmString() {
        
        int i,j;
        Vector estados = tabela.getEstados();
        Vector simbolos = tabela.getSimbolos();        
        
        Vector[][] transicoes = tabela.getTransicoes();
        
        estados.add("#");
        
        estadosDeterminizados = (Vector) estados.clone();
        
        vetTrans = new String[estados.size()][simbolos.size()];
        
        for (i=0;i<transicoes.length;i++) {
            for (j=0;j<transicoes[i].length;j++) {
                vetTrans[i][j] = getTransicao(transicoes[i][j]);               
            }
        }
        for (i=0;i<simbolos.size();i++) {
            vetTrans[estados.size()-1][i] = "";
        }
        
        return vetTrans;
        
    }
    
    public String[][] getVetTrans() {
        return vetTrans;
    }
    
    public void minimiza(String[][] _vetTrans) {
        
        Vector simbolos = tabela.getSimbolos();        
        
        mortos(_vetTrans, estadosDeterminizados, simbolos);
        _vetTrans = removeMortos(simbolos);
        inacessiveis(_vetTrans, estadosDeterminizados, simbolos);
        _vetTrans = removeInacessiveis(simbolos);
        divideFinais(estadosDeterminizados, simbolos);
        divideClasses(_vetTrans, estadosDeterminizados, simbolos);
        vetTrans = montaMatrizEquivalente(estadosDeterminizados, simbolos);  
        estadosDeterminizados = classesParaEstados();
        
    }
    
    public Vector classesParaEstados() {
        Iterator it = classes.iterator();
        Vector novosEstados = new Vector();
        while(it.hasNext()) {
            Vector classe = (Vector) it.next();
            novosEstados.add(getTransicao(classe));
        }
        return novosEstados;
    }
    
    private void inicializaVisitados(Vector _simbolos) {
        int i;
        visitados = new Vector[_simbolos.size()];
        for(i=0; i< _simbolos.size();i++)
            visitados[i] = new Vector();
        
    }
    
    
    public void inacessiveis(String[][] _vetTrans, Vector _estados, Vector _simbolos) {
        
        int i=0, j, k;        
        
        inicializaVisitados(_simbolos);
        percorreEstados(_vetTrans, _estados, _simbolos, i);
        
        inacessiveis = (Vector) _estados.clone();
        for(i=0;i<_simbolos.size();i++) {
            for (j=0;j < visitados[i].size(); j++) {
               if (inacessiveis.contains(visitados[i].get(j))) {
                   k = inacessiveis.indexOf(visitados[i].get(j));
                   inacessiveis.remove(k);
               }
                   
            }            
        }           
        
    }
    
    public String[][] removeInacessiveis(Vector _simbolos) {
         
        int i,j;        
        String[][] tmpVetTrans;
        Vector tmpEstados;
        
        tmpEstados = (Vector) estadosDeterminizados.clone();
        
        for(i=0;i<estadosDeterminizados.size();i++) {            
            String estado = (String) estadosDeterminizados.get(i);
            if (inacessiveis.contains(estado)) {
                tiraEstadoDoVetor(estado,estadosDeterminizados,_simbolos);
                tmpEstados.remove(tmpEstados.indexOf(estado));
            }                                 
        }
                
        tmpVetTrans = new String[tmpEstados.size()][_simbolos.size()];
        for (i=0;i<tmpEstados.size();i++) {
            for (j=0;j<_simbolos.size();j++) {
                String estado = (String) tmpEstados.get(i);
                int idx = estadosDeterminizados.indexOf(estado);
                tmpVetTrans[i][j] = vetTrans[idx][j];
            }
        }
        
        estadosDeterminizados = tmpEstados;
        vetTrans = tmpVetTrans;        
        return tmpVetTrans;
         
    }
    
    public void mortos(String[][] _vetTrans, Vector _estados, Vector _simbolos) {
        
        int i=0, j, k;        
        mortos = new Vector();        
        
        for (i=0;i<_estados.size();i++) {
            inicializaVisitados(_simbolos);
            percorreEstados(_vetTrans, _estados, _simbolos, i);
            mkMortos(_estados, _simbolos, i);
        }         
        
    }
    
    public void mkMortos(Vector _estados, Vector _simbolos,int i) {
        int j, k, finais=0;        
        
        finais = 0;
        
        for (j=0;j<_simbolos.size();j++) {
            for (k=0;k<visitados[j].size();k++) {
                String estado = (String) visitados[j].get(k);
                if (estado.indexOf("#") >= 0)
                    finais++;                    
            }
        }
        
        if (finais==0)
            mortos.add(_estados.get(i));
        
    }
    
    public String[][] removeMortos(Vector _simbolos) {
         
        int i,j;        
        String[][] tmpVetTrans;
        Vector tmpEstados;
        
        tmpEstados = (Vector) estadosDeterminizados.clone();
        
        for(i=0;i<estadosDeterminizados.size();i++) {            
            String estado = (String) estadosDeterminizados.get(i);
            if (mortos.contains(estado)) {
                tmpEstados.remove(tmpEstados.indexOf(estado));                
                tiraEstadoDoVetor(estado,estadosDeterminizados,_simbolos);
            }
        }
                
        tmpVetTrans = new String[tmpEstados.size()][_simbolos.size()];
        for (i=0;i<tmpEstados.size();i++) {
            for (j=0;j<_simbolos.size();j++) {
                String estado = (String) tmpEstados.get(i);
                int idx = estadosDeterminizados.indexOf(estado);
                tmpVetTrans[i][j] = vetTrans[idx][j];
                
            }
        }
        
        estadosDeterminizados = tmpEstados;
        vetTrans = tmpVetTrans;        
        return tmpVetTrans;
         
    }
    
    
    
    public void percorreEstados(String[][] _vetTrans, Vector _estados, Vector _simbolos, int i)  {                
        int j, k;   
                
        for (j=0;j<_simbolos.size();j++) { 
                        
            if (!visitados[j].contains(_estados.get(i))) {                            
                
                visitados[j].add(_estados.get(i));
                
                if (!_vetTrans[i][j].equals("")) {
                    k = _estados.indexOf(_vetTrans[i][j]);                    
                    percorreEstados(_vetTrans, _estados, _simbolos, k);
                }
            }
        }
        
    }
    
    
    private void divideFinais(Vector _estados, Vector _simbolos) {
        int i,j;
        
        finais = new Vector();
        outros = new Vector();
        classes = new Vector();
        
        for (i=0;i<_estados.size();i++) {            
            String estado = (String) _estados.get(i);
            if (estado.indexOf("#") >= 0)
                finais.add(estado);
            else
                outros.add(estado);            
        }
        
        classes.add(outros);
        classes.add(finais);
        
    }
    
    private void divideClasses(String [][] _vetTrans, Vector _estados, Vector _simbolos) {
        
        boolean houveTroca = true;
        while (houveTroca) {
            Iterator iterator = classes.iterator();
            while (iterator.hasNext()) {            
                Vector classe = (Vector) iterator.next();                 
                if(houveTroca = divideClasse(_vetTrans,_estados, _simbolos, classe))                
                    break;
            }
        }
    }
    
    private boolean divideClasse (String [][] _vetTrans, Vector _estados, Vector _simbolos, Vector _classe) {
        
        
        int idxSimbolo = 0;
        boolean houveTroca = false, troca;
        
        
        Iterator iteratorSimbolo = _simbolos.iterator();
        
        while (iteratorSimbolo.hasNext() && !houveTroca) {
            Hashtable tabelaTroca = new Hashtable();
            Iterator iteratorClasse = _classe.iterator();                    
            String strSimbolo = (String) iteratorSimbolo.next();
            while (iteratorClasse.hasNext()) {            
                String strEstado = (String) iteratorClasse.next();
                String transicao = getTransicaoVetor(_vetTrans, _estados, strEstado, idxSimbolo);
                troca = trocaClasse(transicao, strEstado, _classe, tabelaTroca, iteratorClasse);
                houveTroca = houveTroca || troca;
            }            
            idxSimbolo++;
        }
        
        return houveTroca;
    }
    
    /*Pega a transicao do estado no vetor de transição*/
    private String getTransicaoVetor (String [][] _vetTrans, Vector _estados, String _strEstado, int idxSimbolo) {
        int idxEstado = _estados.indexOf(_strEstado);
        return _vetTrans[idxEstado][idxSimbolo];
    }
    
    private boolean trocaClasse(String _transicao, String _estadoClasse, Vector classe, Hashtable tabelaTroca, Iterator itClasse) {
        Integer destino;
        if (tabelaTroca.size() > 0) {                        
            destino = (Integer) tabelaTroca.get(new Integer(getIdxClasse(_transicao)));            
            //Não existe esta transição na tabela de troca
            if (destino == null){ //Cria um novo estado                                
                int i = classe.indexOf(_estadoClasse);                
                itClasse.remove();                
                Vector novaClasse = new Vector();
                novaClasse.add(_estadoClasse);
                classes.add(novaClasse);
                tabelaTroca.put(new Integer(getIdxClasse(_transicao)), new Integer(classes.size()-1));
                return true;
            } else {                
                if (!((Vector) classes.get(destino.intValue())).contains(_estadoClasse)) {
                    //o estado ainda não está nessa classe, deve ser inserido
                    int i = classe.indexOf(_estadoClasse);                   
                    itClasse.remove();                    
                    ((Vector) classes.get(destino.intValue())).add(_estadoClasse);
                    return true;
                } else
                    return false;
                
            }               
            
        } else {
        /*Tabela de troca está vazia significa que
        é o primeiro elemento da classe que está sendo lido.
        Este elemento deve permanecer em sua classe*/                                                
            tabelaTroca.put(new Integer(getIdxClasse(_transicao)), 
                            new Integer(getIdxClasse(_estadoClasse)));
            return false;
        }
    }    
   
    
    private int getIdxClasse(String _estado) {
        int i;
        Vector classe;
        
        for (i=0;i<classes.size();i++) {
            classe = (Vector) classes.get(i);
            if (classe.contains(_estado))
                return i;
        }
        
        return -1;
    }
    
    public String[][] montaMatrizEquivalente(Vector _estados, Vector _simbolos) {
               
        int i=0, j=0; 
        
        String[][] tmpVetTrans = new String[classes.size()][_simbolos.size()];
        Iterator itClasses = classes.iterator();        
        
        while (itClasses.hasNext()) {
            Vector classe = (Vector) itClasses.next();
            Iterator itSimbolos = _simbolos.iterator();
            j=0;
            while (itSimbolos.hasNext()) {
                //Pega o indice do estado do primeiro elemento da classe
                int idx = _estados.indexOf(classe.get(0));
                String transicao = vetTrans[idx][j]; //pega a transicao do elemento
                if (transicao.length() > 0)                    
                    tmpVetTrans[i][j] =  getTransicao(getClasse(transicao));
                else
                    tmpVetTrans[i][j] = "";
                String _simbolo = (String) itSimbolos.next();
                j++;
            }
            i++;
        }
        
        return tmpVetTrans;
        
    }
    
    private Vector getClasse(String _estado) {
        int i;
        Vector classe;
        
        for (i=0;i<classes.size();i++) {
            classe = (Vector) classes.get(i);
            if (classe.contains(_estado))
                return classe;
        }
        
        return null;
    }
    
    private void tiraEstadoDoVetor(String estado, Vector _estados, Vector _simbolos) {
        int i,j;
        
        for(i=0;i<_estados.size();i++)
            for (j=0;j<_simbolos.size();j++)
                if (vetTrans[i][j].equals(estado))
                    vetTrans[i][j] = "";
    }
    
    private void determinizaVetor(String[][] _vetTrans, Vector _estados, Vector _simbolos) {
        int i, j, k;
        
        for (i=0;i<_estados.size();i++) {
            for (j=0;j<_simbolos.size();j++) {
                if (_vetTrans[i][j].length()>1) {
                    
                    //é necessário criar mais um estado
                    //se o estado não existe
                    if (!_estados.contains(_vetTrans[i][j])) {
                        
                        _estados.add(_vetTrans[i][j]);                        
                        
                        //é necessário criar mais uma linha na matriz
                        String[][] tmp = _vetTrans;
                        _vetTrans = new String[_estados.size()][_simbolos.size()];
                        copiaMatriz(tmp, _vetTrans);
                        /*gera os proximos estados para o estado gerado */
                        for (k=0;k<_simbolos.size();k++) {                            
                            Vector estadosTransicao = (Vector) tabelaEstados.get(_vetTrans[i][j]);                            
                            _vetTrans[_estados.size()-1][k] = 
                                    proximaTransicao(estadosTransicao,_vetTrans,_estados,k);
                            /*String aux = _vetTrans[i][j];
                            String parteA = aux.substring(0, aux.length()-1);                        
                            String parteB = aux.substring(aux.length()-1, aux.length());
                            int iA = _estados.indexOf(parteA); 
                            int iB = _estados.indexOf(parteB);
                            _vetTrans[_estados.size()-1][k] = _vetTrans[iA][k] + _vetTrans[iB][k];*/

                        }                    
                    }
                }
            }
        } 
        
        vetTrans = _vetTrans;
    }
    
    //Cria os estado da próxima transicao
    private String proximaTransicao(Vector _estadosTransicao, String[][] _vetTrans, Vector _estados, int j) {
        Iterator it = _estadosTransicao.iterator();
        Vector transicao = new Vector();
        //Prtcorre todos estados da transicao
        String novoEstado = "";
        Vector vnovoEstado = new Vector();
        while(it.hasNext()) {
            String estado = (String) it.next();            
            //Aqui deve-se formar um novo estado, pegando a transicao para esse novo estado
            int i = _estados.indexOf(estado);
            if (!vnovoEstado.contains(_vetTrans[i][j]) && (_vetTrans[i][j].length() > 0)) {
                vnovoEstado.add(_vetTrans[i][j]);
                novoEstado = novoEstado + _vetTrans[i][j];                
            }                
        }
        if (!tabelaEstados.containsKey(novoEstado))
            tabelaEstados.put(novoEstado, vnovoEstado);        
        
        return novoEstado;
    }
    
    private void copiaMatriz(String[][] src, String[][] dest) {
        int i, j;
        for (i=0;i<src.length;i++) {
            for (j=0;j<src[i].length;j++) {                               
                dest[i][j] = src[i][j];                
            }
        }
        
    }
    
    private String getTransicao(Vector _transicao) {
        int i;
        String trans="";
        if (_transicao == null)
            return "";
        
        Vector estadosTransicao = new Vector();
        
        for (i=0;i<_transicao.size();i++){            
            if (_transicao.get(i) != null)                
                trans = trans + ((String) _transicao.get(i));
            else
                trans = "";
        }        
        return trans;
    }
    
    public int indiceNovoEstadoInicial(String estadoInicial) {
        int i = -1;
        
        if (classes != null) {
            Iterator it = classes.iterator();
        
            while (it.hasNext()) {
                Vector classe = (Vector) it.next();
                if (classe.contains(estadoInicial)) {
                    i = classe.indexOf(estadoInicial);
                    break;
                }                
            }
        
        } else 
            i = estadosDeterminizados.indexOf(estadoInicial);
        
        
        return i;
        
    }

}
 