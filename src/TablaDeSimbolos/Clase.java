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
    }
    public void insertarHerencia(Token herencia) throws ExcepcionSemantica {
        //chequear que esa clase exista
        if(nombre.getLexema().equals(herencia.getLexema())){
            // || esClaseEstatica() || esClaseFinal()
            throw new ExcepcionSemantica("Una clase no puede heredarse a sí misma: "+nombre.getLexema(),nombre.getNroLinea());
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
            throw new ExcepcionSemantica(nombreMetodo.getLexema(), nombreMetodo.getNroLinea());
        }
    }

    public void insertarAtributo(Token atributo, Atributo a) throws ExcepcionSemantica {
        if(!atributoDeclarado(atributo.getLexema())){
            atributos.put(atributo.getLexema(), a);
        }
        else{
            throw new ExcepcionSemantica(atributo.getLexema(), atributo.getNroLinea());
        }
    }
    public void insertarConstructor(Token nombreConstructor, Constructor c) throws ExcepcionSemantica {
        if(!constructorDeclarado() && nombre.getLexema().equals(nombreConstructor.getLexema())){
            if(!esClaseAbstracta()){
                this.constructor = c;
            }
            else{
                throw new ExcepcionSemantica(nombreConstructor.getLexema(), nombreConstructor.getNroLinea());
            }
        }
        else{
            throw new ExcepcionSemantica(nombreConstructor.getLexema(),nombreConstructor.getNroLinea());
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
                    throw new ExcepcionSemantica(m.getModificador().getLexema(), m.getNombreMetodo().getNroLinea());
                }
            }
        }
    }
    private void consolidarAtributos() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        for(Atributo a :atributos.values()){
            Tipo tipoAtributo = a.getTipo();
            if(!tipoAtributo.esPrimitivo()){
               /* System.out.println("Tipo atributo :"+tipoAtributo.getNombre());

                if(!TablaSimbolos.getInstance().claseDeclarada(tipoAtributo.getNombre()) && !TablaSimbolos.getInstance().clasePredefinidaDeclarada(tipoAtributo.getNombre())){
                    //Atributo de clase no definida
                    System.out.println("Comparando tipos:");
                    for (String s : TablaSimbolos.getInstance().getClases().keySet()) {
                        System.out.println("'" + s + "' == '" + tipoAtributo.getNombre() + "' → " + s.equals(tipoAtributo.getNombre()));
                    }
                   throw new ExcepcionSemantica(tipoAtributo.getNombre(), a.getNombre().getNroLinea());
                }

                */
                String nombreTipo = tipoAtributo.getNombre();
                if (!ts.claseDeclarada(nombreTipo) && !ts.clasePredefinidaDeclarada(nombreTipo)) {
                    throw new ExcepcionSemantica(tipoAtributo.getNombre(), a.getNombre().getNroLinea());
                }
            }


        }
    }
    public Token getNombre(){
        return nombre;
    }
}
