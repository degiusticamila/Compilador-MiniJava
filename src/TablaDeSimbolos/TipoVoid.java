package TablaDeSimbolos;

public class TipoVoid extends Tipo {
    public TipoVoid() {
        super("void");
    }
    @Override
    public boolean esPrimitivo() { return true; }
    @Override
    public boolean esReferencia() { return false; }
}
