package TablaDeSimbolos;

import Utils.Token;

public class TipoPrimitivo extends Tipo {
    public static final Tipo NULL = new TipoPrimitivo(new Token("null", "null", -1));
    public TipoPrimitivo(Token tokenTipo) {
        super(tokenTipo.getLexema(), tokenTipo);
    }
    public TipoPrimitivo(String nombre){


        super(nombre, new Token(nombre, nombre, -1));
    }
    @Override
    public boolean esCompatible(Tipo t) {

       return this.nombre.equals(t.nombre);

    }

    @Override
    public boolean esPrimitivo(){return true;}
    @Override
    public boolean esReferencia(){return false;}

}
