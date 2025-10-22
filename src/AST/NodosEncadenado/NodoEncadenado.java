package AST.NodosEncadenado;

import TablaDeSimbolos.Tipo;
import Utils.Token;

public abstract class NodoEncadenado {
    public Token nombre;

    public NodoEncadenado(Token nombre) {
        this.nombre = nombre;
    }

    public abstract Tipo chequear(Tipo t);
    public abstract void imprimir(String prefijo);
    public abstract String formatear();
    public abstract void setEncadenado(NodoEncadenado encadenado);
}
