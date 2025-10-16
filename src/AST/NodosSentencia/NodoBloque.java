package AST.NodosSentencia;

import java.util.ArrayList;
import java.util.List;

public class NodoBloque extends NodoSentencia {
    private List<NodoSentencia> sentencias;

    public NodoBloque() {
        sentencias = new ArrayList<NodoSentencia>();
    }
    public List<NodoSentencia> getSentencias() {
        return sentencias;
    }
    public void imprimir(String prefijo){
        System.out.println(prefijo+ "Bloque {");
        for(NodoSentencia nodo: sentencias){
            nodo.imprimir(prefijo +"}");
        }
    }

}
