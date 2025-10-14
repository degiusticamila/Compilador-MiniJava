package AST;

import Utils.Token;

public class NodoAccesoVar extends NodoOperando{
    Token nombre;

    public NodoAccesoVar(Token nombre){
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
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "Var: " + nombre.getLexema());
    }
}
