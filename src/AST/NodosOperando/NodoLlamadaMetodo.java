package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoSentencia;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.*;
import Utils.Token;

import java.util.List;

public class NodoLlamadaMetodo extends NodoOperando{
    private Token nombre;
    private List<NodoExpresion> argumentos;
    private NodoEncadenado encadenado;
    private Elemento referenciaTS;
    public NodoLlamadaMetodo(Token nombre, List<NodoExpresion> argumentos) {
        this.nombre = nombre;
        this.argumentos = argumentos;
        encadenado = new NodoEncadenadoVacio();
    }
    @Override
    public Token getNombre() {
        return null;
    }

    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    @Override
    public void setLadoDerecho(NodoExpresion nodoExpresion) {

    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + nombre.getLexema()+"(");
        for(int i = 0; i < argumentos.size(); i++){
            System.out.println(argumentos.get(i).formatear());
            if(i < argumentos.size()-1){
                System.out.println(", ");
            }
        }
        System.out.println(")");
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            System.out.println(".");
            encadenado.imprimir(prefijo);
        }
    }

    @Override
    public boolean tieneEncadenado() {
        return !(encadenado instanceof NodoEncadenadoVacio);
    }

    @Override
    public String formatear() {
        StringBuilder s = new StringBuilder(nombre.getLexema()+"(");
        for(int i = 0; i < argumentos.size(); i++){
            s.append(argumentos.get(i).formatear());
            if(i < argumentos.size()-1){
                s.append(", ");
            }
        }
         s.append(")");

        if(!(encadenado instanceof NodoEncadenadoVacio)){
            s.append(".").append(encadenado.formatear());
        }
        return s.toString();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase claseActual = ts.getClaseActual();
        Metodo metodo = claseActual.getMetodo(nombre.getLexema());
        if(metodo == null){
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(),"Metodo no declarado");
        }
        if(!metodo.esMetodoEstatico() && ts.getMetodoActual().esMetodoEstatico()){
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(),"Metodo dinamico dentro de metodo estatico");
        }
        //System.out.println("metodo actual: "+ts.getMetodoActual().getNombreMetodo().getLexema());

       /* if(ts.getMetodoActual().esMetodoEstatico() ){
       //PREGUNTAR
            System.out.println("entro a tirar la excepcion");
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(), "No se puede invocar un metodo dentro de un metodo estático");
        }

        */

        if(!claseActual.metodoDeclarado(nombre.getLexema())){
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(), "Método "+nombre.getLexema()+" no declarado en la clase "+claseActual.getNombre().getLexema());
        }
        if(argumentos.size() != metodo.getParametros().size()){
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(),
                    "Cantidad de argumentos incorrecta para '"+nombre.getLexema()+
                            "'. Se esperaban "+metodo.getParametros().size()+" y se recibieron "+argumentos.size());
        }
        for(int i = 0; i < argumentos.size(); i++){
            Tipo tipoActual = argumentos.get(i).chequear();
            Tipo tipoFormal = metodo.getParametros().get(i).getTipo();

            if(!tipoActual.esCompatible(tipoFormal)){  // o tipoActual.conformaCon(tipoFormal)
                throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(),
                        "El argumento "+(i+1)+" del método '"+nombre.getLexema()+
                                "' no es compatible: se esperaba "+tipoFormal+" y se recibió "+tipoActual);
            }
        }
        Tipo tipoRetorno = metodo.getTipoRetorno();
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoRetorno);
        }
        return tipoRetorno;
    }
    public void setEncadenado(NodoEncadenado nodoEncadenado) {
        this.encadenado = nodoEncadenado;
    }
    public void obtenerModificador(String nombreLlamadaMetodo) throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();

    }
    public NodoEncadenado getUltimoEncadenado() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return encadenado;
        }
        else{
            return encadenado.getUltimoEncadenado();
        }
    }
    public void setearReferenciaTs() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Metodo metodoActual = ts.getMetodoActual();
        NodoBloque bloqueActual = metodoActual.getBloque();
    }
    public void generar(ArchivoSalida archivo){
        System.out.println("Generando codigo para NodoLlamadaMetodo "+nombre.getLexema());
        Clase claseActual = TablaSimbolos.tablaSimbolos.getClaseActual();
        Metodo metodo = claseActual.getMetodo(nombre.getLexema());
        Clase claseDelMetodo = metodo.getClaseDeclarada();
        Tipo tipoRetorno = metodo.getTipoRetorno();

        boolean esVoid = tipoRetorno instanceof TipoVoid;
        boolean esEstatico = metodo.esMetodoEstatico();


        if(!esVoid){
            archivo.generar(Instrucciones.RMEM+" 1");
        }

        if(!esEstatico){
            archivo.generar(Instrucciones.LOAD+" 3"); //cargo this
        }

        for(NodoExpresion arg : argumentos){
            arg.generar(archivo);
            if(!esEstatico){
                archivo.generar(Instrucciones.SWAP+"");
            }
        }

        if(esEstatico){
            archivo.generar("PUSH lbl_"+nombre.getLexema()+"@"+claseDelMetodo.getNombre().getLexema());
            archivo.generar(""+ Instrucciones.CALL);
        }
        else{
            archivo.generar(Instrucciones.DUP+"");                  //duplico this
            archivo.generar(Instrucciones.LOADREF+" 0");            //Cargo VT
            int offsetMetodo = metodo.getOffsetMetodo();
            archivo.generar(Instrucciones.LOADREF+" "+offsetMetodo); //Acceso al metodo en la VT
            archivo.generar(Instrucciones.CALL+"");
        }

        if(!(encadenado instanceof NodoEncadenadoVacio)){
            encadenado.generar(archivo);
        }
    }

    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
    }

}
