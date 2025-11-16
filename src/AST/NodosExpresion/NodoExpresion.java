package AST.NodosExpresion;

import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.Elemento;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import Utils.Token;

public abstract class NodoExpresion {
    protected boolean esLadoIzq = false;
    public abstract void setOperador(Token operador);
    public abstract void setLadoIzquierdo(NodoExpresion nodoExpresion);
    public abstract void setLadoDerecho(NodoExpresion nodoExpresion);
    public abstract void imprimir(String prefijo);
    public abstract String formatear();
    public abstract Tipo chequear() throws ExcepcionSemantica;
    public abstract void generar(ArchivoSalida archivo);
    public abstract String nombreSentencia();
    public boolean getLadoIzq(){
        return esLadoIzq;
    }
    public void setEsLadoIzq(){
        esLadoIzq = !esLadoIzq;
    }

}
