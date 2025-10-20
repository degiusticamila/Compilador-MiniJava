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
        System.out.println("entro a chequear de acceso var");
        //me fijo si
        //es atributo
        //lo que se usa es parametro se posterga porque todavia no se
        //es variable local se posterga porque todavia no se inserta bien
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase claseActual = ts.getClaseActual();
        Metodo m = ts.getMetodoActual();
        NodoBloque b = ts.getBloqueActual();

        if(m.parametroDeclarado(nombre.getLexema())){

        }
        if(claseActual.atributoDeclarado(nombre.getLexema())){
            System.out.println(claseActual.getTipoAtributo(nombre.getLexema()));
           return claseActual.getTipoAtributo(nombre.getLexema());
        }
        throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(),"variable no declarada");
    }
}
