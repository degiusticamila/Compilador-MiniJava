package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.Tipo;
import Utils.Token;

public class NodoString extends NodoOperando{
    private Token string;

    public NodoString(Token token) {
        this.string = token;
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
    public String formatear() {
        return "";
    }

    @Override
    public Tipo chequear() {
        return null;
    }
}
