package AST.NodosOperando;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import Utils.Token;

import java.util.LinkedList;
import java.util.List;

public class NodoLlamadaConstructor extends NodoOperando{
    private Token nombre;
    private List<NodoExpresion> argumentos;

    public NodoLlamadaConstructor(Token nombre,  LinkedList<NodoExpresion> argumentos) {
        this.nombre = nombre;
        this.argumentos = argumentos;
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
        return s.toString();
    }
    @Override
    public void imprimir(String prefijo){

        System.out.println(prefijo + nombre.getLexema()+"(");
        for(int i = 0; i < argumentos.size(); i++){
            System.out.println(argumentos.get(i).formatear());
            if(i < argumentos.size()-1){
                System.out.println(", ");
            }
        }
        System.out.println(")");
    }
    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return new TipoPrimitivo("tipo primitivo");
    }
    public void setArgumentos(List<NodoExpresion> argumentos){
        this.argumentos = argumentos;
    }
}
