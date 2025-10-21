package AST.NodosEncadenado;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;

public class NodoEncadenadoVacio extends NodoEncadenado {

    @Override
    public Tipo chequear(Tipo t) {
        return new TipoUniversal("Tipo universal");
    }
}
