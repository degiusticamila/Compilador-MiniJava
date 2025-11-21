package AST.NodosSentencia;

import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.ExcepcionSemantica;

public abstract class NodoSentencia {
    public abstract void imprimir(String prefijo);
    public abstract void chequear() throws ExcepcionSemantica;
    public abstract void generar(ArchivoSalida archivo) throws ExcepcionSemantica;

    public abstract String nombreSentencia();
}
