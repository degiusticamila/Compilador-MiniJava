package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
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
    public void imprimir(String prefijo) {
        System.out.print(prefijo + " null "+ nombre.getLexema());
    }

    @Override
    public String formatear() {
        return nombre.getLexema();
    }

    @Override
    public Tipo chequear() {
        return TipoPrimitivo.NULL;
    }
}
