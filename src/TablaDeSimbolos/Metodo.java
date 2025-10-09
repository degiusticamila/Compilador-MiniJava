package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;

public class Metodo {
    private Tipo tipoRetorno;  //y si es void?
    private Token nombre;
    private HashMap<String, Parametro> parametros;
    private Token modificador;
    public Metodo(Token modificador,Tipo tipoRetorno, Token nombreMetodo){
        parametros = new HashMap<>();
        this.modificador = modificador;
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombreMetodo;
    }
    public void insertarParametro(String lexema,Parametro p,int numLine) throws ExcepcionSemantica {
        if(!parametroDeclarado(lexema)){
            p.setPosicion(parametros.size()+1);
            parametros.put(lexema,p);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine, "Parametro ya declarado");
        }
    }
    public boolean parametroDeclarado(String lexema){
        return parametros.containsKey(lexema);
    }
    public boolean esMetodoAbstracto(){
        return modificador != null && modificador.getLexema().equals("abstract");
    }
    public void getParametros() {
        if(!parametros.isEmpty()){
            for(String s : parametros.keySet()){
                System.out.println(s+" : "+parametros.get(s).toString());
            }
        }
    }
    @Override
    public String toString() {
        return "(" + modificador +","+ tipoRetorno + ", " + nombre.getLexema() +")";
    }
    public Token getModificador(){
        return modificador;
    }
    public Token getNombreMetodo(){
        return nombre;
    }
    public Tipo getTipoRetorno(){ return tipoRetorno;}

}
