package AST.NodosExpresion;

import AST.NodosOperando.NodoAccesoVar;
import AST.NodosOperando.NodoLlamadaMetodo;
import AST.NodosSentencia.NodoVarLocal;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
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
        TablaSimbolos ts = TablaSimbolos.getInstance();

        Tipo tipoLadoDerecho = ladoDerecho.chequear();
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        System.out.println();
        System.out.println(tipoLadoIzquierdo.getNombre());
        System.out.println();

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
    public void generar(ArchivoSalida archivo) {
        System.out.println();
        System.out.println("Entro a generar codigo de NodoExpAsignacion");
        System.out.println();
       ladoDerecho.generar(archivo); //me genera el 33
        if(ladoIzquierdo instanceof NodoAccesoVar nodoAccesoVar){
            Elemento referencia = nodoAccesoVar.getReferenciaTS();

            if(referencia instanceof NodoVarLocal varLocal){
                archivo.generar(Instrucciones.STORE+ " "+varLocal.getOffset());
            }
            else if(referencia instanceof Parametro p){
                archivo.generar(Instrucciones.STORE+ " "+p.getOffset());
            }
            else if(referencia instanceof Atributo a){
                int offsetAtributo = a.getOffset();
                archivo.generar(Instrucciones.LOAD+" 3");
                archivo.generar(Instrucciones.SWAP+"");
                archivo.generar(Instrucciones.STOREREF+" "+offsetAtributo);
            }
        }
       // archivo.generar(""+ Instrucciones.STORE+ );
        //si tengo x = 33
        //tengo que
        // apilar el 33
        //guardar a 33 en la direccion de memoria de x

    }

    @Override
    public String nombreSentencia() {
        return operador.getLexema();
    }

    @Override
   public void imprimir(String prefijo) {
       System.out.println(prefijo + "ExpAsignacion (" + operador.getLexema() + ")");
       System.out.println(prefijo + "  L -> " + ladoIzquierdo.formatear());
       System.out.println(prefijo + "  R -> " + ladoDerecho.formatear());
   }


}
