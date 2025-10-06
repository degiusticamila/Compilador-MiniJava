package TablaDeSimbolos;

public class TipoReferencia extends Tipo {

    public TipoReferencia(String nombre){
        super(nombre);
    }

    @Override
    public boolean esPrimitivo(){return false;}
    public boolean esReferencia(){return true;}

}
