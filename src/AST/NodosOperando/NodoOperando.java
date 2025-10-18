package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresionCompuesta;

public abstract class NodoOperando extends NodoExpresionCompuesta{


    public void imprimir(String s) {
        System.out.print(s);
    }
}
