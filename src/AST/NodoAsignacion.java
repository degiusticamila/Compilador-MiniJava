package AST;

public class NodoAsignacion extends NodoSentencia{
    NodoExpresion nodoExpAsignacion;

    public NodoAsignacion(){

    }

    public void setNodoExpAsignacion(NodoExpresion nodoExpAsignacion) {
        this.nodoExpAsignacion = nodoExpAsignacion;
    }

    @Override
    public void imprimir(String prefijo) {
        System.out.println(prefijo + "Asignacion:");
        if(nodoExpAsignacion!=null){
            nodoExpAsignacion.imprimir(prefijo + "  ");
        }

    }
}
