package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;
import java.util.HashSet;

public class Clase {

    private HashMap<String, Atributo> atributos;
    private HashMap<String, Metodo> metodos;
    private Constructor constructor;

    private Token modificador;
    private Token nombre;
    private Token herencia;

    public Clase(Token nombre,Token modificador){
        atributos = new HashMap<>();
        metodos = new HashMap<>();
        this.nombre = nombre;
        this.modificador = modificador;
        //setHerenciaObject();
    }
    public void insertarHerencia(Token herencia) throws ExcepcionSemantica {

        if(nombre.getLexema().equals(herencia.getLexema())){
            // FALTA esClaseEstatica() || esClaseFinal()
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(), "No es posible heredar de la misma clase");
        }
        else{
            this.herencia = herencia;
        }
    }
    public void insertarMetodo(Token nombreMetodo,Metodo m) throws ExcepcionSemantica {
        if(!metodoDeclarado(nombreMetodo.getLexema())){
            metodos.put(nombreMetodo.getLexema(), m);
            /*if(m.esMetodoAbstracto() && !esClaseAbstracta()){
                throw new ExcepcionSemantica(nombre.getLexema(), nombre.getNroLinea());
            }
            else{
                metodos.put(nombreMetodo.getLexema(), m);
            }

             */
        }
        else{
            throw new ExcepcionSemantica(nombreMetodo.getLexema(), nombreMetodo.getNroLinea(), "Metodo ya declarado");
        }
    }
    public void insertarAtributo(Token atributo, Atributo a) throws ExcepcionSemantica {
        if(!atributoDeclarado(atributo.getLexema())){
            atributos.put(atributo.getLexema(), a);
        }
        else{
            throw new ExcepcionSemantica(atributo.getLexema(), atributo.getNroLinea(), "Atributo ya declarado");
        }
    }
    public void insertarConstructor(Token nombreConstructor, Constructor c) throws ExcepcionSemantica {
        if(!constructorDeclarado() && nombre.getLexema().equals(nombreConstructor.getLexema())){
            if(!esClaseAbstracta()){
                this.constructor = c;
            }
            else{
                throw new ExcepcionSemantica(nombreConstructor.getLexema(), nombreConstructor.getNroLinea(), "Constructor en clase abstracta");
            }
        }
        else{
            throw new ExcepcionSemantica(nombreConstructor.getLexema(),nombreConstructor.getNroLinea(), "Error en declaracion de Constructor");
        }
    }
    public boolean esClaseAbstracta(){
        return modificador != null && modificador.getLexema().equals("abstract");
    }
    public boolean esClaseEstatica(){
        return modificador != null && modificador.getLexema().equals("static");
    }
    public boolean esClaseFinal(){
        return modificador != null && modificador.getLexema().equals("final");
    }
    public boolean constructorDeclarado(){
        return constructor != null;
    }
    public boolean metodoDeclarado(String metodo){
        return metodos.containsKey(metodo);
    }
    public boolean atributoDeclarado(String atributo){
        return atributos.containsKey(atributo);
    }
    public Constructor getConstructor(){
        return constructor;
    }
    public void getAtributos(){
        for(String s : atributos.keySet()){
            System.out.println(s+" Atributos : "+atributos.get(s));
        }
    }
    public void getMetodos(){
        for(String s : metodos.keySet()){
            System.out.println(s+" : "+metodos.get(s).toString());
        }
    }
    public String toString() {
        return modificador != null ? ("("+nombre.toString()+", "+modificador.toString()+")") : nombre.toString();
    }
    public void consolidarClase() throws ExcepcionSemantica {
        consolidarAtributos();
        if(!esClaseAbstracta()){
            for(Metodo m : metodos.values()){
                if(m.esMetodoAbstracto()){
                    throw new ExcepcionSemantica(m.getModificador().getLexema(), m.getNombreMetodo().getNroLinea(),"Metodo abstracto en clase concreta");
                }
            }
        }
        consolidarMetodos();
    }
    private void consolidarAtributos() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        for(Atributo a :atributos.values()){
            Tipo tipoAtributo = a.getTipo();
            Token nombreAtributo = a.getNombre();

            System.out.println("Clase: "+ts.obtenerClase(this.nombre.getLexema()).nombre.getLexema()+" Padre: "+this.herencia.getLexema());
            if(!tipoAtributo.esPrimitivo()){
                String nombreTipo = tipoAtributo.getNombre();
                if (!ts.claseDeclarada(nombreTipo) && !ts.clasePredefinidaDeclarada(nombreTipo)) {
                    throw new ExcepcionSemantica(tipoAtributo.getNombre(), nombreAtributo.getNroLinea(), "Atributo de clase no declarada");
                }
            }
        }
        if (herencia == null) {
            setHerenciaObject();
            return;
        }
        Clase padre = TablaSimbolos.getInstance().obtenerClase(herencia.getLexema());
        if (padre == null) {
            throw new ExcepcionSemantica(herencia.getLexema(), nombre.getNroLinea(), "Clase sin padre");
        }
        for(Atributo a : padre.atributos.values()){
            if(this.atributos.containsKey(a.getNombre().getLexema())){
                int linea = this.atributos.get(a.getNombre().getLexema()).getNombre().getNroLinea();
                throw new ExcepcionSemantica(a.getNombre().getLexema(),linea, "Atributos con el mismo nombre");
            }
            this.insertarAtributo(a.getNombre(),a);
        }
    }
    private void consolidarMetodos() throws ExcepcionSemantica {
        if (herencia == null) {
            setHerenciaObject();
            return;
        }
        // Verificar que el padre exista
        Clase padre = TablaSimbolos.getInstance().obtenerClase(herencia.getLexema());
        if (padre == null) {
            throw new ExcepcionSemantica(herencia.getLexema(),nombre.getNroLinea(),"Clase sin padre");
        }
        for(Metodo m : padre.metodos.values()){
            if(this.metodos.containsKey(m.getNombreMetodo().getLexema())){
                int linea = this.metodos.get(m.getNombreMetodo().getLexema()).getNombreMetodo().getNroLinea();
                throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(),linea,"metodos con el mismo nombre");
            }
            this.insertarMetodo(m.getNombreMetodo(),m);
        }
    }
    private void consolidarHerencia(){
        //aca voy a agregarle a la clase de la que hereda todos sus metodos

    }
    public Token getNombre(){
        return nombre;
    }
    public Token getHerencia(){
        return herencia;
    }
    public void setHerenciaObject(){
        Token tokenObject = new Token("Object", "Object",-1);
        herencia = tokenObject;
    }
    public void imprimirResumen() {
        System.out.println("=== Clase: " + nombre.getLexema() + " ===");
        if (modificador != null)
            System.out.println("Modificador: " + modificador.getLexema());
        if (herencia != null)
            System.out.println("Hereda de: " + herencia.getLexema());

        System.out.println("\nAtributos:");
        if (atributos.isEmpty()) {
            System.out.println("  (ninguno)");
        } else {
            for (Atributo a : atributos.values()) {
                System.out.println("  - " + a.getNombre().getLexema() + " : " + a.getTipo().getNombre());
            }
        }

        System.out.println("\nMétodos:");
        if (metodos.isEmpty()) {
            System.out.println("  (ninguno)");
        } else {
            for (Metodo m : metodos.values()) {
                String tipo = (m.getTipoRetorno() != null) ? m.getTipoRetorno().getNombre() : "void";
                String mod = (m.getModificador() != null) ? m.getModificador().getLexema() : "(sin modificador)";
                System.out.println("  - " + m.getNombreMetodo().getLexema() + " : " + tipo + " [" + mod + "]");
            }
        }

        System.out.println("\nConstructor:");
        if (constructor != null)
            System.out.println("  - " + constructor.getNombreConstructor().getLexema());
        else
            System.out.println("  (ninguno)");

        System.out.println("=====================================\n");
    }
}
