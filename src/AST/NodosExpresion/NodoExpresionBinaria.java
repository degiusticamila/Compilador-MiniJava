package AST.NodosExpresion;

import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoExpresionBinaria extends NodoExpresionCompuesta {
    private Token operador;
    private NodoExpresion ladoIzquierdo;
    private NodoExpresion ladoDerecho;

    public NodoExpresionBinaria(Token operador, NodoExpresion  ladoIzquierdo, NodoExpresion ladoDerecho) {
        this.operador = operador;
        this.ladoIzquierdo = ladoIzquierdo;
        this.ladoDerecho = ladoDerecho;
    }
    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    @Override
    public void setLadoDerecho(NodoExpresion nodoExpresion) {

    }
    @Override
    public String formatear() {
        /*return "(" + ladoIzquierdo.formatear() + " " + operador.getLexema() + " " + ladoDerecho.formatear() + ")";
         */

            String izq = ladoIzquierdo.formatear();
            String der = ladoDerecho.formatear();

            // Si los hijos ya son una operación completa, devolvemos sin duplicar paréntesis externos
           /* if (ladoIzquierdo instanceof NodoExpresionBinaria) {
                izq = "(" + izq + ")";
            }
            if (ladoDerecho instanceof NodoExpresionBinaria) {
                der = "(" + der + ")";
            }

            */
            return izq + " " + operador.getLexema() + " " + der;
    }

    @Override
    public void imprimir(String prefijo) {
       /* System.out.println(prefijo + "ExpBinaria (" + operador.getLexema() + ")");
        if (ladoIzquierdo != null) {
            System.out.println(prefijo + "  L ->");
            ladoIzquierdo.imprimir(prefijo + "    ");
        }
        if (ladoDerecho != null) {
            System.out.println(prefijo + "  R ->");
            ladoDerecho.imprimir(prefijo + "    ");
        }
        */
        System.out.println(prefijo + "ExpBinaria (" + operador.getLexema() + ")");
        System.out.println(prefijo + "  L -> " + ladoIzquierdo.formatear());
        System.out.println(prefijo + "  R -> " + ladoDerecho.formatear());
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {

        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        Tipo tipoLadoDerecho = ladoDerecho.chequear();

        if(operador.getLexema().equals("+") || operador.getLexema().equals("-") || operador.getLexema().equals("*") || operador.getLexema().equals("/") || operador.getLexema().equals("%")) {
            if(!tipoLadoIzquierdo.esCompatible(new TipoPrimitivo("int"))){
               throw new ExcepcionSemantica(operador.getLexema(), operador.getNroLinea(),"El tipo "+tipoLadoIzquierdo+" no es compatible con el operador "+operador.getLexema());
            }
            if(!tipoLadoDerecho.esCompatible(new TipoPrimitivo("int"))){
                throw new ExcepcionSemantica(operador.getLexema(),operador.getNroLinea(), "El tipo "+tipoLadoDerecho+" no es compatible con el operador "+operador.getLexema());
            }
            return new TipoPrimitivo("int");
        } else if(operador.getLexema().equals("&&") || operador.getLexema().equals("||")){
            if(!tipoLadoIzquierdo.esCompatible(new TipoPrimitivo("boolean"))){
                throw new ExcepcionSemantica(operador.getLexema(), operador.getNroLinea(), "El tipo "+tipoLadoIzquierdo+" no es compatible con el operador "+operador.getLexema());
            }
            if(!tipoLadoDerecho.esCompatible(new TipoPrimitivo("boolean"))){
                throw new ExcepcionSemantica(operador.getLexema(),operador.getNroLinea(), "El tipo "+tipoLadoDerecho+" no es compatible con el operador "+operador.getLexema());
            }
            return new TipoPrimitivo("boolean");
        }else if(operador.getLexema().equals("==") || operador.getLexema().equals("!=")){
            //MANEJAR HERENCIA de TIPOS
            if(!tipoLadoIzquierdo.esCompatible(tipoLadoDerecho)){
                throw new ExcepcionSemantica(operador.getLexema(), operador.getNroLinea(),"El tipo "+tipoLadoIzquierdo+" no es compatible con el tipo "+tipoLadoDerecho);
            }
            return new TipoPrimitivo("boolean");
        } else if(operador.getLexema().equals("<") || operador.getLexema().equals(">") || operador.getLexema().equals("<=") || operador.getLexema().equals(">=")){
            if(!tipoLadoIzquierdo.esCompatible(new TipoPrimitivo("int"))){
                throw new ExcepcionSemantica(operador.getLexema(), operador.getNroLinea(), "El tipo "+tipoLadoIzquierdo+" no es compatible con el operador "+operador.getLexema());
            }
            if(!tipoLadoDerecho.esCompatible(new TipoPrimitivo("int"))){
                throw new ExcepcionSemantica(operador.getLexema(), operador.getNroLinea(), "El tipo "+tipoLadoDerecho+" no es compatible con el operador "+operador.getLexema());
            }
            return new TipoPrimitivo("boolean");
        }
        else{
            return new TipoUniversal("tipo universal");
        }

    }
}
