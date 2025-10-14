package AST;

import Utils.Token;

public class NodoVar extends NodoOperando{
    public Token nombre;

    public NodoVar(Token nombre) {
        this.nombre = nombre;
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
        System.out.println(prefijo + "Var: " + nombre.getLexema());
    }
}
