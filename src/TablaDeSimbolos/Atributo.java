package TablaDeSimbolos;

import Utils.Token;

public class Atributo implements Elemento{
    private Clase claseDeclarada;
    private Tipo tipo;
    private Token nombre;
    private int offset;
    public Atributo(Tipo tipo, Clase claseDeclarada, Token nombre) {
        this.tipo = tipo;
        this.claseDeclarada = claseDeclarada;
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
    public Clase getClaseDeclarada() {
        return claseDeclarada;
    }
    @Override
    public Token getModificador() {
        return null;
    }

    public int getOffset(){
        return offset;
    }
    public void setOffset(int offset){
        this.offset = offset;
    }
}
