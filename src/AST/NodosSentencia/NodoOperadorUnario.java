package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import Utils.Token;

public class NodoOperadorUnario extends NodoExpresion{
    private NodoExpresion ladoDerecho;
    private Token nombre;

    public NodoOperadorUnario(Token nombre, NodoExpresion ladoDerecho) {
        this.nombre = nombre;
        this.ladoDerecho = ladoDerecho;
    }

    public void imprimir(String prefijo) {
        System.out.println(prefijo+ nombre.getLexema());
        if(ladoDerecho != null){
            ladoDerecho.imprimir(prefijo+ " ");
        }
    }
    public Token getNombre(){
        return nombre;
    }

    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    public void setLadoDerecho(NodoExpresion ladoDerecho){
        this.ladoDerecho = ladoDerecho;
    }
}
