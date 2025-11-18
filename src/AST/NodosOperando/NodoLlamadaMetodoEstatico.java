package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.*;
import Utils.Token;

import java.util.List;

public class NodoLlamadaMetodoEstatico extends NodoOperando{
    private Token nombreClase;
    private Token nombreMetodo;
    private List<NodoExpresion> argumentos;
    private NodoEncadenado encadenado;

    public NodoLlamadaMetodoEstatico(Token nombre, Token nombreMetodo, List<NodoExpresion> argumentos, NodoEncadenado encadenado) {
        this.nombreClase = nombre;
        this.nombreMetodo = nombreMetodo;
        this.argumentos = argumentos;
        this.encadenado = encadenado;
    }
    @Override
    public Token getNombre() {
        return nombreMetodo;
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
        System.out.println(prefijo + nombreClase.getLexema() + "." + nombreMetodo.getLexema() + "(");
        for (int i = 0; i < argumentos.size(); i++) {
            System.out.println(prefijo + "  " + argumentos.get(i).formatear());
            if (i < argumentos.size() - 1) System.out.println(",");
        }
        System.out.print(prefijo + ")");
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
        StringBuilder s = new StringBuilder(nombreClase.getLexema() + "." + nombreMetodo.getLexema() + "(");
        for (int i = 0; i < argumentos.size(); i++) {
            s.append(argumentos.get(i).formatear());
            if (i < argumentos.size() - 1) s.append(", ");
        }
        s.append(")");
        return s.toString();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        System.out.println("nodo llamada metodo estatico");
        Clase clase = ts.obtenerClase(nombreClase.getLexema());
        if(clase == null){
            throw new ExcepcionSemantica(nombreClase.getLexema(), nombreClase.getNroLinea(), "La clase no está definida");

        }
        if(!clase.metodoDeclarado(nombreMetodo.getLexema())){
            throw new ExcepcionSemantica(nombreMetodo.getLexema(), nombreMetodo.getNroLinea(), "El metodo "+nombreMetodo.getLexema()+" no esta declarado en la clase "+nombreClase.getLexema());
        }
        Metodo metodo = clase.getMetodo(nombreMetodo.getLexema());
        if(!metodo.esMetodoEstatico()){
            throw new ExcepcionSemantica(nombreMetodo.getLexema(), nombreMetodo.getNroLinea(), "El metodo "+nombreMetodo.getLexema()+" no es static y se esta llamando como "+nombreClase.getLexema()+"."+nombreMetodo.getLexema()+"()");
        }
        if(argumentos.size() != metodo.getParametros().size()){
            throw new ExcepcionSemantica(nombreMetodo.getLexema(), nombreMetodo.getNroLinea(), "Cantidad de argumentos incorrecta "+nombreMetodo.getLexema()+" deben ser "+metodo.getParametros().size()+" en lugar de "+argumentos.size());
        }
        for (int i = 0; i < argumentos.size(); i++) {
            Tipo tipoArgActual = argumentos.get(i).chequear();
            Tipo tipoArgFormal = metodo.getParametros().get(i).getTipo();

            if (!tipoArgActual.esCompatible(tipoArgFormal)) {
                throw new ExcepcionSemantica(nombreMetodo.getLexema(), nombreMetodo.getNroLinea(),
                        "El argumento " + (i + 1) + " no es compatible: se esperaba " +
                                tipoArgFormal + " y se recibió " + tipoArgActual);
            }
        }
        Tipo tipoRetorno = metodo.getTipoRetorno();
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoRetorno);
        }
        return tipoRetorno;
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
    public void generar(ArchivoSalida archivo) {
        System.out.println("Entro a generar NodoLlamadaMetodoEstatico "+nombreMetodo.getLexema());
        TablaSimbolos ts = TablaSimbolos.tablaSimbolos;
        Clase clase = ts.obtenerClase(nombreClase.getLexema());
        Metodo metodo = clase.getMetodo(nombreMetodo.getLexema());

        archivo.generar(Instrucciones.RMEM+" 1");
        for(NodoExpresion parametro : argumentos){
            parametro.generar(archivo);
        }
        archivo.generar(Instrucciones.PUSH+" lbl_"+metodo.getNombreMetodo().getLexema()+"@"+clase.getNombre().getLexema());
        archivo.generar(Instrucciones.CALL+"");



       /* Clase claseActual = TablaSimbolos.tablaSimbolos.getClaseActual();
        Metodo metodo = claseActual.getMetodo(nombreMetodo.getLexema());
        Clase claseDelMetodo = metodo.esMetodoPredefinido();
        if(claseDelMetodo == null){
            claseDelMetodo = metodo.obtenerClase();
        }
        for(NodoExpresion parametro : argumentos){
            parametro.generar(archivo);
        }

        archivo.generar(Instrucciones.PUSH+" lbl_"+nombreMetodo.getLexema()+"@"+claseDelMetodo.getNombre().getLexema());
        archivo.generar(Instrucciones.CALL+"");

         */

    }

    @Override
    public String nombreSentencia() {
        return nombreMetodo.getLexema();
    }

    public void setEncadenado(NodoEncadenado encadenado){
        this.encadenado = encadenado;
    }
}
