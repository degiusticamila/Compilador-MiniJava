package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;
import java.util.HashSet;

public class Clase {

    private HashMap<String, Atributo> atributos;
    private HashMap<String, Metodo> metodos;

    private Token modificador;
    private Token nombre;
    private Token herencia;

    public Clase(Token nombre,Token modificador){
        this.nombre = nombre;
        this.modificador = modificador;
    }
    public void setHerencia(Token herencia) throws ExcepcionSemantica {
        //preguntar si el nombre del ancestro es distinto que el de la clase
        if(nombre.getLexema().equals(herencia.getLexema())){
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea());
        }
        else{
            this.herencia = herencia;
        }
    }
}
