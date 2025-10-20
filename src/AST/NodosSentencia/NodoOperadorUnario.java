package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import Utils.Token;

public class NodoOperadorUnario extends NodoExpresion{
    private NodoExpresion ladoDerecho;
    private Token nombre;

    public NodoOperadorUnario(Token nombre, NodoExpresion ladoDerecho) {
        this.nombre = nombre;
        this.ladoDerecho = ladoDerecho;
    }

    @Override
    public String formatear() {
        return nombre.getLexema() + ladoDerecho.formatear();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        System.out.println("chequear de NodoOperadorUnario");
        Tipo tipoOperando = ladoDerecho.chequear();
        if(!nombre.getLexema().equals("!")){
            //si se usa una variable tiene que estar, pero cómo obtengo que a1 es alcanzable?

            //el tipo de lado derecho debe ser entero
            if(!tipoOperando.esCompatible(new TipoPrimitivo("int"))){
                throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Tipos incompatibles");
            }
            return new TipoPrimitivo("int");
        }
        else{
            if(!tipoOperando.esCompatible(new TipoPrimitivo("boolean"))){
                throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Tipos incompatibles");
            }
            return new TipoPrimitivo("boolean");
        }
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "OperadorUnario (" + nombre.getLexema() + ")");
        System.out.println(prefijo + "  R -> " + ladoDerecho.formatear());
        /*
        System.out.println(prefijo + "ExpUnaria (" + operador.getLexema() + ")");
        if (ladoDerecho != null) {
            ladoDerecho.imprimir(prefijo + "  ");
        }

         */
    }

    public Token getNombre(){
        return nombre;
    }

    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    public void setLadoDerecho(NodoExpresion ladoDerecho){
        this.ladoDerecho = ladoDerecho;
    }
}
