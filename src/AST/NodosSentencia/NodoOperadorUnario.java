package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.ExcepcionSemantica;
import TablaDeSimbolos.Tipo;
import TablaDeSimbolos.TipoPrimitivo;
import TablaDeSimbolos.TipoUniversal;
import Utils.Token;

public class NodoOperadorUnario extends NodoExpresion{
    private NodoExpresion ladoDerecho;
    private Token nombre;

    public NodoOperadorUnario(Token nombre, NodoExpresion ladoDerecho) {
        this.nombre = nombre;
        this.ladoDerecho = ladoDerecho;
    }

    @Override
    public String formatear() {
        return nombre.getLexema() + ladoDerecho.formatear();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {

        Tipo tipoOperando = ladoDerecho.chequear();
        if(tipoOperando != null){
            if(!nombre.getLexema().equals("!")){
                if(!tipoOperando.esCompatible(new TipoPrimitivo("int"))){
                    throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "El tipo "+tipoOperando+" es incompatible con el operador "+nombre.getLexema());
                }
                return new TipoPrimitivo("int");
            }
            else{
                if(!tipoOperando.esCompatible(new TipoPrimitivo("boolean"))){
                    throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea(), "Tipos incompatibles");
                }
                return new TipoPrimitivo("boolean");
            }
        }

        return new TipoUniversal("tipo universal");
    }

    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "OperadorUnario (" + nombre.getLexema() + ")");
        System.out.println(prefijo + "  R -> " + ladoDerecho.formatear());

    }

    public Token getNombre(){
        return nombre;
    }

    @Override
    public void setOperador(Token operador) {

    }

    @Override
    public void setLadoIzquierdo(NodoExpresion nodoExpresion) {

    }

    public void setLadoDerecho(NodoExpresion ladoDerecho){
        this.ladoDerecho = ladoDerecho;
    }
    @Override
    public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {
        System.out.println("Generando codigo en Unarios");

        ladoDerecho.generar(archivo);
        if(nombre.getLexema().equals("!")){
            archivo.generar(Instrucciones.NOT+"");
        }
        else if(nombre.getLexema().equals("+")){
          //TO-DO

        }
        else if(nombre.getLexema().equals("-")){
            archivo.generar(Instrucciones.NEG + "");
        }
        else if(nombre.getLexema().equals("++")){
            archivo.generar(Instrucciones.PUSH+" 1");
            archivo.generar(Instrucciones.ADD+"");

            ladoDerecho.setEsLadoIzq();
            ladoDerecho.generar(archivo);
            ladoDerecho.setEsLadoIzq();
        }
        else if(nombre.getLexema().equals("--")){
            archivo.generar(Instrucciones.PUSH+" 1");
            archivo.generar(Instrucciones.SUB+"");

            ladoDerecho.setEsLadoIzq();
            ladoDerecho.generar(archivo);
            ladoDerecho.setEsLadoIzq();
        }

    }

}
