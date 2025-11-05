package TablaDeSimbolos;

import Utils.Token;

public class Atributo implements Elemento{
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
    public Token getToken(){
        return nombre;
    }
    public String getNombre() {
        return nombre.getLexema();
    }
    public int getLinea() {
        return nombre.getNroLinea();
    }

    @Override
    public Token getModificador() {
        return null;
    }
}
