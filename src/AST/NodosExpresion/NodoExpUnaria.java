package AST.NodosExpresion;

import AST.NodosOperando.NodoOperando;
import Utils.Token;

public class NodoExpUnaria extends NodoExpresionCompuesta {
    private Token operador;
    private NodoOperando nodoOperando;

    public NodoExpUnaria(){

    }
    public void setOperando(NodoOperando nodoOperando) {
        this.nodoOperando = nodoOperando;
    }
    @Override
    public void setOperador(Token operador) {
        this.operador = operador;
    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    @Override
    public void setLadoDerecho(NodoExpresion nodoExpresion) {

    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "ExpUnaria(" + operador.getLexema() + ")");
        if (operador != null) System.out.print(operador.getLexema());;
        if (nodoOperando != null) nodoOperando.imprimir(prefijo + "  R-> ");
    }
}
