package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.ExcepcionSemantica;

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

    @Override
    public void chequear() throws ExcepcionSemantica {
        expresion.chequear();
    }
}
