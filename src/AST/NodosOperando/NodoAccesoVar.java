package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import AST.NodosSentencia.NodoBloque;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoAccesoVar extends NodoOperando {
    Token nombre;

    public NodoAccesoVar(Token nombre){
        this.nombre = nombre;
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
    public void imprimir(String prefijo) {
        System.out.print(prefijo + nombre.getLexema());
    }

    @Override
    public String formatear() {
        return nombre.getLexema();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        System.out.println("entro a chequear de acceso var "+nombre.getLexema());


        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase claseActual = ts.getClaseActual();
        Metodo m = ts.getMetodoActual();
        NodoBloque b = ts.getBloqueActual();

        //es variable local
        if(b.variableLocalDeclarada(nombre.getLexema())){
            System.out.println("LA VARIABLE ESTA DECLARADA");
            System.out.println(b.getTipoVariableLocalDeclarada(nombre.getLexema()));
            return b.getTipoVariableLocalDeclarada(nombre.getLexema());
        }
        //falta ver en caso de que sean bloques anidados
        /*if (buscarEnBloques(nombre.getLexema())) { //NEW
            return b.getTipoVariableLocalDeclarada(nombre.getLexema());
        }
        if(b.variableDeclaradaEnAlgunBloque(nombre.getLexema())){
            System.out.println("La variable esta declarada en un bloque de AFUERA");
            System.out.println(b.getTipoVariableLocalDeclarada(nombre.getLexema()));
            return b.getTipoVariableLocalDeclarada(nombre.getLexema());
        }

         */
        Tipo tipo = b.buscarTipoVariableEnBloques(nombre.getLexema());
        if (tipo != null) {
            System.out.println("Variable encontrada en algún bloque. Tipo = " + tipo);
            return tipo;
        }

        //es parametro
        else if(m.parametroDeclarado(nombre.getLexema())){
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
}
