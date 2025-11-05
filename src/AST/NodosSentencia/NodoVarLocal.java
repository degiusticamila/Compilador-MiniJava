package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import AST.NodosExpresion.NodoExpresionVacia;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoVarLocal extends NodoSentencia implements Elemento {
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
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Metodo m = ts.getMetodoActual();
        NodoBloque b = ts.getBloqueActual();

        if(m.parametroDeclarado(nombre.getLexema())){
            throw new ExcepcionSemantica(this.nombre.getLexema(), this.nombre.getNroLinea(), "La variable tiene el mismo nombre que un parametro");
        }
        if(b.variableLocalDeclarada(nombre.getLexema())){
            throw new ExcepcionSemantica(this.nombre.getLexema(), this.nombre.getNroLinea(), "La variable tiene el mismo nombre que una del bloque");
        }
        if(b.buscarDeclaradaEnBloques(nombre.getLexema())){
            throw new ExcepcionSemantica(this.nombre.getLexema(), this.nombre.getNroLinea(), "Nombre de variable local repetido");
        }
        TablaSimbolos.getInstance().getBloqueActual().insertarVariable(this);
        tipo = ladoDerecho.chequear();

        if(tipo.equals(TipoPrimitivo.NULL)){
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Tipo var nula");
        }

    }
    public String getNombreVarLocal(){
        return nombre.getLexema();
    }
    public Tipo getTipoVarLocal(){
        return tipo;
    }
    public String getNombre(){
        return nombre.getLexema();
    }
    public Tipo getTipo(){
        return tipo;
    }
    public int getLinea(){
        return nombre.getNroLinea();
    }

    @Override
    public Token getModificador() {
        return null;
    }
}
