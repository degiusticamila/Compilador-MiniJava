package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;
import java.util.LinkedList;

public class Metodo {
    private Tipo tipoRetorno;  //y si es void?
    private Token nombre;
    //private HashMap<String, Parametro> parametros;
    private LinkedList<Parametro> parametros;
    private Token modificador;
    public Metodo(Token modificador,Tipo tipoRetorno, Token nombreMetodo){
        //parametros = new HashMap<>();
        parametros = new LinkedList<>();
        this.modificador = modificador;
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombreMetodo;
    }
    public void insertarParametro(String lexema,Parametro p,int numLine) throws ExcepcionSemantica {
        if(!parametroDeclarado(lexema)){
            p.setPosicion(parametros.size()+1);
           // parametros.put(lexema,p);
            parametros.addLast(p);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine, "Parametro ya declarado");
        }
    }
    public boolean parametroDeclarado(String lexema){

        //return parametros.containsKey(lexema);
        for(Parametro p : parametros){
            if(p.getNombre().getLexema().equals(lexema)){
                return true;
            }
        }
        return false;
    }

    public boolean esMetodoAbstracto(){
        return modificador != null && modificador.getLexema().equals("abstract");
    }
    /*public HashMap<String, Parametro> getParametros() {
        return parametros;
    }
     */
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

}
