package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpAsignacion;
import AST.NodosExpresion.NodoExpresion;
import AST.NodosOperando.NodoLlamadaConstructor;
import AST.NodosOperando.NodoLlamadaMetodo;
import AST.NodosOperando.NodoLlamadaMetodoEstatico;
import AST.NodosOperando.NodoOperando;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import TablaDeSimbolos.TipoVoid;
import Utils.SourceManager;
import com.sun.tools.javac.Main;

public class NodoSentenciaExpresion extends NodoSentencia{
    private NodoExpresion expresion;
    private int linea;
    private Tipo tipoExpresion;
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

            tipoExpresion = expresion.chequear();

            return;
        }
        if(expresion instanceof NodoOperando){
            NodoOperando op = (NodoOperando)expresion;
            if(op.tieneEncadenado()){
                tipoExpresion = expresion.chequear();
                return;
            }
        }


        throw new ExcepcionSemantica(expresion.nombreSentencia(),linea,"La expresión no produce efecto (resultado no utilizado)");
    }

    @Override
    public void generar(ArchivoSalida archivo) {
        if (expresion instanceof NodoExpAsignacion ||
                expresion instanceof NodoOperadorUnario ||
                expresion instanceof NodoLlamadaConstructor ||
                expresion instanceof NodoLlamadaMetodo ||
                expresion instanceof NodoLlamadaMetodoEstatico) {
            expresion.generar(archivo);
        }
        if(expresion instanceof NodoOperando){
            NodoOperando op = (NodoOperando)expresion;
            if(op.tieneEncadenado()){
                expresion.generar(archivo);

            }
        }
        System.out.println("CLase de tipoExpresion "+tipoExpresion.getClass().getName());
        if (tipoExpresion != null) {
            if (!(tipoExpresion instanceof TipoVoid)) {
                if (!(expresion instanceof NodoExpAsignacion)) {

                    if (expresion instanceof NodoLlamadaMetodo ||
                            expresion instanceof NodoLlamadaMetodoEstatico ||
                            (expresion instanceof NodoOperando && ((NodoOperando)expresion).tieneEncadenado())) {
                        System.out.println("TIRO EL RESULTADO PORQUE NO SE USA!");
                        archivo.generar(Instrucciones.POP + "");
                    }
                }
            }
        }


    }

    public String nombreSentencia() {
        return "";
    }
}
