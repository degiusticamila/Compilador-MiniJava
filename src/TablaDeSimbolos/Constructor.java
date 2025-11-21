package TablaDeSimbolos;

import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoBloqueVacio;
import ArchivoSalida.ArchivoSalida;
import Utils.Token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Constructor {
    private Token nombre;
    private LinkedList<Parametro> parametros;
    private NodoBloque bloque;
    private Clase claseDeclarada;
    public Constructor(Token nombre,Clase claseDeclarada){
        parametros = new LinkedList<>();
        this.claseDeclarada = claseDeclarada;
        this.nombre = nombre;
        this.bloque = new NodoBloqueVacio();
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
    public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {
        System.out.println("Clase constructor");
        archivo.generar("LOADFP");
        archivo.generar("LOADSP");
        archivo.generar("STOREFP");

        if (!(bloque instanceof NodoBloqueVacio)) {
            bloque.generar(archivo);
        }

        archivo.generar("STOREFP");
        archivo.generar("RET 0");
    }
   public boolean parametroDeclarado(String lexema){

       //return parametros.containsKey(lexema);
       for(Parametro p : parametros){
           if(p.getNombre().equals(lexema)){
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
    public NodoBloque getBloque(){
        if(!(bloque instanceof NodoBloqueVacio)){
            return bloque;
        }
        return new NodoBloqueVacio();
    }
    public NodoBloque getBloqueConstructor(){
        return bloque;
    }
}
