package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.*;
import Utils.Token;

import java.util.LinkedList;
import java.util.List;

public class NodoLlamadaConstructor extends NodoOperando{
    private Token nombre;
    private List<NodoExpresion> argumentos;
    private NodoEncadenado encadenado;

    public NodoLlamadaConstructor(Token nombre,  LinkedList<NodoExpresion> argumentos, NodoEncadenado encadenado) {
        this.nombre = nombre;
        this.argumentos = argumentos;
        this.encadenado = encadenado;
    }

    @Override
    public Token getNombre() {
        return nombre;
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
    public void imprimir(String prefijo){
        System.out.println("entra a imprimir de llamadaConstructor");
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
            encadenado.imprimir("");
        }
    }
    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        //devuelvo el nombre de la clase
        //misma cantidad de parametros y tipo

        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase clase = ts.obtenerClase(nombre.getLexema());
        if (clase == null) {
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Constructor de clase no declarada " + nombre.getLexema());
        }
        Constructor constructorClase = clase.getConstructor();
        if(constructorClase != null) {
            if (argumentos.size() != constructorClase.getParametros().size()) {
                throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Distinta cantidad de parametros");
            }
            for (int i = 0; i < argumentos.size(); i++) {
                Tipo tipoActual = argumentos.get(i).chequear();
                Tipo tipoFormal = constructorClase.getParametros().get(i).getTipo();

                if (!tipoActual.esCompatible(tipoFormal)) {
                    throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "El argumento " + (i + 1) + " no es compatible: se esperaba " + tipoFormal.getNombre() + " y recibe " + tipoActual.getNombre());
                }
            }
        }
        Tipo tipoConstructor = new TipoReferencia(nombre.getLexema());
        //antesa me fijo si tiene o no ENCADENADO
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoConstructor);
        }
        return tipoConstructor;
    }

    @Override
    public void generar(ArchivoSalida archivo) {
        //es la que hace el .cir?
    }

    public void setArgumentos(List<NodoExpresion> argumentos){
        this.argumentos = argumentos;
    }
    public void setEncadenado(NodoEncadenado nodoEncadenado){
        this.encadenado = nodoEncadenado;
    }
}
