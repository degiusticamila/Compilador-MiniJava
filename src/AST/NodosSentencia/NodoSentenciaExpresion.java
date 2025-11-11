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
import com.sun.tools.javac.Main;

public class NodoSentenciaExpresion extends NodoSentencia{
    private NodoExpresion expresion;
    private int linea;
    public NodoSentenciaExpresion(NodoExpresion nodoSentenciaExpresion, int linea) {
        this.expresion = nodoSentenciaExpresion;
        this.linea = linea;
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

        /*
        if (expresion instanceof NodoExpAsignacion ||
                expresion instanceof NodoOperadorUnario ||
                expresion instanceof NodoLlamadaMetodo ||
                expresion instanceof NodoLlamadaMetodoEstatico ||
                expresion instanceof NodoOperando) {
            expresion.chequear();
            return;
        }

         */
        if (expresion instanceof NodoExpAsignacion ||
                expresion instanceof NodoLlamadaMetodo ||
                expresion instanceof NodoOperadorUnario ||
                expresion instanceof NodoLlamadaMetodoEstatico ||
                expresion instanceof NodoLlamadaConstructor) {

            expresion.chequear();
            return;
        }
        if(expresion instanceof NodoOperando){
            NodoOperando op = (NodoOperando)expresion;
            if(op.tieneEncadenado()){
                expresion.chequear();
                return;
            }
        }


        throw new ExcepcionSemantica(expresion.formatear(),linea,"La expresión no produce efecto (resultado no utilizado)");
    }

    @Override
    public void generar(ArchivoSalida archivo) {
        if (expresion instanceof NodoExpAsignacion ||
                expresion instanceof NodoLlamadaMetodo ||
                expresion instanceof NodoLlamadaMetodoEstatico) {
            expresion.generar(archivo);
        }
    }
}
