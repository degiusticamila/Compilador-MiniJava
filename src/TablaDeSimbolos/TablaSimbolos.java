package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class TablaSimbolos {
    private HashMap<String,Clase> clases;
    private Clase claseActual;
    private Metodo metodoActual;

    public TablaSimbolos(){
        clases = new HashMap<String,Clase>();
    }
    public void insertarClase(String lexema, int numLine, Clase clase) throws ExcepcionSemantica {
        if(!claseDeclarada(lexema)){
            clases.put(lexema,clase);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine);
        }
    }
    public Metodo metodoActual(){
        return metodoActual;
    }
    public Clase getClaseActual(){
        return claseActual;
    }

    public void setClaseActual(Clase claseActual){
        this.claseActual = claseActual;
    }
    public void setMetodoActual(Metodo metodoActual){
        this.metodoActual = metodoActual;
    }
    public boolean claseDeclarada(String nombreClase){
        return clases.containsKey(nombreClase);
    }
    public void clases(){
        for(String s : clases.keySet()){
            System.out.println(s);
        }
    }


}
