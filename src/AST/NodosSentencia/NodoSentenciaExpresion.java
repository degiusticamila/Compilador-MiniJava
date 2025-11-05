package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpAsignacion;
import AST.NodosExpresion.NodoExpresion;
import AST.NodosOperando.NodoLlamadaConstructor;
import AST.NodosOperando.NodoLlamadaMetodo;
import AST.NodosOperando.NodoLlamadaMetodoEstatico;
import AST.NodosOperando.NodoOperando;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import Utils.SourceManager;

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
        //Tipo tipoExpresion = expresion.chequear();
        if (expresion instanceof NodoExpAsignacion ||
                expresion instanceof NodoOperadorUnario ||
                expresion instanceof NodoLlamadaMetodo ||
                expresion instanceof NodoLlamadaMetodoEstatico ||
                expresion instanceof NodoOperando) {
            expresion.chequear();
            return;
        }

        throw new ExcepcionSemantica(expresion.formatear(),-1,"La expresión no produce efecto (resultado no utilizado)");
    }

    @Override
    public void generar(ArchivoSalida archivo) {
        if(expresion instanceof NodoLlamadaMetodo){
            generar(archivo);
        }
    }
}
