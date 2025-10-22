package AST.NodosEncadenado;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoVarEncadenada extends NodoEncadenado {
    private NodoEncadenado encadenado;
    public NodoVarEncadenada(Token nombre, NodoEncadenado encadenado) {
        super(nombre);
        this.encadenado = encadenado;
    }
    @Override
    public Tipo chequear(Tipo t) {
        return new TipoUniversal("Tipo universal");
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
            return nombre.getLexema();
        }
        return nombre.getLexema()+"."+encadenado.formatear();
    }

    @Override
    public void setEncadenado(NodoEncadenado encadenado) {
        this.encadenado = encadenado;
    }
}
