import ArchivoSalida.ArchivoSalida;
import Lexico.AnalizadorLexico;
import Lexico.ExcepcionLexica;
import Sintactico.AnalizadorSintactico;
import Sintactico.ExcepcionSintactica;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.TablaSimbolos;
import Utils.SourceManager;
import Utils.SourceManagerImpl;
import Utils.Token;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static SourceManager sourceManager;
    private static ArchivoSalida archivoSalida;
    private static AnalizadorSintactico analizadorSintactico;
    private static AnalizadorLexico analizadorLexico;

    public static void analisisSintactico() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        analizadorLexico = new AnalizadorLexico(sourceManager);
        analizadorSintactico = new AnalizadorSintactico(analizadorLexico);
    }
    public static void main(String[] args) {
        sourceManager = new SourceManagerImpl();
        abrirArchivo(args);
        try {
            analisisSintactico();
            analizadorSintactico.consolidarTS();
            analizadorSintactico.chequeoSemantico();

            generacionCodigo("testOut.out");
            //TablaSimbolos.getInstance().imprimirDetalleClases();
            System.out.println("[SinErrores]");
        } catch (ExcepcionLexica e) {
            System.out.println("Error Lexico: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExcepcionSintactica e) {
            System.out.println("Error Sintactico: " + e.getMessage());
            System.out.println(e.formatoCorto());
        } catch(ExcepcionSemantica e){
            System.out.println(e.getMessage());
        }
        cerrarArchivo(args);
    }
    public static void abrirArchivo(String[] args){
        try {
            sourceManager.open(args[0]);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void cerrarArchivo(String[] args){
        try {
            sourceManager.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
    public void generacionCodigo(String nombre_archivo){
        archivoSalida = new ArchivoSalida(nombre_archivo);
        //TO - DO
        //tablaSimbolos.generarCodigo(archivoSalida);
        archivoSalida.close();
    }
}