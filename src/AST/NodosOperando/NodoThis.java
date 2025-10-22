package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.*;
import Utils.Token;

    public class NodoThis extends NodoOperando {
    private Token tokenThis;
    private NodoEncadenado encadenado;
    public NodoThis(Token tokenThis) {
        this.tokenThis = tokenThis;
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

    }

    @Override
    public String formatear() {
        return tokenThis.getLexema();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        /*El tipo de this es el mismo que el de la clase en que se esta utilizando, y no es posible utilizarlo en
        metodos estaticos.*/
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        if(tablaSimbolos.getMetodoActual().esMetodoEstatico()){
            throw new ExcepcionSemantica(tokenThis.getLexema(), tokenThis.getNroLinea(), "No es posible invocar a this en métodos estáticos");
        }
       /* String nombreTipo = "";
        if(encadenado != null){
            encadenado.chequear();
        }
        else{
            nombreTipo = tablaSimbolos.getClaseActual().getNombre().getLexema();
        }

        System.out.println("Tipo de this: " + nombreTipo);
        return new TipoReferencia(nombreTipo);

        */
        return new TipoUniversal("tipo universal");
    }
    public void setEncadenado(NodoEncadenado nodoEncadenado) {
        this.encadenado = nodoEncadenado;
    }
}
