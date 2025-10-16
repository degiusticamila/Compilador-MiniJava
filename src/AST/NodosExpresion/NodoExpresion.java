package AST.NodosExpresion;

import Utils.Token;

public abstract class NodoExpresion {
    public abstract void setOperador(Token operador);
    public abstract void setLadoIzquierdo(NodoExpresion nodoExpresion);
    public abstract void setLadoDerecho(NodoExpresion nodoExpresion);
    public abstract void imprimir(String prefijo);
}
