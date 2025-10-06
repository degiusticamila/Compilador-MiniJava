package TablaDeSimbolos;

public class TipoPrimitivo extends Tipo {
    public TipoPrimitivo(String nombre) {
        super(nombre);
    }

    @Override
    public boolean esPrimitivo(){return true;}
    @Override
    public boolean esReferencia(){return false;}
}
