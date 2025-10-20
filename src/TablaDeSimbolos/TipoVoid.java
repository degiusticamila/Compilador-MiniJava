package TablaDeSimbolos;

public class TipoVoid extends Tipo {
    public TipoVoid() {
        super("void");
    }

    @Override
    public boolean esCompatible(Tipo t) {
        if(t instanceof TipoVoid){
            return true;
        }
        return false;
    }

    @Override
    public boolean esPrimitivo() { return true; }
    @Override
    public boolean esReferencia() { return false; }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof TipoVoid;
    }
}
