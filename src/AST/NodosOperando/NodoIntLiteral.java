package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import Utils.Token;

public class NodoIntLiteral extends NodoOperando {
    Token nombre;

    public NodoIntLiteral(Token nombre){
        this.nombre = nombre;
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
    public void imprimir(String prefijo) {
            System.out.print(prefijo+ nombre.getLexema());
    }

    @Override
    public boolean tieneEncadenado() {
        return false;
    }

    @Override
    public String formatear() {
        return nombre.getLexema();
    }

    @Override
    public Tipo chequear() {
        return new TipoPrimitivo("int");
    }

    @Override
    public void generar(ArchivoSalida archivo) {
        System.out.println("Generando código NodoIntLiteral "+nombre.getLexema());
        archivo.generar(Instrucciones.PUSH+" "+nombre.getLexema());
        System.out.println();
    }

    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
    }

    public Token getNombre(){
        return nombre;
    }
}
