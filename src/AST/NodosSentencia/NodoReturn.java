package AST.NodosSentencia;

import AST.NodosExpresion.NodoExpresion;
import AST.NodosExpresion.NodoExpresionVacia;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import TablaDeSimbolos.*;
import Utils.Token;

public class NodoReturn extends NodoSentencia {
    private Token nombre;
    private NodoExpresion expresionOpcional;
    public NodoReturn(Token nombre) {
        this.nombre = nombre;
    }
    public void setExpresionOpcional(NodoExpresion expresionOpcional) {
        this.expresionOpcional = expresionOpcional;
    }
    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "Return: " + nombre.getLexema());
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();
        Metodo metodoActual = tablaSimbolos.getMetodoActual();

        Tipo tipoRetornoMetodo = metodoActual.getTipoRetorno();

        if(expresionOpcional instanceof NodoExpresionVacia && (!(tipoRetornoMetodo instanceof TipoVoid))){
            throw new ExcepcionSemantica(expresionOpcional.formatear(),nombre.getNroLinea(),"Tipo de retorno vacio, se esperaba "+tipoRetornoMetodo);
        }
        if(!(expresionOpcional instanceof NodoExpresionVacia) && tipoRetornoMetodo instanceof TipoVoid){
            Tipo tipoExpresionOp = expresionOpcional.chequear();
            throw new ExcepcionSemantica(expresionOpcional.formatear(),nombre.getNroLinea(), "Tipo de retorno debe ser void, no "+tipoExpresionOp);
        }
        if(!(expresionOpcional instanceof NodoExpresionVacia)){
           Tipo tipoExpresionOp = expresionOpcional.chequear();

            if(tipoExpresionOp.equals(TipoPrimitivo.NULL)){

            }
            else{
                if(!tipoExpresionOp.esCompatible( tipoRetornoMetodo)){
                    throw new ExcepcionSemantica(expresionOpcional.formatear(), nombre.getNroLinea(), "Tipo de retorno debería es "+tipoRetornoMetodo+" en lugar de "+tipoExpresionOp);
                }
            }

        }
    }

    @Override
    public void generar(ArchivoSalida archivo) throws ExcepcionSemantica {
        System.out.println("Generar de NodoReturn");

        TablaSimbolos ts = TablaSimbolos.tablaSimbolos;
        Metodo metodoActual = ts.getMetodoActual();
        Tipo tipoRetorno = metodoActual.getTipoRetorno();


        //si NO es void, tengo que guardar el valor de retorno
        if(!(tipoRetorno instanceof TipoVoid)){
            expresionOpcional.generar(archivo);
            int cantidadParametros = metodoActual.getParametros().size();
            int offsetRetorno = metodoActual.esMetodoEstatico() ? cantidadParametros + 3 : cantidadParametros + 4;
            archivo.generar(Instrucciones.STORE+" "+offsetRetorno);
            System.out.println("Offset del retorno del metodo "+metodoActual.getNombre()+": "+offsetRetorno);


        }

        String lbl = "lbl_final_"+metodoActual.getNombre()+"@"+metodoActual.getClaseDeclarada().getNombre().getLexema();
        archivo.generar(Instrucciones.JUMP+" "+lbl);


    }
    private void generarRetorno(ArchivoSalida archivo) {

    }
    private void generarSaltoAlFinalDelMetodo(ArchivoSalida archivoSalida){
        Metodo metodoActual = TablaSimbolos.tablaSimbolos.getMetodoActual();
        String nombreMetodo = metodoActual.getNombre();
        String nombreClase = metodoActual.getClaseDeclarada().getNombre().getLexema();

        String label = "lbl_final_"+nombreMetodo+"@"+nombreClase;
        archivoSalida.generar(Instrucciones.JUMP+" "+label);
    }

    @Override
    public String nombreSentencia() {
        return nombre.getLexema();
    }
}
