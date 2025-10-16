package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import Utils.Token;

public class NodoIfSolo extends NodoSentencia {
    private NodoExpresion condicion;
    private NodoSentencia sentenciaIf;
    private NodoSentencia sentenciaElse;
    public NodoIfSolo(NodoExpresion condicion, NodoSentencia sentenciaIf,NodoSentencia sentenciaElse) {
        this.condicion = condicion;
        this.sentenciaIf = sentenciaIf;
        this.sentenciaElse = sentenciaElse;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo+"If");
    }
}
