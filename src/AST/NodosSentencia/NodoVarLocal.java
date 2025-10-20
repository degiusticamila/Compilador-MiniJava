package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import AST.NodosExpresion.NodoExpresionVacia;
import Utils.Token;

public class NodoVarLocal extends NodoSentencia {
    public Token nombre;
    private Token operador;
    private NodoExpresion ladoDerecho;
    public NodoVarLocal(Token nombre) {
        this.nombre = nombre;
        this.ladoDerecho = new NodoExpresionVacia();
    }
    public void setLadoDerecho(NodoExpresion ladoDerecho){
        this.ladoDerecho = ladoDerecho;
    }
    public void setOperador(Token operador){
        this.operador = operador;
    }
    @Override
    public void imprimir(String prefijo) {

        System.out.println(prefijo + "Var: " + nombre.getLexema());
        System.out.print(operador.getLexema());
        ladoDerecho.imprimir(" ");
    }

    @Override
    public void chequear() {

    }
}
