package AST.NodosExpresion;

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

   public void imprimir(String prefijo) {
       System.out.println(prefijo + "ExpAsignacion (" + operador.getLexema() + ")");
       if (ladoIzquierdo != null) ladoIzquierdo.imprimir(prefijo + "  L-> ");
       if (ladoDerecho != null) ladoDerecho.imprimir(prefijo + "  R-> ");
   }

}
