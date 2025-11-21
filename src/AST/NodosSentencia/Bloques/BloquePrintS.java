package AST.NodosSentencia.Bloques;

import AST.NodosSentencia.NodoBloque;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;

public class BloquePrintS extends NodoBloque {
    public BloquePrintS() {
        super();
    }
    public void generar(ArchivoSalida archivo){
        //System.out.println("Entro al codigo de PRINTS");
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.SPRINT);
    }
}
