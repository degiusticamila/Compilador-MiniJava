package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoVarLocal;
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
    public String formatear() {
        if(encadenado instanceof NodoEncadenadoVacio){
            return nombre.getLexema();
        }
        return nombre.getLexema() + "." + encadenado.formatear();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        System.out.println("entro a chequear de acceso var "+nombre.getLexema());

        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase claseActual = ts.getClaseActual();
        Metodo metodoActual = ts.getMetodoActual();
        NodoBloque bloqueActual = ts.getBloqueActual();

        NodoVarLocal varLocal = bloqueActual.getVariableLocal(nombre.getLexema());
        if(varLocal != null){
            referenciaTS = varLocal;
        }
        else if(metodoActual.parametroDeclarado(nombre.getLexema())){
            referenciaTS = metodoActual.getParametro(nombre.getLexema());
        }
        else if(claseActual.atributoDeclarado(nombre.getLexema())){
            referenciaTS = claseActual.getAtributo(nombre.getLexema());
        }
        else{
            throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Variable no declarada");
        }
        Tipo tipoBase = referenciaTS.getTipo();
        //Si tiene encadenado, delego el chequeo.
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoBase);
        }
        return tipoBase;

        /*
        Tipo tipo = b.buscarTipoVariableEnBloques(nombre.getLexema());
        if (tipo != null) {
            System.out.println("Variable encontrada en algún bloque. Tipo = " + tipo);
            return tipo;
        }
        //es parametro
        else if(m.parametroDeclarado(nombre.getLexema())){
            //varEnTS = ts.getMetodoActual().getParametro(nombre.getLexema());
            System.out.println(m.getTipoParametro(nombre.getLexema()));
            return m.getTipoParametro(nombre.getLexema());
        }
        //es atributo
        else if(claseActual.atributoDeclarado(nombre.getLexema())){
            System.out.println(claseActual.getTipoAtributo(nombre.getLexema()));
            return claseActual.getTipoAtributo(nombre.getLexema());
        }
        else{
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(),"variable no declarada");
        }
        */
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
}
