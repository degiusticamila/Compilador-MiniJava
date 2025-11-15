package AST.NodosSentencia;

import ArchivoSalida.ArchivoSalida;

public class NodoSentenciaVacia extends NodoSentencia {
    @Override
    public void imprimir(String prefijo) {
        System.out.println("Sentencia vacia");
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
