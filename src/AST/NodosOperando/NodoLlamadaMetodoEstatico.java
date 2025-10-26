package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import Utils.Token;

import java.util.List;

public class NodoLlamadaMetodoEstatico extends NodoOperando{
    private Token nombreClase;
    private Token nombreMetodo;
    private List<NodoExpresion> argumentos;
    public NodoLlamadaMetodoEstatico(Token nombre, Token nombreMetodo, List<NodoExpresion> argumentos) {
        this.nombreClase = nombre;
        this.nombreMetodo = nombreMetodo;
        this.argumentos = argumentos;
    }
    @Override
    public Token getNombre() {
        return null;
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
    public void imprimir(String s) {

    }

    @Override
    public String formatear() {
        return "";
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
