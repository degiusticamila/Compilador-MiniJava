package ArchivoSalida;

import java.io.FileWriter;
import java.io.IOException;

public class ArchivoSalida {
   final FileWriter fileWriter;

    public ArchivoSalida(String nombreArchivo){
        try {
            fileWriter = new FileWriter(nombreArchivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void generar(String linea){
        try {
            fileWriter.write(linea);
            fileWriter.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void close(){
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
