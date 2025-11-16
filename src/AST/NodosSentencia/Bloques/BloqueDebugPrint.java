package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloqueDebugPrint extends NodoBloque {
    public BloqueDebugPrint() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        System.out.println("Entro al codigo de DEBUGPRINT");
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.IPRINT);

    }

}
