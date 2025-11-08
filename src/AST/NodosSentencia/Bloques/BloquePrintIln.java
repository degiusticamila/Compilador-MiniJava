package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintIln extends NodoBloque {
    public BloquePrintIln() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.IPRINT);
        archivo.generar(""+Instrucciones.PRNLN);
    }
}
