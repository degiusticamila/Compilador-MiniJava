package TablaDeSimbolos;

import AST.NodosSentencia.NodoBloque;
import Utils.Token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Constructor {
    private Token nombre;
    private LinkedList<Parametro> parametros;
    private NodoBloque bloque;
    public Constructor(Token nombre){
        parametros = new LinkedList<>();
        this.nombre = nombre;
    }
    public void insertarParametro(String lexema,Parametro p,int numLine) throws ExcepcionSemantica {
        if(!parametroDeclarado(lexema)){
            p.setPosicion(parametros.size()+1);
            parametros.addLast(p);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine,"Parametro ya declarado");
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
    public Token getNombreConstructor(){
        return nombre;
    }
    public LinkedList<Parametro> getParametros(){
        return parametros;
    }
    public String toString() {
        return ("("+nombre.toString()+")");
    }

    public void insertarBloque(NodoBloque bloque, NodoBloque nodoBloquePadre) {
        this.bloque = bloque;
        bloque.setNodoBloquePadre(nodoBloquePadre);
    }
}
