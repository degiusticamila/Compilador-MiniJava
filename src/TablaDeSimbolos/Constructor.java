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
            p.setPosicion(parametros.size()+1);
            parametros.put(lexema,p);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine,"Parametro ya declarado");
        }
    }
    public boolean parametroDeclarado(String lexema){
        return parametros.containsKey(lexema);
    }
    public Token getNombreConstructor(){
        return nombre;
    }
    public void getParametros(){
        if(!parametros.isEmpty()){
            for(String s : parametros.keySet()){
                System.out.println(s+" : "+parametros.get(s).toString());
            }
        }
    }
    public String toString() {
        return ("("+nombre.toString()+")");
    }
}
