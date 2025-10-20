package TablaDeSimbolos;

public class TipoUniversal extends Tipo{

    public TipoUniversal(String tipoUniversal) {
        super(tipoUniversal);
    }

    @Override
    public boolean esCompatible(Tipo t) {
        return false;
    }

    @Override
    public boolean esPrimitivo() {
        return false;
    }

    @Override
    public boolean esReferencia() {
        return false;
    }
}
