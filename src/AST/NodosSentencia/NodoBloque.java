package AST.NodosSentencia;

import AST.NodosOperando.NodoOperando;
import TablaDeSimbolos.ExcepcionSemantica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NodoBloque extends NodoSentencia {
    private List<NodoSentencia> sentencias;
    private List<NodoSentencia> variables;
    public NodoBloque() {
        sentencias = new ArrayList<>();
        variables = new LinkedList<>();
    }
    public void insertarVariable(NodoSentencia nodo){
        //Controlar que no se este insertando una que ya existe
        
        variables.addLast(nodo);
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
        for(NodoSentencia s: sentencias){
            System.out.println("Recorro mis sentencias");
            s.chequear();
        }
    }
}
