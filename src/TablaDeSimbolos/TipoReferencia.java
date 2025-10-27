package TablaDeSimbolos;

import Utils.Token;

public class TipoReferencia extends Tipo {
    public static final TipoReferencia NULL = new TipoReferencia("null");
    public TipoReferencia(Token tokenTipo){
        super(tokenTipo.getLexema(), tokenTipo);
    }
    public TipoReferencia(String nombre){
        super(nombre, new Token(nombre,nombre,-1));
    }

    @Override
    public boolean esCompatible(Tipo t) {
        System.out.println("entro a es compatible de referencia");
        if(this.equals(TipoReferencia.NULL)){
            return t.esReferencia();
        }

        //t = tipo destino (izquierda)
        // this = tipo origen (derecha)
        if(this.nombre.equals(t.getNombre())){
            return true;
        }
        if(!t.esReferencia()){
            return false;
        }
        //buscar si this es subclase de t
        TablaSimbolos ts = null;
        try {
            ts = TablaSimbolos.getInstance();
        } catch (ExcepcionSemantica e) {
            throw new RuntimeException(e);
        }
        Clase claseActual = ts.obtenerClase(this.nombre);
        while(claseActual != null && claseActual.getHerencia() != null){
            String nombrePadre = claseActual.getHerencia().getLexema();
            if(nombrePadre.equals(t.getNombre())){
                return true;
            }
            claseActual = ts.obtenerClase(nombrePadre);
        }
        return false;
    }
    @Override

    public boolean esPrimitivo(){return false;}
    public boolean esReferencia(){return true;}

}
