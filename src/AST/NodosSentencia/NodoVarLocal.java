package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import AST.NodosExpresion.NodoExpresionVacia;
import AST.NodosOperando.NodoLlamadaMetodo;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoVarLocal extends NodoSentencia implements Elemento {
    public Token nombre;
    private Token operador;
    private NodoExpresion ladoDerecho;
    private Tipo tipo;
    private int offset;
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
        if(ladoDerecho instanceof NodoLlamadaMetodo && tipo.esCompatible(new TipoVoid())){
            throw new ExcepcionSemantica(operador.getLexema(), operador.getNroLinea(), "El tipo "+tipo+" no es asignable");
        }
    }

    @Override
    public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {
        System.out.println();
        System.out.println("Generando codigo VarLocal "+nombre.getLexema()+" con offset "+offset);
        System.out.println();
        ladoDerecho.generar(archivo);
        archivo.generar(Instrucciones.STORE+" "+offset);


    }

    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
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
    public void setOffset(int n){
        this.offset = n;
    }
    public int getOffset(){
        return offset;
    }
}
