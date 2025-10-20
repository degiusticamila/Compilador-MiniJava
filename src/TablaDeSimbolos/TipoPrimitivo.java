package TablaDeSimbolos;

public class TipoPrimitivo extends Tipo {
    public static final Tipo NULL = new TipoPrimitivo("null");

    public TipoPrimitivo(String nombre) {
        super(nombre);
    }

    @Override
    public boolean esCompatible(Tipo t) {
        if(nombre.equals(t.nombre)){
            return true;
        }
        else{
            throw new Error("Tipos no compatible"+nombre+t.nombre);
        }
    }

    @Override
    public boolean esPrimitivo(){return true;}
    @Override
    public boolean esReferencia(){return false;}

}
