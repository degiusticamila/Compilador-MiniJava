package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;

public class NodoIf extends NodoSentencia {
    private NodoExpresion condicion;
    private NodoSentencia sentenciaIf;
    private NodoSentencia sentenciaElse;
    public NodoIf(NodoExpresion condicion, NodoSentencia sentenciaIf, NodoSentencia sentenciaElse) {
        this.condicion = condicion;
        this.sentenciaIf = sentenciaIf;
        this.sentenciaElse = sentenciaElse;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo+"If");
        if(condicion != null){
            System.out.println(prefijo+ " Condicion:");
            condicion.imprimir(prefijo);
        }
        if(sentenciaIf != null){
            System.out.println(prefijo+ " Cuerpo if:");
            sentenciaIf.imprimir(prefijo+ " ");
        }
        if(sentenciaElse != null && !(sentenciaElse instanceof NodoSentenciaVacia)){
            System.out.println(prefijo+ " Cuerpo else:");
            sentenciaElse.imprimir(prefijo+ " ");

        }
    }
}
