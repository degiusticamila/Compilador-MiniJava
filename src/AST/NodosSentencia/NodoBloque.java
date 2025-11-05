package AST.NodosSentencia;

import AST.NodosOperando.NodoOperando;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.TablaSimbolos;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NodoBloque extends NodoSentencia {
    private List<NodoSentencia> sentencias;
    private List<NodoVarLocal> variablesLocales;
    private NodoBloque nodoBloquePadre;
    public NodoBloque() {
        sentencias = new ArrayList<>();
        variablesLocales = new LinkedList<>();
       // nodoBloquePadre = new NodoBloqueVacio();
    }
    public void insertarVariable(NodoVarLocal nodo){

        //Controlar que no se este insertando una que ya existe
        if(variablesLocales.contains(nodo)){
           // throw new ExcepcionSemantica()
        }
        variablesLocales.addLast(nodo);
    }
    public List<NodoSentencia> getSentencias() {
        return sentencias;
    }
    public void imprimir(String prefijo){
        System.out.println(prefijo+ "Bloque {");
        for(NodoSentencia nodo: sentencias){
            nodo.imprimir(prefijo +" ");
        }
        System.out.println(prefijo+ "}");
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        NodoBloque bloqueAnterior = ts.getBloqueActual();

        ts.setBloqueActual(this);
        for(NodoSentencia s: sentencias){

            s.chequear();
        }
        ts.setBloqueActual(bloqueAnterior);
    }
    public List<NodoVarLocal> getVariablesLocales() {
        return variablesLocales;
    }
    public boolean variableDeclaradaEnAlgunBloque(String nombre) throws ExcepcionSemantica {
        NodoBloque bloqueActual = TablaSimbolos.getInstance().getBloqueActual();
        if(bloqueActual.variableLocalDeclarada(nombre)){
            return true;
        }
        else{
            if(!(bloqueActual.getNodoBloquePadre() instanceof NodoBloqueVacio)){
                bloqueActual = bloqueActual.getNodoBloquePadre();
                return bloqueActual.variableLocalDeclarada(nombre);
            }
           // return bloqueActual.variableLocalDeclarada(nombre);
        }
        return false;
    }
    public boolean variableLocalDeclarada(String nombre){;
        /*System.out.println();
        System.out.println("¿Buscando variable local? -> " + nombre);
        System.out.println(this.variablesLocales.toString());

         */
        for(NodoVarLocal nodoVarLocal: variablesLocales){
           /* System.out.println("Tengo declarada: " + nodoVarLocal.getNombreVarLocal());
            System.out.println(nodoVarLocal.getNombreVarLocal());

            */
            if(nodoVarLocal.getNombreVarLocal().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    public Tipo getTipoVariableLocalDeclarada(String nombre){
        for(NodoVarLocal nodoVarLocal: variablesLocales){
            if(nodoVarLocal.getNombreVarLocal().equals(nombre)){
                return nodoVarLocal.getTipoVarLocal();
            }
        }
        return null;
    }
    public boolean buscarDeclaradaEnBloques(String nombre){
        NodoBloque bloque = this; //arranco desde el actual
        while(bloque != null){
            for(NodoVarLocal var: bloque.variablesLocales){
                if(var.getNombreVarLocal().equals(nombre)){
                    return true;
                }
            }
            bloque = bloque.getNodoBloquePadre(); //subo
        }
        return false;
    }
    public NodoVarLocal getVariableLocal(String nombre){

        NodoBloque bloque = this; //arranco desde el actual
        while(bloque != null){
            for(NodoVarLocal var: bloque.variablesLocales){
                if(var.getNombreVarLocal().equals(nombre)){
                    return var;
                }
            }
            bloque = bloque.getNodoBloquePadre(); //subo
        }
        return null;
    }
    public Tipo buscarTipoVariableEnBloques(String nombre) {
        NodoBloque bloque = this; // empezamos desde el actual
        while (bloque != null) {
            for (NodoVarLocal var : bloque.variablesLocales) {
                if (var.getNombreVarLocal().equals(nombre)) {
                    return var.getTipoVarLocal();
                }
            }
            bloque = bloque.getNodoBloquePadre(); // subo
        }
        return null; // no se encontró
    }
    public void setNodoBloquePadre(NodoBloque nodoBloquePadre){
        this.nodoBloquePadre = nodoBloquePadre;
    }
    public NodoBloque getNodoBloquePadre(){
        return nodoBloquePadre;
    }
    public void generar(ArchivoSalida archivo){
        for(NodoSentencia sentencia: sentencias){
            sentencia.generar(archivo);
        }
    }
}
