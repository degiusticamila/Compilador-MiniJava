package TablaDeSimbolos;

import Utils.Token;

public abstract class Tipo {
    protected String nombre;
    protected Token token;

    public Tipo(String nombre){
        this.nombre = nombre;
        this.token = null;
    }
    public Tipo(String nombre, Token token) {
        this.nombre = nombre;
        this.token = token;
    }
    public String getNombre() {
        return nombre;
    }
    public abstract boolean esCompatible(Tipo t);
    public abstract boolean esPrimitivo();
    public abstract boolean esReferencia();
    public Token getToken() {
        return token;
    }
    @Override
    public String toString() {
        return nombre;
    }
    public boolean equals(Tipo o) {
        return nombre.equals(o.nombre);
    }
}
