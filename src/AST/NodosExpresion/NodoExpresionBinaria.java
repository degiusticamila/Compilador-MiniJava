package AST.NodosExpresion;

import Utils.Token;

public class NodoExpresionBinaria extends NodoExpresionCompuesta {
    private Token operador;
    private NodoExpresion ladoIzquierdo;
    private NodoExpresion ladoDerecho;

    public NodoExpresionBinaria(Token operador, NodoExpresion  ladoIzquierdo, NodoExpresion ladoDerecho) {
        this.operador = operador;
        this.ladoIzquierdo = ladoIzquierdo;
        this.ladoDerecho = ladoDerecho;
    }
    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    @Override
    public void setLadoDerecho(NodoExpresion nodoExpresion) {

    }

    @Override
    public void imprimir(String prefijo) {
        ladoIzquierdo.imprimir("");
        System.out.print(operador.getLexema()+" ");
        ladoDerecho.imprimir("");
    }
}
