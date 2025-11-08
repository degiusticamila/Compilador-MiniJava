package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintBln extends NodoBloque {
    public BloquePrintBln() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.BPRINT);
        archivo.generar(""+Instrucciones.PRNLN);
    }
}
