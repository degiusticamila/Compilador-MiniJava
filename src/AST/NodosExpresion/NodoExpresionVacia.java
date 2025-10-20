package AST.NodosExpresion;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoExpresionVacia extends NodoExpresion {
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
        System.out.println("exp vacia");
    }

    @Override
    public String formatear() {
        return "";
    }

    @Override
    public Tipo chequear() {
        return new TipoUniversal("tipo universal");
    }
}
