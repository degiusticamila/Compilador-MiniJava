package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import Utils.Token;

public class NodoReturn extends NodoSentencia {
    private Token nombre;
    private NodoExpresion expresionOpcional;
    public NodoReturn(Token nombre) {
        this.nombre = nombre;
    }
    public void setExpresionOpcional(NodoExpresion expresionOpcional) {
        this.expresionOpcional = expresionOpcional;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "Return: " + nombre.getLexema());
    }
}
