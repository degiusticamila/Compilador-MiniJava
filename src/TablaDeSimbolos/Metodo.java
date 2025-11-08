package TablaDeSimbolos;

import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoBloqueVacio;
import AST.NodosSentencia.NodoSentencia;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import Utils.Token;

import java.util.LinkedList;

public class Metodo implements Elemento{
    private Tipo tipoRetorno;  //y si es void?
    private Token nombre;
    private LinkedList<Parametro> parametros;
    private Token modificador;
    private NodoBloque bloque;


    public Metodo(Token modificador,Tipo tipoRetorno, Token nombreMetodo){
        parametros = new LinkedList<>();
        this.modificador = modificador;
        this.tipoRetorno = tipoRetorno;
        this.nombre = nombreMetodo;
        this.bloque = new NodoBloqueVacio();
    }
    public void insertarParametro(String lexema,Parametro p,int numLine) throws ExcepcionSemantica {
        if(!parametroDeclarado(lexema)){
            p.setPosicion(parametros.size()+1);
            parametros.addLast(p);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine, "Parametro ya declarado");
        }
    }
    public boolean parametroDeclarado(String lexema){
        for(Parametro p : parametros){
            if(p.getNombre().equals(lexema)){
                return true;
            }
        }
        return false;
    }
    public boolean esMetodoAbstracto(){
        return modificador != null && modificador.getLexema().equals("abstract");
    }
    public boolean esMetodoEstatico(){
        return modificador != null && modificador.getLexema().equals("static");
    }
    public Clase esMetodoPredefinido(){
        for(Clase clasePredefinida : TablaSimbolos.tablaSimbolos.getClasesPredefinidas().values()){
            for(Metodo metodoPredefino : clasePredefinida.getMetodosPropios().values()){
                if(metodoPredefino.getNombre().equals(nombre.getLexema())){
                    return clasePredefinida;
                }
            }
        }
        return null;
    }
    public LinkedList<Parametro> getParametros() {
        return parametros;
    }
    @Override
    public String toString() {
        return "(" + modificador +","+ tipoRetorno + ", " + nombre.getLexema()+","+parametros +")";
    }
    public Token getModificador(){
        return modificador;
    }
    public Token getNombreMetodo(){
        return nombre;
    }
    public Tipo getTipoRetorno(){ return tipoRetorno;}
    public void insertarBloque(NodoBloque bloque, NodoBloque nodoBloquePadre){
        this.bloque = bloque;
        bloque.setNodoBloquePadre(nodoBloquePadre);
    }
    public NodoBloque getBloque(){
        if(!(bloque instanceof NodoBloqueVacio)){
            return bloque;
        }
        return new NodoBloqueVacio();
    }
    public Tipo getTipoParametro(String nombreParametro){
        for(Parametro p : parametros){
            if(p.getNombre().equals(nombreParametro)){
                return p.getTipo();
            }
        }
        return null;
    }
    public Parametro getParametro(String lexema){
        for(Parametro p : parametros){
            if(p.getNombre().equals(lexema)){
                return p;
            }
        }
        return null;
    }
    public Tipo getTipo(){
        return tipoRetorno;
    }
    public String getNombre(){
        return nombre.getLexema();
    }
    public int getLinea(){
        return nombre.getNroLinea();
    }
    public void generar(ArchivoSalida archivo){
        generarEtiquetaMetodo(archivo);
        generarConstruirRA(archivo);
        generarBloque(archivo);
        generarRetornoMetodo(archivo);
    }
    public void generarEtiquetaMetodo(ArchivoSalida archivo){
        //si es metodo de una clase predefinida (System, String, Object)
        Clase clasePredefinida = this.esMetodoPredefinido();
        if(clasePredefinida != null){
            archivo.generar("lbl_"+nombre.getLexema()+"@"+clasePredefinida.getNombre().getLexema()+": "+Instrucciones.LOADFP + " #Apila el valor del registro fp");
        }
        else{
            //es metodo propio
            archivo.generar("lbl_"+nombre.getLexema()+"@"+TablaSimbolos.tablaSimbolos.getClaseActual().getNombre().getLexema()+": "+ Instrucciones.LOADFP + " #Apila el valor del registro fp");
        }
    }
    public void generarConstruirRA(ArchivoSalida archivo){
        //archivo.generar(""+Instrucciones.LOADSP+" #Apila el valor del registro sp");
        archivo.generar(""+Instrucciones.LOADSP);
        //archivo.generar(""+Instrucciones.STOREFP+" #Almacena el tope de la pila en el registro fp");
        archivo.generar(""+Instrucciones.STOREFP);
    }
    public void generarBloque(ArchivoSalida archivo){
        bloque.generar(archivo);
    }
    public void generarRetornoMetodo(ArchivoSalida archivo){
        archivo.generar(Instrucciones.STOREFP+" #Apila el valor del registro sp");
        archivo.generar(Instrucciones.RET +" "+this.getParametros().size());
    }

}
