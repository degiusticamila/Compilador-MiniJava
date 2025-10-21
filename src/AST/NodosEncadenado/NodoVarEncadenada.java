package AST.NodosEncadenado;

import AST.NodosOperando.NodoOperando;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoVarEncadenada extends NodoEncadenado {
    private Token nombre;
    private NodoEncadenado encadenado;
    public NodoVarEncadenada(Token nombre,  NodoEncadenado encadenado) {
        this.nombre = nombre;
        this.encadenado = encadenado;
    }
    @Override
    public Tipo chequear(Tipo t) {
        return new TipoUniversal("Tipo universal");
    }
}
