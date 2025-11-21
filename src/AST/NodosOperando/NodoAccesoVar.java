package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoVarLocal;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoAccesoVar extends NodoOperando {
    Token nombre;
    NodoEncadenado encadenado;
    private Elemento referenciaTS;
    public NodoAccesoVar(Token nombre){
        this.nombre = nombre;
        encadenado = new NodoEncadenadoVacio();
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
    public Token getNombre() {
        return nombre;
    }

    public void imprimir(String prefijo) {
        System.out.print(prefijo + nombre.getLexema());
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            System.out.println(".");
            encadenado.imprimir("");
        }
        System.out.println();
    }

    @Override
    public boolean tieneEncadenado() {
        if (!(encadenado instanceof NodoEncadenadoVacio)) {
            return true;
        }
        return false;
    }

    @Override
    public String formatear() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return nombre.getLexema();
        }
        return nombre.getLexema() + "." + encadenado.formatear();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {

        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase claseActual = ts.getClaseActual();
        Metodo metodoActual = ts.getMetodoActual();
        NodoBloque bloqueActual = ts.getBloqueActual();
        Tipo tipoBase;
        NodoVarLocal varLocal = bloqueActual.getVariableLocal(nombre.getLexema());

        if(metodoActual.esMetodoEstatico() && claseActual.atributoDeclarado(nombre.getLexema())){
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "No se pueden usar variables de instancia en contextos estáticos");
        }
        if(varLocal != null){
            referenciaTS = varLocal;
        }
        else if(metodoActual.parametroDeclarado(nombre.getLexema())){
            referenciaTS = metodoActual.getParametro(nombre.getLexema());
        }
        else if(claseActual.atributoDeclarado(nombre.getLexema())){
            referenciaTS = claseActual.getAtributo(nombre.getLexema());
        }
        else if(claseActual.metodoDeclarado(nombre.getLexema())){
            //que no sea void
            //ni estatico?
            Metodo metodoRef = claseActual.getMetodo(nombre.getLexema());
            referenciaTS = metodoRef;
            if(metodoRef.getTipoRetorno() instanceof TipoVoid){
                throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "El método "+nombre.getLexema()+ " retorna void y no puede encadenarse");
            }
            //tipoBase = metodoRef.getTipo();
        }
        else{
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Variable no declarada");
        }
        tipoBase = referenciaTS.getTipo();
        //Si tiene encadenado, delego el chequeo.
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoBase);
        }
        return tipoBase;

    }

    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
    }

    private boolean buscarEnBloques(String nombre) throws ExcepcionSemantica {
        NodoBloque bloque = TablaSimbolos.getInstance().getBloqueActual();
        while (bloque != null) {
            if (bloque.variableLocalDeclarada(nombre)) {
                return true;
            }
            bloque = bloque.getNodoBloquePadre();
        }
        return false;
    }
    public void setEncadenado(NodoEncadenado nodoEncadenado){
        this.encadenado = nodoEncadenado;
    }
    public int getOffset(){
        if(referenciaTS instanceof NodoVarLocal){
            return ((NodoVarLocal) referenciaTS).getOffset();
        }
        if(referenciaTS instanceof Parametro){
            return ((Parametro) referenciaTS).getOffset();
        }
        //si es atributo, TODO
        return 0;
    }
    public Elemento getReferenciaTS(){
        return referenciaTS;
    }
    public NodoEncadenado getUltimoEncadenado() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return encadenado;
        }
        else{
            return encadenado.getUltimoEncadenado();
        }
    }
    @Override
    public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {
        System.out.println("Generando código en NodoAccesoVar "+nombre.getLexema());
        if(referenciaTS instanceof NodoVarLocal varLocal){

            System.out.println(varLocal.getNombreVarLocal()+" "+varLocal.getOffset());
            if(esLadoIzq){
                archivo.generar(Instrucciones.STORE+ " "+ varLocal.getOffset());
            }
            else{
                archivo.generar(Instrucciones.LOAD+ " "+ varLocal.getOffset());
            }

        }
        else if(referenciaTS instanceof Atributo atributo){

            int offsetAttr = atributo.getOffset(); //offset adentro del CIR

            System.out.println("Desplazamiento de "+atributo.getNombre()+" "+atributo.getOffset());
            //archivo.generar(Instrucciones.LOAD+ " "+ atributo.getOffset());
            if(esLadoIzq){
                archivo.generar(Instrucciones.LOAD+" 3"); //this
                archivo.generar(Instrucciones.SWAP+"");
                archivo.generar(Instrucciones.STOREREF+" "+ offsetAttr);
            }
            else{
                archivo.generar(Instrucciones.LOAD+" 3");
                archivo.generar(Instrucciones.LOADREF+" "+ offsetAttr);
            }

        }
        else if(referenciaTS instanceof Parametro p){
            System.out.println();
            System.out.println("Desplazamiento de" +p.getNombre()+" "+ p.getOffset());
            System.out.println();
            if(esLadoIzq){
                archivo.generar(Instrucciones.STORE+ " "+ p.getOffset());
            }
            else{
                archivo.generar(Instrucciones.LOAD+ " "+ p.getOffset());
            }

        }
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            System.out.println("Generando codigo del encadenado "+encadenado.nombre.getLexema());
            encadenado.generar(archivo);
        }
    }
}