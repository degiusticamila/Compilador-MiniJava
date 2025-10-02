package TablaDeSimbolos;

public class ExcepcionSemantica extends Exception {
    private int numLine;
    private String lexema;

    public ExcepcionSemantica( String lexema,int numLine){
        this.lexema = lexema;
        this.numLine = numLine;
    }
    public String getMessage(){
        return ("Error semántico en línea "+numLine);
    }
    public String formatoCorto() {
        return "[Error:" + lexema + "|" + numLine + "]";
    }
}
