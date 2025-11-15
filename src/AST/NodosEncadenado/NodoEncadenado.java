package AST.NodosEncadenado;

import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import Utils.Token;

public abstract class NodoEncadenado {
    public Token nombre;

    public NodoEncadenado(Token nombre) {
        this.nombre = nombre;
    }

    public abstract Tipo chequear(Tipo t) throws ExcepcionSemantica;
    public abstract void imprimir(String prefijo);
    public abstract String formatear();
    public abstract void setEncadenado(NodoEncadenado encadenado);
    public NodoEncadenado getUltimoEncadenado() {
        NodoEncadenado actual = this;
        NodoEncadenado toReturn = this;
        while(!(actual instanceof NodoEncadenadoVacio) && !(actual.getUltimoEncadenado() instanceof NodoEncadenadoVacio)){
            actual = actual.getUltimoEncadenado();
            if(!(actual instanceof NodoEncadenadoVacio)){
                toReturn = actual;
            }
        }
        return toReturn;

    }
}
