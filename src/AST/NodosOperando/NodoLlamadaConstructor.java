package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
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
    public boolean tieneEncadenado() {
        if (!(encadenado instanceof NodoEncadenadoVacio)) {
            return true;
        }
        return false;
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
    public NodoEncadenado getUltimoEncadenado() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return encadenado;
        }
        else{
            return encadenado.getUltimoEncadenado();
        }
    }

    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
    }

    public void setArgumentos(List<NodoExpresion> argumentos){
        this.argumentos = argumentos;
    }
    public void setEncadenado(NodoEncadenado nodoEncadenado){
        this.encadenado = nodoEncadenado;
    }


    @Override
    public void generar(ArchivoSalida archivo) {
        System.out.println("Generando codigo NodoLlamadaConstructor "+nombre.getLexema());

        TablaSimbolos ts = TablaSimbolos.tablaSimbolos;
        Clase clase = ts.obtenerClase(nombre.getLexema());
        int cantAtributos = clase.getMapAtributos().size();
        int tamanioObjeto = cantAtributos + 1;

        //Retorno de malloc
        //archivo.generar(Instrucciones.RMEM + " 1");


        for (NodoExpresion arg : argumentos) {
            arg.generar(archivo);
            archivo.generar(Instrucciones.SWAP + ""); // deja el `this` (que tendremos) abajo para luego
        }

        // Otra celda temporal para manipular this / activar constructor sin romper RA
        archivo.generar(Instrucciones.RMEM + " 1");

        // Llamada a malloc: deja en tope la referencia al CIR (base del objeto)
        archivo.generar(Instrucciones.PUSH + " " + tamanioObjeto);
        archivo.generar(Instrucciones.PUSH+ " simple_malloc");
        archivo.generar(Instrucciones.CALL + "");

        // Inicializo VT: objeto en tope, duplico para no perderlo
        archivo.generar(Instrucciones.DUP + "");
        archivo.generar(Instrucciones.PUSH + " VT@" + clase.getNombre().getLexema());
        archivo.generar(Instrucciones.STOREREF + " 0");

        // Preparo this para el constructor: duplico y lo guardo en la RA reservada
        archivo.generar(Instrucciones.DUP + "");        // tengo: ... result params? CIR CIR
        archivo.generar(Instrucciones.LOADSP + "");     // apilo SP
        archivo.generar(Instrucciones.SWAP + "");       // pongo CIR arriba de SP
        // guardar referencia a this en la posición adecuada del RA del constructor:
        // la convención: offset = 3 + #parametros (ver apunte) => usamos (3 + argumentos.size())
        archivo.generar(Instrucciones.STOREREF + " " + (3 + argumentos.size()));

        // ahora invoco al constructor (su etiqueta)
        archivo.generar(Instrucciones.PUSH + " lbl_constructor@" + clase.getNombre().getLexema());
        archivo.generar(Instrucciones.CALL + "");

        // recupero/limpio las temporarias reservadas para los parametros/this
        archivo.generar(Instrucciones.FMEM + " 1"); // libera la RMEM anterior (la 2da)
        //archivo.generar(Instrucciones.FMEM + " 1"); // libera la RMEM inicial usada para argumentos

        // Si hay encadenado, la referencia al objeto está en tope: delego
        if (!(encadenado instanceof NodoEncadenadoVacio)) {
            encadenado.generar(archivo);
        }

        System.out.println("Finalizando NodoLlamadaConstructor");
    }


}
