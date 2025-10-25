package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoString extends NodoOperando{
    private Token nombre;

    public NodoString(Token token) {
        this.nombre= token;
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
        return new TipoUniversal("tipo universal");
    }

    @Override
    public Token getNombre() {
        return nombre;
    }

    @Override
    public void imprimir(String s) {
        System.out.println(s);
    }
}
