package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloqueRead extends NodoBloque {
    public BloqueRead() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        archivo.generar(""+ Instrucciones.READ);
    }
}
