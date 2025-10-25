package AST.NodosEncadenado;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

import java.util.LinkedList;
import java.util.List;

public class NodoLlamadaEncadenada extends NodoEncadenado {
    private List<NodoExpresion> parametros;
    private NodoEncadenado encadenado;
    public NodoLlamadaEncadenada(Token nombre, NodoEncadenado encadenado, List<NodoExpresion> parametros) {
        super(nombre);
        this.parametros = parametros;
        this.encadenado = encadenado;
    }
    @Override
    public Tipo chequear(Tipo t) {
        return new TipoUniversal("Tipo universal");
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + nombre.getLexema()+"(");
        for(int i = 0; i < parametros.size(); i++){
            System.out.println(parametros.get(i).formatear());
            if(i < parametros.size()-1){
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
        for(int i = 0; i < parametros.size(); i++){
            s.append(parametros.get(i).formatear());
            if(i < parametros.size()-1){
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
    public void setEncadenado(NodoEncadenado encadenado) {
        this.encadenado = encadenado;
    }
}
