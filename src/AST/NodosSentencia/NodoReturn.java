package AST.NodosSentencia;

import Utils.Token;

public class NodoReturn extends NodoSentencia {
    private Token nombre;

    public NodoReturn(Token nombre) {
        this.nombre = nombre;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "Return: " + nombre.getLexema());
    }
}
