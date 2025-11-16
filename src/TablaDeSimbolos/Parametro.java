package TablaDeSimbolos;

import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import Utils.Token;

import java.sql.SQLOutput;

public class Parametro extends OffsetElemento implements Elemento{
    private Tipo tipo;
    private Token nombre;
    private int posicion;
    private int offset;

    public Parametro(Tipo tipo, Token nombre, int posicion) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.posicion = posicion;
    }
    public String toString() {
        return ("("+tipo.toString()+", "+nombre.toString()+", "+posicion+")");
    }
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    public Token getToken() {
        return nombre;
    }
    public String  getNombre() {
        return nombre.getLexema();
    }
    public int getPosicion() {
        return posicion;
    }
    public boolean equals(Parametro p){
        System.out.println("tipo1"+tipo);
        System.out.println("tipo2"+p);
        return tipo.equals(p.tipo) && nombre.getLexema().equals(p.nombre.getLexema());
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public int getLinea() {
        return nombre.getNroLinea();
    }

    @Override
    public Token getModificador() {
        return null;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int n) {
        this.offset = n;
    }

    public void generar(ArchivoSalida archivoSalida){
        System.out.println("Generando codigo del Parametro "+nombre.getLexema());
        archivoSalida.generar(Instrucciones.STORE+ " "+offset);
    }
}
