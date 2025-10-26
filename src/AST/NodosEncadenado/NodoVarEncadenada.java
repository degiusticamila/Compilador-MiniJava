package AST.NodosEncadenado;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoVarEncadenada extends NodoEncadenado {
    Token nombre;
    private NodoEncadenado encadenado;
    Atributo atributoEnTS;
    public NodoVarEncadenada(Token nombre, NodoEncadenado encadenado) {
        super(nombre);
        this.encadenado = encadenado;
    }
    @Override
    public Tipo chequear(Tipo t) throws ExcepcionSemantica {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        System.out.println("Chequeando var encadenada");
        System.out.println();
        System.out.println(t.getNombre());
        System.out.println();
        if(t.esReferencia() && tablaSimbolos.obtenerClase(t.getNombre()).atributoDeclarado(super.nombre.getLexema())){
            atributoEnTS = tablaSimbolos.obtenerClase(t.getNombre()).getAtributo(super.nombre.getLexema());

        }else{
            throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(),"No existe atributo "+super.nombre.getLexema());
        }
        if(!(encadenado instanceof NodoEncadenadoVacio)){
           return encadenado.chequear(atributoEnTS.getTipo());
        }

        return atributoEnTS.getTipo();
    }

    @Override
    public void imprimir(String prefijo) {

        System.out.println(prefijo+nombre.getLexema());
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            System.out.println(".");
            encadenado.imprimir("");
        }
    }

    @Override
    public String formatear() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return super.nombre.getLexema();
        }
        return super.nombre.getLexema()+"."+encadenado.formatear();
    }

    @Override
    public void setEncadenado(NodoEncadenado encadenado) {
        this.encadenado = encadenado;
    }
}
