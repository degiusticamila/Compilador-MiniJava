package AST.NodosExpresion;

import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoUniversal;
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
    public Tipo chequear() {
        return new TipoUniversal("tipo universal");
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
