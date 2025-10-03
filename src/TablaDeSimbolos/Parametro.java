package TablaDeSimbolos;

import Utils.Token;

public class Parametro {
    private Token tipo;
    private Token nombre;
    private int posicion;

    public Parametro(Token tipo, Token nombre, int posicion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.posicion = posicion;
    }

}
