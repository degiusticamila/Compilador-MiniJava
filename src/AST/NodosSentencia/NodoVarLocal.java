package AST.NodosSentencia;

import Utils.Token;

public class NodoVarLocal extends NodoSentencia {
    public Token nombre;
    private NodoSentencia nodoAsignacion;
    public NodoVarLocal(Token nombre) {
        this.nombre = nombre;
        this.nodoAsignacion = new NodoSentenciaVacia();
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "Var: " + nombre.getLexema());
    }
}
