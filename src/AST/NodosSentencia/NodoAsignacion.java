package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.Tipo;

public class NodoAsignacion extends NodoSentencia {
    NodoExpresion nodoExpAsignacion;

    public NodoAsignacion(NodoExpresion nodoExpresionAsignacion){
        nodoExpAsignacion = nodoExpresionAsignacion;
    }

    public void setNodoExpAsignacion(NodoExpresion nodoExpAsignacion) {
        this.nodoExpAsignacion = nodoExpAsignacion;
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "Asignacion:");
        if(nodoExpAsignacion!=null){
            nodoExpAsignacion.imprimir(prefijo + "  ");
        }
    }

    @Override
    public void chequear() {

    }

    @Override
    public void generar(ArchivoSalida archivo) {

    }

    @Override
    public String nombreSentencia() {
        return "";
    }
}
