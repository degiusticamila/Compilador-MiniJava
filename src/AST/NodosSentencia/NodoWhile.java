package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;

public class NodoWhile extends NodoSentencia {
    private NodoSentencia sentencia;
    private NodoExpresion expresion;

    public NodoWhile(NodoExpresion expresion, NodoSentencia sentencia){
        this.expresion = expresion;
        this.sentencia =  sentencia;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo+"While:");
        if(expresion != null){
            System.out.println(prefijo+ " Condicion:");
            expresion.imprimir(prefijo);
        }
        if(sentencia != null){
            System.out.println(prefijo+ " Cuerpo:");
            sentencia.imprimir(prefijo);
        }
    }
}
