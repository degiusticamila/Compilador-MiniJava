package TablaDeSimbolos;

import Utils.Token;

public class Parametro {
    private Tipo tipo;
    private Token nombre;
    private int posicion;

    public Parametro(Tipo tipo, Token nombre, int posicion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.posicion = posicion;
    }
    public String toString() {
        return ("("+tipo.toString()+", "+nombre.toString()+", "+posicion+")");
    }
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    public Token  getNombre() {
        return nombre;
    }
    public boolean equals(Parametro p){
        return tipo.equals(p.tipo) && nombre.getLexema().equals(p.nombre.getLexema());
    }

}
