package AST.NodosOperando;

import AST.NodosEncadenado.NodoEncadenado;
import AST.NodosEncadenado.NodoEncadenadoVacio;
import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
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
        return new TipoUniversal("tipo universal");
    }
    public void setEncadenado(NodoEncadenado nodoEncadenado) {
        this.encadenado = nodoEncadenado;
    }
}
