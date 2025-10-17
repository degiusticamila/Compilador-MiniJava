package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import Utils.Token;

public class NodoOperandoVacio extends NodoOperando{
    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    @Override
    public void setLadoDerecho(NodoExpresion nodoExpresion) {

    }
}
