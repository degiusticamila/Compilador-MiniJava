package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import Utils.Token;

public class NodoIf extends NodoSentencia {
    private Token tokenif;
    private NodoExpresion condicion;
    private NodoSentencia sentenciaIf;
    private NodoSentencia sentenciaElse;
    public NodoIf(Token tokenif,NodoExpresion condicion, NodoSentencia sentenciaIf, NodoSentencia sentenciaElse) {
        this.tokenif = tokenif;
        this.condicion = condicion;
        this.sentenciaIf = sentenciaIf;
        this.sentenciaElse = sentenciaElse;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo+"If");
        if(condicion != null){
            System.out.println(prefijo+ " Condicion:");
            condicion.imprimir(prefijo);
        }
        if(sentenciaIf != null){
            System.out.println(prefijo+ " Cuerpo if:");
            sentenciaIf.imprimir(prefijo+ " ");
        }
        if(sentenciaElse != null && !(sentenciaElse instanceof NodoSentenciaVacia)){
            System.out.println(prefijo+ " Cuerpo else:");
            sentenciaElse.imprimir(prefijo+ " ");

        }
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoCondicion = condicion.chequear();
        if(!tipoCondicion.esCompatible(new TipoPrimitivo("boolean"))){
            throw new ExcepcionSemantica(condicion.formatear(), tokenif.getNroLinea(), "El tipo de la condicion "+tipoCondicion+" no es compatible con boolean");
        }
        sentenciaIf.chequear();
        if(!(sentenciaElse instanceof NodoSentenciaVacia)){
            sentenciaElse.chequear();
        }
    }

    @Override
    public void generar(ArchivoSalida archivo) {

    }
}
