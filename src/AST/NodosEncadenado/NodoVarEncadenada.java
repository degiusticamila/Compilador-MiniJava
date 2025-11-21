package AST.NodosEncadenado;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoVarEncadenada extends NodoEncadenado {
    protected boolean esLadoIzq = false;
    Token nombre;
    private NodoEncadenado encadenado;
    Atributo atributoEnTS;
    public NodoVarEncadenada(Token nombre, NodoEncadenado encadenado) {
        super(nombre);
        this.encadenado = encadenado;
        this.nombre = nombre;
    }

    @Override
    public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {
        System.out.println("Generando código de NodoVarEncadenada " + nombre.getLexema());

        int offsetAttr = atributoEnTS.getOffset();

        if (esLadoIzq) {
            archivo.generar(Instrucciones.SWAP + "");
            archivo.generar(Instrucciones.STOREREF + " " + offsetAttr);
        } else {

            archivo.generar(Instrucciones.LOADREF + " " + offsetAttr);
        }

        if (!(encadenado instanceof NodoEncadenadoVacio)) {
            encadenado.generar(archivo);
        }
    }

    @Override
    public Tipo chequear(Tipo t) throws ExcepcionSemantica {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        System.out.println();
        System.out.println(t.getNombre());
        System.out.println();
        System.out.println();
        System.out.println(super.nombre.getLexema());
        System.out.println();
        System.out.println("Chequeando atributo " + nombre.getLexema() + " sobre tipo " + t.getNombre());
        if(t.esReferencia() && tablaSimbolos.obtenerClase(t.getNombre()).atributoDeclarado(super.nombre.getLexema())){

            atributoEnTS = tablaSimbolos.obtenerClase(t.getNombre()).getAtributo(super.nombre.getLexema());
        }else{
            //
            throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(),"No existe atributo "+super.nombre.getLexema());
        }
        if(!(encadenado instanceof NodoEncadenadoVacio)){
           return encadenado.chequear(atributoEnTS.getTipo());
        }

        return atributoEnTS.getTipo();
    }

    public boolean tieneEncadenado(){
       if(!(encadenado instanceof NodoEncadenadoVacio)){
           return true;
       }
       return false;
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

    @Override
    public NodoEncadenado getUltimoEncadenado() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return this;
        }
        else{
            return encadenado.getUltimoEncadenado();
        }
    }

    public boolean getLadoIzq(){
        return esLadoIzq;
    }
    public void setEsLadoIzq(){
        esLadoIzq = !esLadoIzq;
    }
}
