package TablaDeSimbolos;

import Utils.Token;

public class Atributo {
    private Token tipo;
    private Token nombre;

    public Atributo(Token tipo, Token nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }
    public String toString() {
        return ("("+tipo.toString()+", "+nombre.toString()+")");
    }
}
