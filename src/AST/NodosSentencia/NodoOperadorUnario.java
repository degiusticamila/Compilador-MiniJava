package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpUnaria;

public class NodoOperadorUnario extends NodoSentencia {
    private NodoExpUnaria nodoExpresionUnaria;

    public NodoOperadorUnario(NodoExpUnaria nodoExpresion) {
        this.nodoExpresionUnaria = nodoExpresion;
    }
    @Override
    public void imprimir(String prefijo) {

    }
}
