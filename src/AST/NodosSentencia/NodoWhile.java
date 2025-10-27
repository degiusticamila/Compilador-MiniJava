package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import Utils.Token;

public class NodoWhile extends NodoSentencia {
    private Token tokenWhile;
    private NodoSentencia sentencia;
    private NodoExpresion expresion;

    public NodoWhile(Token tokenWhile, NodoExpresion expresion, NodoSentencia sentencia){
        this.tokenWhile = tokenWhile;
        this.expresion = expresion;
        this.sentencia =  sentencia;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo+"While:");
        if(expresion != null){
            System.out.println(prefijo+ " Condicion:");
            expresion.imprimir(prefijo);
        }
        if(sentencia != null){
            System.out.println(prefijo+ " Cuerpo:");
            sentencia.imprimir(prefijo);
        }
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoExpresion = expresion.chequear();
        if(!tipoExpresion.esCompatible(new TipoPrimitivo("boolean"))){
            //Extender de getNombre desde expresion para pedir el token.
            throw new ExcepcionSemantica(expresion.formatear(),tokenWhile.getNroLinea(), "El tipo de la expresion es de tipo "+tipoExpresion+" y no es compatible con boolean");
        }
        sentencia.chequear();
    }
}
