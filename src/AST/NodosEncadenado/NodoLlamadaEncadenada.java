package AST.NodosEncadenado;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.*;
import Utils.Token;
import java.util.List;

public class NodoLlamadaEncadenada extends NodoEncadenado {
    private List<NodoExpresion> parametros;
    private NodoEncadenado encadenado;
    protected Tipo tipoBase;
    public NodoLlamadaEncadenada(Token nombre, NodoEncadenado encadenado, List<NodoExpresion> parametros) {
        super(nombre);
        this.parametros = parametros;
        this.encadenado = encadenado;
    }



    @Override
    public Tipo chequear(Tipo t) throws ExcepcionSemantica {
        this.tipoBase = t;
        if(!t.esReferencia()){
            throw new ExcepcionSemantica(super.nombre.getLexema(), super.nombre.getNroLinea(), "Encadenado sobre tipo no definido o primitivo: "+ t.getNombre());
        }
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase clase = ts.obtenerClase(t.getNombre());
        if(clase == null){
            throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(), "Tipo no definido "+t.getNombre());
        }
        if(!clase.metodoDeclarado(super.nombre.getLexema())){
            throw new ExcepcionSemantica(super.nombre.getLexema(), super.nombre.getNroLinea(), "Metodo "+super.nombre.getLexema()+" encadenado no declarado en clase: "+clase.getNombre().getLexema());
        }
        Metodo metodo = clase.getMetodo(super.nombre.getLexema());

        if(parametros.size() != metodo.getParametros().size()){
            throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(), "La cantidad de parametros es incorrecta, deben ser "+clase.getMetodo(super.nombre.getLexema()).getParametros().size());
        }

        //Correspondencia entre parametros de la llamada
        for(int i = 0; i<parametros.size(); i++){
            Tipo tipoActual = parametros.get(i).chequear();
            Tipo tipoFormal = metodo.getParametros().get(i).getTipo();

            if(!tipoActual.esCompatible(tipoFormal)){
                throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(),"El argumento "+ (i+1)+ " no es compatible: se esperaba "+tipoFormal.getNombre()+" y recibe "+tipoActual.getNombre());
            }
        }
        Tipo tipoRetorno = metodo.getTipo();

        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoRetorno);
        }
        return tipoRetorno;

    }
    public boolean tieneEncadenado(){
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return true;
        }
        return false;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + nombre.getLexema()+"(");
        for(int i = 0; i < parametros.size(); i++){
            System.out.println(parametros.get(i).formatear());
            if(i < parametros.size()-1){
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
    public String formatear() {
        StringBuilder s = new StringBuilder(nombre.getLexema()+"(");
        for(int i = 0; i < parametros.size(); i++){
            s.append(parametros.get(i).formatear());
            if(i < parametros.size()-1){
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
    public void setEncadenado(NodoEncadenado encadenado) {
        this.encadenado = encadenado;
    }

    @Override
    public NodoEncadenado getUltimoEncadenado() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return this;
        }
        else{
            return encadenado.getUltimoEncadenado();
        }
    }


    @Override
    public void generar(ArchivoSalida archivo) {
        System.out.println("Generando codigo NodoLlamadaEncadenada "+nombre.getLexema());

        TablaSimbolos ts = TablaSimbolos.tablaSimbolos;
        Clase clase = ts.obtenerClase(tipoBase.getNombre());
        Metodo metodo = clase.getMetodo(super.nombre.getLexema());
        int offset = metodo.getOffsetMetodo(); // asumimos ya calculado por Clase.calcularOffsetMetodos()


        archivo.generar(Instrucciones.DUP + "");
        archivo.generar(Instrucciones.LOADREF + " 0");
        archivo.generar(Instrucciones.LOADREF + " " + offset);


        for (NodoExpresion parametro : parametros) {
            archivo.generar(Instrucciones.SWAP + ""); // pone 'this' debajo para que parametro vaya arriba
            parametro.generar(archivo);
            archivo.generar(Instrucciones.SWAP + ""); // restaura orden: ... this lbl param...
        }
        int a = parametros.size() + 3;
        //archivo.generar(Instrucciones.DUP+"");
        //archivo.generar(Instrucciones.LOADSP+"");
        //archivo.generar(Instrucciones.SWAP+"");
        //archivo.generar(Instrucciones.STOREREF+" "+a);

        archivo.generar(Instrucciones.CALL + "");

        // si hay encadenado
        if (!(encadenado instanceof NodoEncadenadoVacio)) {
            encadenado.generar(archivo);
        }
        generarRetorno(archivo);
    }
    public void generarRetorno(ArchivoSalida archivo) {
        //TO-DO
        archivo.generar(Instrucciones.LOADREF + " 1");
    }


}
