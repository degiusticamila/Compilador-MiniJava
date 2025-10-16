package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import Utils.Token;

public class NodoBoolean extends NodoOperando {
    private Token nombre;

    public NodoBoolean(Token nombre) {
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
        System.out.println(prefijo + " "+nombre.getId()+ " "+ nombre.getLexema());
    }
}
