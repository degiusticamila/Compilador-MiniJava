package TablaDeSimbolos;

public class TipoReferencia extends Tipo {

    public TipoReferencia(String nombre){
        super(nombre);
    }

    @Override
    public boolean esCompatible(Tipo t) {
        return false;
    }

    @Override
    public boolean esPrimitivo(){return false;}
    public boolean esReferencia(){return true;}

}
