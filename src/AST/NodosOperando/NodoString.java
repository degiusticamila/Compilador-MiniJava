package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoReferencia;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoString extends NodoOperando{
    private Token nombre;
    private String label;
    public NodoString(Token token) {
        this.nombre= token;
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

    @Override
    public String formatear() {
        return nombre.getLexema();
    }

    @Override
    public Tipo chequear() {
        return new TipoReferencia("String");
    }



    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
    }

    @Override
    public Token getNombre() {
        return nombre;
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.print(prefijo+nombre.getLexema());
    }

    @Override
    public boolean tieneEncadenado() {
        return false;
    }
    @Override
    public void generar(ArchivoSalida archivo) {
        setLabel();
        archivo.generar(Instrucciones.PUSH+" "+this.label);
    }
    public void setLabel(){
        this.label = "label_str"+nombre.getNroLinea();
    }
    public String getLabel(){
        return this.label;
    }
}
