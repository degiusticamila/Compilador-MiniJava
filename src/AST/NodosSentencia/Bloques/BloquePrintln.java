package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintln extends NodoBloque {
    public BloquePrintln() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        System.out.println("Entro al codigo de PRINTln");
        archivo.generar(""+ Instrucciones.PRNLN);
    }
}
