package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;
import java.util.HashSet;

public class Constructor {
    private Token nombre;
    private HashMap<String, Parametro> parametros;

    public Constructor(Token nombre){
        parametros = new  HashMap<>();
        this.nombre = nombre;
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
    public Token getNombre(){
        return nombre;
    }
}
