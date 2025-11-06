package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoReferencia;
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
        return nombre.getLexema();
    }

    @Override
    public Tipo chequear() {
        return new TipoReferencia("String");
    }

    @Override
    public void generar(ArchivoSalida archivo) {

    }

    @Override
    public Token getNombre() {
        return nombre;
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.print(prefijo+nombre.getLexema());
    }
}
