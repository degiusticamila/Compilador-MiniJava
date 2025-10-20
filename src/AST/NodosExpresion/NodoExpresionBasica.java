package AST.NodosExpresion;

import AST.NodosOperando.NodoOperando;
import AST.NodosSentencia.NodoOperadorUnario;
import TablaDeSimbolos.Tipo;
import Utils.Token;

public class NodoExpresionBasica extends NodoExpresionCompuesta{
    private NodoOperadorUnario operadorUnario;
    private NodoOperando operando;
    public NodoExpresionBasica(NodoOperadorUnario operadorUnario, NodoOperando operando) {
        this.operadorUnario = operadorUnario;
        this.operando = operando;
    }
    public NodoExpresionBasica(NodoOperando operando){
        this.operando = operando;
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

    }

    @Override
    public String formatear() {
        return "";
    }

    @Override
    public Tipo chequear() {
        return null;
    }
}
