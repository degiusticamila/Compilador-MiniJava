package TablaDeSimbolos;

public abstract class Tipo {
    protected String nombre;

    public Tipo(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public abstract boolean esPrimitivo();
    public abstract boolean esReferencia();

    @Override
    public String toString() {
        return nombre;
    }
}
