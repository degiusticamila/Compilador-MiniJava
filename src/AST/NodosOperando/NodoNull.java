package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import TablaDeSimbolos.TipoReferencia;
import Utils.Token;

public class NodoNull extends NodoOperando {
    private Token nombre;

    public NodoNull(Token nombre) {
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
        System.out.print(prefijo + " null "+ nombre.getLexema());
    }

    @Override
    public boolean tieneEncadenado() {
        return false;
    }

    @Override
    public String formatear() {
        return nombre.getLexema();
    }

    @Override
    public Tipo chequear() {
        return TipoReferencia.NULL;
    }

    @Override
    public void generar(ArchivoSalida archivo) {

    }
}
