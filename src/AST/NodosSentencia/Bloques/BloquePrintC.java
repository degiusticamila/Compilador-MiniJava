package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintC extends NodoBloque {
    public BloquePrintC() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        System.out.println("Entro al codigo de PRINTC");
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.CPRINT);
    }
}
