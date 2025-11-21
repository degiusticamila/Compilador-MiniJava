package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.*;
import Utils.Token;

    public class NodoThis extends NodoExpresion {
    private Token tokenThis;
    private NodoEncadenado encadenado;
    public NodoThis(Token tokenThis) {
        this.tokenThis = tokenThis;
        this.encadenado = new NodoEncadenadoVacio();
    }
    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    @Override
    public void setLadoDerecho(NodoExpresion nodoExpresion) {

    }


    public Token getNombre() {
        return tokenThis;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.print(prefijo + tokenThis.getLexema());
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            System.out.println(".");
            encadenado.imprimir("");
        }
        System.out.println();
    }


    public boolean tieneEncadenado() {
        if (!(encadenado instanceof NodoEncadenadoVacio)) {
            return true;
        }
        return false;
    }

        @Override
    public String formatear() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return tokenThis.getLexema();
        }


        return tokenThis.getLexema() + "." + encadenado.formatear();
    }
    public NodoEncadenado getUltimoEncadenado() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return encadenado;
        }
        else{
            return encadenado.getUltimoEncadenado();
        }
    }
    @Override
    public Tipo chequear() throws ExcepcionSemantica {


        /*El tipo de this es el mismo que el de la clase en que se esta utilizando, y no es posible utilizarlo en
        metodos estaticos.*/
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        if(tablaSimbolos.getMetodoActual().esMetodoEstatico()){
            throw new ExcepcionSemantica(tokenThis.getLexema(), tokenThis.getNroLinea(), "No es posible invocar a this en métodos estáticos");
        }
        Clase clase = tablaSimbolos.getClaseActual();
        if(clase == null){
            throw new ExcepcionSemantica(tokenThis.getLexema(), tokenThis.getNroLinea(), "No existe clase");
        }
        Tipo tipoThis =new TipoReferencia(clase.getNombre().getLexema());

        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoThis);
        }
        return tipoThis;

    }

        @Override
        public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {
            archivo.generar("LOAD 3");
            if(!(encadenado instanceof NodoEncadenadoVacio)){
                encadenado.generar(archivo);
            }
        }

        @Override
        public String nombreSentencia() {
            return tokenThis.getLexema();
        }

        public void setEncadenado(NodoEncadenado nodoEncadenado) {
        this.encadenado = nodoEncadenado;
    }
}
