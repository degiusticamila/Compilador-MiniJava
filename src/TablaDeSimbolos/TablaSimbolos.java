package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;

public class TablaSimbolos {
    private HashMap<String,Clase> clases;
    private HashMap<String,Clase> clasesPredefinidas;
    private Clase claseActual;
    private Metodo metodoActual;

    public TablaSimbolos(){
        clasesPredefinidas = new HashMap<>();
        insertarClasesPredefinidas();
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
    public Metodo getMetodoActual(){
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

    private void insertarClasesPredefinidas(){
        Token nombreClaseObject = new Token("idClase", "Object", -1);
        Token nombreClaseSystem = new Token("idClase", "System", -1);
        Token nombreClaseString = new Token("idClase", "String", -1);

        clasesPredefinidas.put("Object",new Clase(nombreClaseObject,null));
        clasesPredefinidas.put("System",new Clase(nombreClaseSystem,null));
        clasesPredefinidas.put("String",new Clase(nombreClaseString,null));
    }
    public void consolidacion() throws ExcepcionSemantica {
        for(String clase : clases.keySet()){
            clases.get(clase).consolidarClase();
        }
    }

}
