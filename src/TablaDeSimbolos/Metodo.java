package TablaDeSimbolos;

import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoBloqueVacio;
import AST.NodosSentencia.NodoSentencia;
import Utils.Token;

import java.util.LinkedList;

public class Metodo implements Elemento{
    private Tipo tipoRetorno;  //y si es void?
    private Token nombre;
    private LinkedList<Parametro> parametros;
    private Token modificador;
    private NodoBloque bloque;

    public Metodo(Token modificador,Tipo tipoRetorno, Token nombreMetodo){
        parametros = new LinkedList<>();
        this.modificador = modificador;
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombreMetodo;
        this.bloque = new NodoBloqueVacio();
    }
    public void insertarParametro(String lexema,Parametro p,int numLine) throws ExcepcionSemantica {
        if(!parametroDeclarado(lexema)){
            p.setPosicion(parametros.size()+1);
            parametros.addLast(p);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine, "Parametro ya declarado");
        }
    }
    public boolean parametroDeclarado(String lexema){
        for(Parametro p : parametros){
            if(p.getNombre().equals(lexema)){
                return true;
            }
        }
        return false;
    }
    public boolean esMetodoAbstracto(){
        return modificador != null && modificador.getLexema().equals("abstract");
    }
    public boolean esMetodoEstatico(){
        return modificador != null && modificador.getLexema().equals("static");
    }
    public LinkedList<Parametro> getParametros() {
        return parametros;
    }
    @Override
    public String toString() {
        return "(" + modificador +","+ tipoRetorno + ", " + nombre.getLexema()+","+parametros +")";
    }
    public Token getModificador(){
        return modificador;
    }
    public Token getNombreMetodo(){
        return nombre;
    }
    public Tipo getTipoRetorno(){ return tipoRetorno;}
    public void insertarBloque(NodoBloque bloque, NodoBloque nodoBloquePadre){
        this.bloque = bloque;
        bloque.setNodoBloquePadre(nodoBloquePadre);
    }
    public NodoBloque getBloque(){
        if(!(bloque instanceof NodoBloqueVacio)){
            return bloque;
        }
        return new NodoBloqueVacio();
    }
    public Tipo getTipoParametro(String nombreParametro){
        for(Parametro p : parametros){
            if(p.getNombre().equals(nombreParametro)){
                return p.getTipo();
            }
        }
        return null;
    }
    public Parametro getParametro(String lexema){
        for(Parametro p : parametros){
            if(p.getNombre().equals(lexema)){
                return p;
            }
        }
        return null;
    }
    public Tipo getTipo(){
        return tipoRetorno;
    }
    public String getNombre(){
        return nombre.getLexema();
    }
    public int getLinea(){
        return nombre.getNroLinea();
    }
}
