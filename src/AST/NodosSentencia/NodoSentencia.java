package AST.NodosSentencia;

import TablaDeSimbolos.ExcepcionSemantica;

public abstract class NodoSentencia {
    public abstract void imprimir(String prefijo);
    public abstract void chequear() throws ExcepcionSemantica;
}
