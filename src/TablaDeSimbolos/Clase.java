package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;
import java.util.HashSet;

public class Clase {

    private HashMap<String, Atributo> atributos;
    private HashMap<String, Metodo> metodos;
    private Constructor constructor;

    private Token modificador;
    private Token nombre;
    private Token herencia;

    public Clase(Token nombre,Token modificador){
        atributos = new HashMap<>();
        metodos = new HashMap<>();
        this.nombre = nombre;
        this.modificador = modificador;
    }
    public void setHerencia(Token herencia) throws ExcepcionSemantica {
        //preguntar si el nombre del ancestro es distinto que el de la clase
        //chequear que esa clase exista
        if(nombre.getLexema().equals(herencia.getLexema())){
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea());
        }
        else{
            this.herencia = herencia;
        }
    }
    public void insertarMetodo(Token nombreMetodo,Metodo m) throws ExcepcionSemantica {
        if(!metodoDeclarado(nombreMetodo.getLexema())){
            metodos.put(nombreMetodo.getLexema(), m);
        }
        else{
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea());
        }
    }
    public void insertarAtributo(Token atributo, Atributo a) throws ExcepcionSemantica {
        if(!atributoDeclarado(atributo.getLexema())){
            atributos.put(atributo.getLexema(), a);
        }
        else{
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea());
        }
    }
    public void insertarConstructor(Token nombreConstructor, Constructor c) throws ExcepcionSemantica {
        if(!constructorDeclarado() && nombre.getLexema().equals(nombreConstructor.getLexema())){
            this.constructor = c;
        }
        else{
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea());
        }
    }
    public boolean constructorDeclarado(){
        return constructor != null;
    }
    public boolean metodoDeclarado(String metodo){
        return metodos.containsKey(metodo);
    }
    public boolean atributoDeclarado(String atributo){
        return atributos.containsKey(atributo);
    }
    public Constructor getConstructor(){
        return constructor;
    }
}
