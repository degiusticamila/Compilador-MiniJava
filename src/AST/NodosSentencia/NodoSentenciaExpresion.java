package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;

public class NodoSentenciaExpresion extends NodoSentencia{
    private NodoExpresion expresion;
    public NodoSentenciaExpresion(NodoExpresion nodoSentenciaExpresion){
        this.expresion = nodoSentenciaExpresion;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo);
        if(expresion != null){
            expresion.imprimir(prefijo+ " ");
        }
    }
}
