package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintCln extends NodoBloque {
    public BloquePrintCln() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.CPRINT);
        archivo.generar(""+Instrucciones.PRNLN);
    }
}
