import Lexico.AnalizadorLexico;
import Lexico.ExcepcionLexica;
import Sintactico.AnalizadorSintactico;
import Sintactico.ExcepcionSintactica;
import Utils.SourceManager;
import Utils.SourceManagerImpl;
import Utils.Token;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static SourceManager sourceManager;
    private static AnalizadorSintactico analizadorSintactico;
    private static AnalizadorLexico analizadorLexico;

    public static void analisisLexico(String[] args){
        try {
            sourceManager = new SourceManagerImpl();
            sourceManager.open(args[0]);
            AnalizadorLexico analizadorLexico = new AnalizadorLexico(sourceManager);
            String test = "[SinErrores]";
            Token token;
            do{
                analizadorLexico.setLexema("");
                try{
                    token = analizadorLexico.proximoToken();
                    System.out.println(token);
                }catch(ExcepcionLexica ex){
                    System.out.println("Error lexico en Linea "
                            + sourceManager.getLineNumber()+" | Columna "
                            + sourceManager.getLineIndexNumber()
                            + ": "+analizadorLexico.getLexema()+" "
                            + ex.getDescrip());
                    System.out.println(ex.mostrarDetalle(
                            sourceManager.getCurrentLine(),
                            analizadorLexico.getLexema(),
                            sourceManager.getLineIndexNumber()));
                    System.out.println(ex.getMessage());
                    token = null;
                    test = "";
                    analizadorLexico.actualizarCaracterActual();
                    analizadorLexico.setLexema("");
                }
            } while(token == null || !token.getId().equals("EOF"));
            sourceManager.close();
            if (!test.isEmpty()) System.out.println("[SinErrores]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void analisisSintactico() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        analizadorLexico = new AnalizadorLexico(sourceManager);
        analizadorSintactico = new AnalizadorSintactico(analizadorLexico);
    }
    public static void main(String[] args) {
        sourceManager = new SourceManagerImpl();
        try {
            sourceManager.open(args[0]);
            analisisSintactico();
            System.out.println("[SinErrores]");
        } catch (ExcepcionSintactica | ExcepcionLexica ex) {
            try { //NUEVO
                System.out.println("Error Sintactico en línea: "+sourceManager.getLineNumber()+"\n" +
                        "[Error:"+(analizadorLexico.getLexema().isEmpty() ? analizadorLexico.proximoToken().getId() : analizadorLexico.getLexema())
                        +"|"+sourceManager.getLineNumber()+"]");
            } catch (ExcepcionLexica e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //System.out.println("Error Sintactico en línea: "+sourceManager.getLineNumber()+"\n" +
            //        "[Error:"+analizadorLexico.getLexema()+"|"+sourceManager.getLineNumber()+"]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
                sourceManager.close();
        } catch (IOException ignored) {}
    }

}