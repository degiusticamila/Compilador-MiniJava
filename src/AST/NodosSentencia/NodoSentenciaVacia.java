package AST.NodosSentencia;

public class NodoSentenciaVacia extends NodoSentencia {
    @Override
    public void imprimir(String prefijo) {
        System.out.println("Sentencia vacia");
    }

    @Override
    public void chequear() {

    }
}
