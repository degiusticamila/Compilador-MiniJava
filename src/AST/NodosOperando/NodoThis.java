package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.Tipo;
import Utils.Token;

public class NodoThis extends NodoOperando {
    private Token tokenThis;

    public NodoThis(Token tokenThis) {
        this.tokenThis = tokenThis;
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
        return tokenThis.getLexema();
    }

    @Override
    public Tipo chequear() {
        return null;
    }
}
