package TablaDeSimbolos;

import Utils.Token;

public interface Elemento {
    String getNombre(); //lexema
    Tipo getTipo();
    int getLinea();
    Token getModificador();
}
