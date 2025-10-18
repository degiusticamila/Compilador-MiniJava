package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import Utils.Token;

public class NodoIntLiteral extends NodoOperando {
    Token nombre;
    public NodoIntLiteral(Token nombre){
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
            System.out.print(prefijo+ nombre.getLexema());
    }
}
