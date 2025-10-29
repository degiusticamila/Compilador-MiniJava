package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.*;
import Utils.Token;

import java.util.List;

public class NodoLlamadaMetodo extends NodoOperando{
    private Token nombre;
    private List<NodoExpresion> argumentos;
    private NodoEncadenado encadenado;

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
        if(ts.getMetodoActual().esMetodoEstatico()){
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(), "No se puede invocar un metodo dentro de un metodo estático");
        }
        if(!claseActual.metodoDeclarado(nombre.getLexema())){
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(), "Método "+nombre.getLexema()+" no declarado en la clase "+claseActual.getNombre().getLexema());
        }
        Metodo metodo = claseActual.getMetodo(nombre.getLexema());
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
}
