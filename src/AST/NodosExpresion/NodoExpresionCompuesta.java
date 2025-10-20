package AST.NodosExpresion;

import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;

public abstract class NodoExpresionCompuesta extends NodoExpresion {
    public abstract Tipo chequear() throws ExcepcionSemantica;
}
