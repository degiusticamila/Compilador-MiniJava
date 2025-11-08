package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintSln extends NodoBloque {
    public BloquePrintSln() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.SPRINT);
        archivo.generar(""+Instrucciones.PRNLN);
    }
}
