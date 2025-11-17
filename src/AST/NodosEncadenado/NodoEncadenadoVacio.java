package AST.NodosEncadenado;

import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoEncadenadoVacio extends NodoEncadenado {

    public NodoEncadenadoVacio() {
        super(new Token("null", "null", -1));

    }

    @Override
    public void generar(ArchivoSalida archivo) {

    }

    @Override
    public Tipo chequear(Tipo t) {
        return new TipoUniversal("Tipo universal");
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println("encadenado vacio");
    }

    @Override
    public String formatear() {
        return "";
    }

    @Override
    public void setEncadenado(NodoEncadenado encadenado) {
        //vacio
    }



}
