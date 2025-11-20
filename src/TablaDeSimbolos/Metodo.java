package TablaDeSimbolos;

import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoBloqueVacio;
import AST.NodosSentencia.NodoSentencia;
import AST.NodosSentencia.NodoVarLocal;
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
    private int offsetThis;
    private int offsetMetodo;
    private Clase claseDeclarada;

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
    public Clase obtenerClase(){
        for(Clase clase : TablaSimbolos.tablaSimbolos.getClases().values()){
            for(Metodo metodo : clase.getMetodosPropios().values()){
                if(metodo.getNombre().equals(nombre.getLexema())){
                    return clase;
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
        TablaSimbolos.tablaSimbolos.setMetodoActual(this);
        generarEtiquetaMetodo(archivo);
        generarConstruirRA(archivo);
        generarBloque(archivo);
        generarRetornoMetodo(archivo);
        TablaSimbolos.tablaSimbolos.setMetodoActual(null);
    }
    public void generarEtiquetaMetodo(ArchivoSalida archivo){
        //si es metodo de una clase predefinida (System, String, Object)
        Clase clasePredefinida = this.esMetodoPredefinido();
       /* if(clasePredefinida != null){
            archivo.generar("lbl_"+nombre.getLexema()+"@"+clasePredefinida.getNombre().getLexema()+": "+Instrucciones.LOADFP);
        }
        else{
            System.out.println("la clase es "+TablaSimbolos.tablaSimbolos.getClaseActual().getNombre().getLexema());
            //es metodo propio
            //archivo.generar("lbl_"+nombre.getLexema()+"@"+TablaSimbolos.tablaSimbolos.getClaseActual().getNombre().getLexema()+": "+ Instrucciones.LOADFP);
            archivo.generar("lbl_"+nombre.getLexema()+"@"+TablaSimbolos.tablaSimbolos.getClaseActual().getNombre().getLexema()+": "+ Instrucciones.LOADFP);
        }

        */
    }
    public void generarConstruirRA(ArchivoSalida archivo){
        archivo.generar(""+Instrucciones.LOADSP);
        archivo.generar(""+Instrucciones.STOREFP);

        int cantidadParametros = parametros.size();

        System.out.println("Cantidad de Parametros en metodo "+nombre.getLexema()+": "+cantidadParametros);


        int cantidadVariablesLocales = bloque.getTodasLasVariablesLocales().size();
        System.out.println("Cantidad de Variables locales en metodo "+nombre.getLexema()+": "+cantidadVariablesLocales);
        if(cantidadVariablesLocales != 0){
            archivo.generar(""+Instrucciones.RMEM+" "+cantidadVariablesLocales);
        }

    }
    public void generarBloque(ArchivoSalida archivo){
        bloque.generar(archivo);
    }
    public void generarRetornoMetodo(ArchivoSalida archivo){
        archivo.generar("lbl_final_"+nombre.getLexema()+"@"+claseDeclarada.getNombre().getLexema()+": "+Instrucciones.NOP);


        int cantidadVariablesLocales = bloque.getTodasLasVariablesLocales().size();
        if(cantidadVariablesLocales != 0){
            archivo.generar(Instrucciones.FMEM+" "+cantidadVariablesLocales); //NUEVO RECIEN
        }

        archivo.generar(Instrucciones.STOREFP+"");


        int nParams = this.getParametros().size();
        if(!this.esMetodoEstatico()){
            archivo.generar(Instrucciones.RET +" "+(nParams+1)); //Pruebo removiendo el this

        }
        else{
            //no hay this
            archivo.generar(Instrucciones.RET +" "+nParams);
        }
        archivo.generar("");
    }
    public void calcularOffsets(){

        int cantidadParametros = parametros.size();

        if(!esMetodoEstatico()){
            this.offsetThis = 3;
            for(Parametro p : parametros){
                int i = p.getPosicion();
                int offset = cantidadParametros + 3 - i;
                p.setOffset(offset);
            }

        }
        else{
            this.offsetThis = 2;
            for(Parametro p : parametros){
                int i = p.getPosicion();
                int offset = cantidadParametros + 2 - i;
                p.setOffset(offset);
            }
        }


        int offsetVariablesLocales = 0;
        for(NodoVarLocal variableLocal : this.bloque.getTodasLasVariablesLocales()){
            variableLocal.setOffset(offsetVariablesLocales);
            offsetVariablesLocales--;
        }
    }
    public int getOffsetThis(){
        return offsetThis;
    }
    public void setOffsetThis(int offsetThis){
        this.offsetThis = offsetThis;
    }
    public int getOffsetMetodo(){
        return offsetMetodo;
    }
    public void setOffsetMetodo(int offsetMetodo){
        this.offsetMetodo = offsetMetodo;
    }

    public void setClaseDeclarada(Clase c) {
        this.claseDeclarada = c;
    }
    public Clase getClaseDeclarada() {
        return claseDeclarada;
    }
    public void setTipoRetorno(Tipo tipoRetorno){
        this.tipoRetorno = tipoRetorno;
    }
}
