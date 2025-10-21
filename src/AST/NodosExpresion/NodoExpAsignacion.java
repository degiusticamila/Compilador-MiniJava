package AST.NodosExpresion;

import AST.NodosSentencia.NodoBloque;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoExpAsignacion extends NodoExpresion {
  private NodoExpresion ladoIzquierdo;
  private NodoExpresion ladoDerecho;
  private Token operador;

   public NodoExpAsignacion(Token operador, NodoExpresion ladoIzquierdo, NodoExpresion ladoDerecho) {
       this.operador = operador;
       this.ladoIzquierdo = ladoIzquierdo;
       this.ladoDerecho = ladoDerecho;

   }
   public void setOperador(Token operador){
       this.operador = operador;
   }

   @Override
   public void setLadoIzquierdo(NodoExpresion ladoIzquierdo) {
       this.ladoIzquierdo = ladoIzquierdo;
   }

   @Override
   public void setLadoDerecho(NodoExpresion ladoDerecho) {
       this.ladoDerecho = ladoDerecho;
   }
   @Override
   public String formatear() {
        return ladoIzquierdo.formatear() + " " + operador.getLexema() + " " + ladoDerecho.formatear();
   }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        System.out.println("Chequeando ExpresionAsignacion");
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase claseActual = ts.getClaseActual();
        Metodo m = ts.getMetodoActual();
        NodoBloque b = ts.getBloqueActual();

       /*La expresi´en de asignaci´on Destino=Origen es correcta si y s´olo si:
            Destino es correctamente tipada y, adem´as estructuralmente debe cumplir que:
        – No tiene encadenado, debe ser un acceso a variable (resuelto correctamente).

        – Tiene encadenado, el ultimo elemento del encadenado debe ser una variable.
        Origen es una expresi´on correctamente tipada y su tipo conforma con el tipo de Destino.*/

        Tipo tipoLadoDerecho = ladoDerecho.chequear();
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        if(!tipoLadoDerecho.esCompatible(tipoLadoIzquierdo)){
            throw new ExcepcionSemantica(operador.getLexema(),operador.getNroLinea(),"El tipo "+tipoLadoDerecho+" no conforma con "+tipoLadoIzquierdo);
        }
        return tipoLadoDerecho; //qué tipo se devuelve?
    }

    @Override
   public void imprimir(String prefijo) {
       /*System.out.println(prefijo + "ExpAsignacion (=)");
       if (ladoIzquierdo != null) {
           System.out.println(prefijo + "  L ->");
           ladoIzquierdo.imprimir(prefijo + "    ");
       }
       if (ladoDerecho != null) {
           System.out.println(prefijo + "  R ->");
           ladoDerecho.imprimir(prefijo + "    ");
       }
        */
       System.out.println(prefijo + "ExpAsignacion (" + operador.getLexema() + ")");
       System.out.println(prefijo + "  L -> " + ladoIzquierdo.formatear());
       System.out.println(prefijo + "  R -> " + ladoDerecho.formatear());
   }


}
