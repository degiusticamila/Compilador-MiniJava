package TablaDeSimbolos;

public class ExcepcionSemantica extends Exception {
    private int numLine;
    private String lexema;
    private String detalle;

    public ExcepcionSemantica( String lexema,int numLine,String detalle){
        this.lexema = lexema;
        this.numLine = numLine;
        this.detalle = detalle;
    }
    public String getMessage(){
        return ("Error semántico en línea "+numLine+"\n"+detalle+"\n"+formatoCorto());

    }
    public String formatoCorto() {
        return "[Error:" + lexema + "|" + numLine + "]";
    }
}
