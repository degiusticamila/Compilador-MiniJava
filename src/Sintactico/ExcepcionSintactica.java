package Sintactico;

import Utils.Token;

public class ExcepcionSintactica extends Exception{
    private Token token;
    private String nombreToken;

    public ExcepcionSintactica(Token token, String nombreToken) {
        this.token = token;
        this.nombreToken = nombreToken;
    }
    public Token getToken() {
        return token;
    }
    public String getNombreToken() {
        return nombreToken;
    }
    public String getMessage() {
        return "Se esperaba '" + nombreToken + "' pero se encontró '"
                + token.getId() + "' (" + token.getLexema() + ") "
                + "en línea " + token.getNroLinea();
    }
    public String formatoCorto() {
        return "[Error:" + token.getLexema() + "|" + token.getNroLinea() + "]";
    }
}
