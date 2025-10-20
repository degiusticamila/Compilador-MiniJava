package AST.NodosSentencia;

import AST.NodosOperando.NodoOperando;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.TablaSimbolos;
import TablaDeSimbolos.Tipo;
import Utils.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NodoBloque extends NodoSentencia {
    private List<NodoSentencia> sentencias;
    private List<NodoVarLocal> variablesLocales;
    public NodoBloque() {
        sentencias = new ArrayList<>();
        variablesLocales = new LinkedList<>();
    }
    public void insertarVariable(NodoVarLocal nodo){

        //Controlar que no se este insertando una que ya existe
        if(variablesLocales.contains(nodo)){
           // throw new ExcepcionSemantica()
        }
        variablesLocales.addLast(nodo);
    }

    public List<NodoSentencia> getSentencias() {
        return sentencias;
    }
    public void imprimir(String prefijo){
        System.out.println(prefijo+ "Bloque {");
        for(NodoSentencia nodo: sentencias){
            nodo.imprimir(prefijo +" ");
        }
        System.out.println(prefijo+ "}");
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        NodoBloque bloqueAnterior = ts.getBloqueActual();

        ts.setBloqueActual(this);
        for(NodoSentencia s: sentencias){
           /* System.out.println("Recorro mis sentencias");
            if(s instanceof NodoBloque){
                TablaSimbolos.getInstance().setBloqueActual((NodoBloque) s);
            }

            */
            s.chequear();
        }
        ts.setBloqueActual(bloqueAnterior);
    }
    public List<NodoVarLocal> getVariablesLocales() {
        return variablesLocales;
    }
    public boolean variableLocalDeclarada(String nombre){;
        System.out.println();
        System.out.println("¿Buscando variable local? -> " + nombre);
        for(NodoVarLocal nodoVarLocal: variablesLocales){
            System.out.println("Tengo declarada: " + nodoVarLocal.getNombreVarLocal());
            System.out.println(nodoVarLocal.getNombreVarLocal());
            if(nodoVarLocal.getNombreVarLocal().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    public Tipo getTipoVariableLocalDeclarada(String nombre){
        for(NodoVarLocal nodoVarLocal: variablesLocales){
            if(nodoVarLocal.getNombreVarLocal().equals(nombre)){
                return nodoVarLocal.getTipoVarLocal();
            }
        }
        return null;
    }
}
