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
import TablaDeSimbolos.TipoVoid;

public class NodoSentenciaExpresion extends NodoSentencia {
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
        if (expresion != null) {
            expresion.imprimir(prefijo + " ");
        }
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        if (expresion instanceof NodoExpAsignacion ||
                expresion instanceof NodoLlamadaMetodo ||
                expresion instanceof NodoOperadorUnario ||
                expresion instanceof NodoLlamadaMetodoEstatico ||
                expresion instanceof NodoLlamadaConstructor) {

            tipoExpresion = expresion.chequear();
            return;
        }
        if (expresion instanceof NodoOperando) {
            NodoOperando op = (NodoOperando) expresion;
            if (op.tieneEncadenado()) {
                tipoExpresion = expresion.chequear();
                return;
            }
        }
        throw new ExcepcionSemantica(expresion.nombreSentencia(), linea,
                "La expresión no produce efecto (resultado no utilizado)");
    }

    @Override
    public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {

        expresion.generar(archivo);

        boolean esNoVoid = tipoExpresion != null && !(tipoExpresion instanceof TipoVoid);
        boolean esAsignacion = expresion instanceof NodoExpAsignacion;
        boolean esLlamadaInstancia = expresion instanceof NodoLlamadaMetodo;
        boolean esLlamadaEstatico = expresion instanceof NodoLlamadaMetodoEstatico;
        boolean esConstructor = expresion instanceof NodoLlamadaConstructor;
        boolean esUnario = expresion instanceof NodoOperadorUnario;
        //boolean esEncadenado = expresion instanceof NodoOperando && ((NodoOperando) expresion).tieneEncadenado();
        if (esNoVoid && !esAsignacion && !esLlamadaInstancia && !esLlamadaEstatico && !esConstructor ) {
            if (!(esUnario && (
                    ((NodoOperadorUnario)expresion).getNombre().getLexema().equals("++") ||
                            ((NodoOperadorUnario)expresion).getNombre().getLexema().equals("--")))) {
                archivo.generar(Instrucciones.POP + "");
            }
        }
    }
    @Override
    public String nombreSentencia() {
        return "";
    }
}
