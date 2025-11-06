package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
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
    public Token getNombre() {
        return nombre;
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.print(prefijo + " "+" "+ nombre.getLexema());
    }

    @Override
    public String formatear() {
        return nombre.getLexema();
    }

    @Override
    public Tipo chequear() {
        return new TipoPrimitivo("boolean");
    }

    @Override
    public void generar(ArchivoSalida archivo) {

    }
}
