package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;

public class Metodo {
    private Token tipoRetorno;  //y si es void?
    private Token nombre;
    private HashMap<String, Parametro> parametros;
    private Token modificador;
    public Metodo(Token tipoRetorno, Token nombreMetodo){
        parametros = new HashMap<>();
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombreMetodo;
    }
    public Metodo(Token tipoRetorno, Token nombreMetodo, Token modificador){
        parametros = new HashMap<>();
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombreMetodo;
        this.modificador = modificador;
    }
    public void insertarParametro(String lexema,Parametro p,int numLine) throws ExcepcionSemantica {
        if(!parametroDeclarado(lexema)){
            parametros.put(lexema,p);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine);
        }
    }
    public boolean parametroDeclarado(String lexema){
        return parametros.containsKey(lexema);
    }
}
