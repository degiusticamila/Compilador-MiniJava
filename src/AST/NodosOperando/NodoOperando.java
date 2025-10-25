package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresionCompuesta;
import Utils.Token;

public abstract class NodoOperando extends NodoExpresionCompuesta{

    public abstract Token getNombre();
    /*public void imprimir(String s) {
        System.out.print(s);
    }

     */
    public abstract void imprimir(String s);
}
