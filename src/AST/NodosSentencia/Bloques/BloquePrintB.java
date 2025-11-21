package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintB extends NodoBloque {
    public BloquePrintB() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        //System.out.println("Entro al codigo de PRINTB");
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.BPRINT);
    }
}
