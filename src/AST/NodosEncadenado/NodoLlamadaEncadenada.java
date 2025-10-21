package AST.NodosEncadenado;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

import java.util.LinkedList;
import java.util.List;

public class NodoLlamadaEncadenada extends NodoEncadenado {
    private Token nombre;
    private List<NodoExpresion> parametros;
    NodoEncadenado encadenado;
    public NodoLlamadaEncadenada(Token nombre, NodoEncadenado encadenado, List<NodoExpresion> parametros) {
        this.parametros = parametros;
        this.nombre = nombre;
        this.encadenado = encadenado;
    }
    @Override
    public Tipo chequear(Tipo t) {
        return new TipoUniversal("Tipo universal");
    }
}
