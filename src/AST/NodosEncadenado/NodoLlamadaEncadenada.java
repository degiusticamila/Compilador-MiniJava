package AST.NodosEncadenado;

import AST.NodosExpresion.NodoExpresion;
import TablaDeSimbolos.*;
import Utils.Token;
import java.util.List;

public class NodoLlamadaEncadenada extends NodoEncadenado {
    private List<NodoExpresion> parametros;
    private NodoEncadenado encadenado;
    public NodoLlamadaEncadenada(Token nombre, NodoEncadenado encadenado, List<NodoExpresion> parametros) {
        super(nombre);
        this.parametros = parametros;
        this.encadenado = encadenado;
    }
    @Override
    public Tipo chequear(Tipo t) throws ExcepcionSemantica {
        if(!t.esReferencia()){
            throw new ExcepcionSemantica(super.nombre.getLexema(), super.nombre.getNroLinea(), "Encadenado sobre tipo no definido o primitivo: "+ t.getNombre());
        }
        TablaSimbolos ts = TablaSimbolos.getInstance();
        Clase clase = ts.obtenerClase(t.getNombre());
        if(clase == null){
            throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(), "Tipo no definido "+t.getNombre());
        }
        if(!clase.metodoDeclarado(super.nombre.getLexema())){
            throw new ExcepcionSemantica(super.nombre.getLexema(), super.nombre.getNroLinea(), "Metodo "+super.nombre.getLexema()+" encadenado no declarado en clase: "+clase.getNombre().getLexema());
        }
        Metodo metodo = clase.getMetodo(super.nombre.getLexema());

        if(parametros.size() != metodo.getParametros().size()){
            throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(), "La cantidad de parametros es incorrecta, deben ser "+clase.getMetodo(super.nombre.getLexema()).getParametros().size());
        }

        //Correspondencia entre parametros de la llamada
        for(int i = 0; i<parametros.size(); i++){
            Tipo tipoActual = parametros.get(i).chequear();
            Tipo tipoFormal = metodo.getParametros().get(i).getTipo();

            if(!tipoActual.esCompatible(tipoFormal)){
                throw new ExcepcionSemantica(super.nombre.getLexema(),super.nombre.getNroLinea(),"El argumento "+ (i+1)+ "no es compatible: se esperaba "+tipoFormal.getNombre()+" y recibe "+tipoActual.getNombre());
            }
        }
        Tipo tipoRetorno = metodo.getTipo();

        if(!(encadenado instanceof NodoEncadenadoVacio)){
            return encadenado.chequear(tipoRetorno);
        }
        return tipoRetorno;
        /* 1. Verificar que tipoBase sea una clase (no primitivo)
        2. Buscar si el método "nombre" existe en esa clase
        3. Verificar cantidad de parámetros e invocación correcta
        4. Verificar conformidad de cada parámetro actual con el tipo formal
        5. Obtener el tipo de retorno del método encontrado
        6. Si el encadenado continúa → delegar: encadenado.chequear(tipoRetorno)
        7. Si no → retornar tipoRetorno*/

    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + nombre.getLexema()+"(");
        for(int i = 0; i < parametros.size(); i++){
            System.out.println(parametros.get(i).formatear());
            if(i < parametros.size()-1){
                System.out.println(", ");
            }
        }
        System.out.println(")");
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            System.out.println(".");
            encadenado.imprimir(prefijo);
        }
    }

    @Override
    public String formatear() {
        StringBuilder s = new StringBuilder(nombre.getLexema()+"(");
        for(int i = 0; i < parametros.size(); i++){
            s.append(parametros.get(i).formatear());
            if(i < parametros.size()-1){
                s.append(", ");
            }
        }
        s.append(")");
        if(!(encadenado instanceof NodoEncadenadoVacio)){
            s.append(".").append(encadenado.formatear());
        }
        return s.toString();
    }

    @Override
    public void setEncadenado(NodoEncadenado encadenado) {
        this.encadenado = encadenado;
    }
}
