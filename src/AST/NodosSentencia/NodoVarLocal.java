package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import AST.NodosExpresion.NodoExpresionVacia;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoVarLocal extends NodoSentencia {
    public Token nombre;
    private Token operador;
    private NodoExpresion ladoDerecho;
    private Tipo tipo;
    public NodoVarLocal(Token nombre) {
        this.nombre = nombre;
        this.ladoDerecho = new NodoExpresionVacia();
    }
    public void setLadoDerecho(NodoExpresion ladoDerecho){
        this.ladoDerecho = ladoDerecho;
    }
    public void setOperador(Token operador){
        this.operador = operador;
    }
    @Override
    public void imprimir(String prefijo) {

        System.out.println(prefijo + "Var: " + nombre.getLexema());
        System.out.print(operador.getLexema());
        ladoDerecho.imprimir(" ");
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        System.out.println("Chequeando NodoVarLocal");
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase claseActual = ts.getClaseActual();
        Metodo m = ts.getMetodoActual();
        NodoBloque b = ts.getBloqueActual();

        // var id = e1

        if(m.parametroDeclarado(nombre.getLexema())){
            throw new ExcepcionSemantica(this.nombre.getLexema(), this.nombre.getNroLinea(), "La variable tiene el mismo nombre que el parametro");
        }
        if(b.variableLocalDeclarada(nombre.getLexema())){
            throw new ExcepcionSemantica(this.nombre.getLexema(), this.nombre.getNroLinea(), "La variable tiene el mismo nombre que una del bloque");
        }
        tipo = ladoDerecho.chequear();
        System.out.println("Tipo de la Variable local "+tipo+"con nombre "+nombre.getLexema());
        if(tipo.equals(TipoPrimitivo.NULL)){
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Tipo var nula");
        }
        TablaSimbolos.getInstance().getBloqueActual().insertarVariable(this);
        //FALTA CHEQUEAR UNA DEL BLOQUE DE MAS ARRIBA, como escalo?

        // var id = e1
        //id no debe ser nombre de un parametro del metodo que contiene                             LISTO!
        //a la declaracion o una variable local definida anteriormente en el mismo bloque           LISTO!
        //o anteriormente en un bloque que contiene al bloque que contiene a la declaracion.        FALTA!!!!!
        //e1 es una expresion correctamente tipada y su tipo T es dinstinto del tipo especial null. LISTO!

        //Una declaracion de variable local correctamente tipada establece
        //la variable local tendra nombre id y tipo T
        //luego de la sentencia de declaracion la variable sera visible por todas
        //las  sentencias subsiguientes hasta que finalice el bloque actual.
    }
    public String getNombreVarLocal(){
        return nombre.getLexema();
    }
    public Tipo getTipoVarLocal(){
        return tipo;
    }
}
