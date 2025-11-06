package AST.NodosExpresion;

import AST.NodosOperando.NodoOperando;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoExpUnaria extends NodoExpresionCompuesta {
    private Token operador;
    private NodoOperando ladoDerecho;

    public NodoExpUnaria(){

    }
    public void setOperando(NodoOperando nodoOperando) {
        this.ladoDerecho = nodoOperando;
    }
    @Override
    public void setOperador(Token operador) {
        this.operador = operador;
    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    @Override
    public void setLadoDerecho(NodoExpresion nodoExpresion) {

    }
    @Override
    public String formatear() {
        return operador.getLexema() + ladoDerecho.formatear();
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "ExpUnaria (" + operador.getLexema() + ")");
        if (ladoDerecho != null) {
            ladoDerecho.imprimir(prefijo + "  ");
        }

    }
    @Override
    public Tipo chequear() {
        return new TipoUniversal("tipo universal");
    }

    @Override
    public void generar(ArchivoSalida archivo) {

    }

}
