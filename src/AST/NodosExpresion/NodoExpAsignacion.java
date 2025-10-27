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

        Tipo tipoLadoDerecho = ladoDerecho.chequear();
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();

        // Si se asigna null a una variable de tipo referencia, la expresión completa
        // adopta el tipo del lado izquierdo (destino), no el tipo null.
        if(tipoLadoDerecho.equals(TipoReferencia.NULL) && tipoLadoIzquierdo.esReferencia()){
             return tipoLadoIzquierdo;
        }
        if(!tipoLadoDerecho.esCompatible(tipoLadoIzquierdo)){
            throw new ExcepcionSemantica(operador.getLexema(),operador.getNroLinea(),"El tipo "+tipoLadoDerecho+" no conforma con "+tipoLadoIzquierdo);
        }
        return tipoLadoDerecho;
    }

    @Override
   public void imprimir(String prefijo) {
       System.out.println(prefijo + "ExpAsignacion (" + operador.getLexema() + ")");
       System.out.println(prefijo + "  L -> " + ladoIzquierdo.formatear());
       System.out.println(prefijo + "  R -> " + ladoDerecho.formatear());
   }


}
