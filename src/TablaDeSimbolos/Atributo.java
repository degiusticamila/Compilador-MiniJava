package TablaDeSimbolos;

import Utils.Token;

public class Atributo {
    private Tipo tipo;
    private Token nombre;

    public Atributo(Tipo tipo, Token nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }
    public String toString() {
        return ("("+tipo.toString()+", "+nombre.toString()+")");
    }
    public Tipo getTipo() {
        return tipo;
    }
    public Token getNombre(){
        return nombre;
    }
}
