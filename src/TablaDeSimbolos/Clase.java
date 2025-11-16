package TablaDeSimbolos;

import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import Utils.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Clase {

    private HashMap<String, Atributo> atributos;
    private HashMap<String, Metodo> metodos;
    private List<Metodo> metodosOrdenados;
    private HashMap<String, Metodo> metodosPropios;
    private Constructor constructor;
    private Token modificador;
    private Token nombre;
    private Token herencia;

    public Clase(Token nombre,Token modificador){
        atributos = new HashMap<>();
        metodos = new HashMap<>();
        metodosPropios = new HashMap<>();
        metodosOrdenados = new ArrayList<>();
        this.nombre = nombre;
        this.modificador = modificador;
    }
    public void insertarHerencia(Token herencia) throws ExcepcionSemantica {
        if(nombre.getLexema().equals(herencia.getLexema())){
            throw new ExcepcionSemantica(nombre.getLexema(),nombre.getNroLinea(), "No es posible heredar de la misma clase");
        }
        else{
            this.herencia = herencia;
        }
    }
    public void insertarMetodo(Token nombreMetodo,Metodo m) throws ExcepcionSemantica {
        if(!metodoDeclarado(nombreMetodo.getLexema())){
            metodos.put(nombreMetodo.getLexema(), m);
            metodosPropios.put(nombreMetodo.getLexema(), m);
            metodosOrdenados.addFirst(m);
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
    public Tipo getTipoAtributo(String atributo){
        return atributos.get(atributo).getTipo();
    }
    public Constructor getConstructor(){
        return constructor;
    }
    public HashMap<String,Metodo> getMetodosPropios(){return metodosPropios;}
    public Metodo getMetodo(String nombreMetodo){
        return metodos.get(nombreMetodo);
    }
    public void getAtributos(){
        for(String s : atributos.keySet()){
            System.out.println(s+" Atributos : "+atributos.get(s));
        }
    }
    public HashMap<String, Atributo> getMapAtributos(){
        return atributos;
    }
    public void getMetodos(){
        for(String s : metodos.keySet()){
            System.out.println(s+" : "+metodos.get(s).toString());
        }
    }
    public HashMap<String, Metodo> getMetodosHeredados(){
        HashMap<String, Metodo> heredados = new HashMap<>();
        for(String nombre : metodos.keySet()){
            if(!metodosPropios.containsKey(nombre)){
                heredados.put(nombre,metodos.get(nombre));
            }
        }
        return heredados;
    }

    public String toString() {
        return modificador != null ? ("("+nombre.toString()+", "+modificador.toString()+")") : nombre.toString();
    }
    public void consolidarClase() throws ExcepcionSemantica {
        if(!esClaseAbstracta()){
            for(Metodo m : metodos.values()){
                if(m.esMetodoAbstracto()){
                    throw new ExcepcionSemantica(m.getModificador().getLexema(), m.getNombreMetodo().getNroLinea(),"Metodo abstracto en clase concreta");
                }
            }
        }
        Clase padre = TablaSimbolos.getInstance().obtenerClase(herencia.getLexema());
        if(padre != null){
            if(padre.getModificador() != null && padre.getModificador().getLexema().equals("final")){
                throw new ExcepcionSemantica(padre.getNombre().getLexema(),this.getNombre().getNroLinea(),"Padre final");
            }
            if(this.esClaseAbstracta() && padre.getModificador() == null && !padre.getNombre().getLexema().equals("Object")){
                throw new ExcepcionSemantica(padre.getNombre().getLexema(), this.getNombre().getNroLinea(),"Una clase abstracta no puede heredar de una concreta");
            }
        }
        else{
            throw new ExcepcionSemantica(herencia.getLexema(),this.getNombre().getNroLinea(),"Padre no encontrado");
        }
        chequearAtributos();
        consolidarMetodos();
    }
    private void chequearAtributos() throws ExcepcionSemantica {
        TablaSimbolos ts = TablaSimbolos.getInstance();
        for(Atributo a :atributos.values()){
            Tipo tipoAtributo = a.getTipo();
            Token nombreAtributo = a.getToken();
            if(!tipoAtributo.esPrimitivo()){
                String nombreTipo = tipoAtributo.getNombre();
                if (!ts.claseDeclarada(nombreTipo) && !ts.clasePredefinidaDeclarada(nombreTipo)) {
                    throw new ExcepcionSemantica(tipoAtributo.getNombre(), nombreAtributo.getNroLinea(), "Atributo de clase no declarada");
                }
            }
        }
        consolidarAtributos();
    }
    private void consolidarAtributos() throws ExcepcionSemantica {
        Clase padre = TablaSimbolos.getInstance().obtenerClase(herencia.getLexema());
        if (padre == null) {
            throw new ExcepcionSemantica(herencia.getLexema(), nombre.getNroLinea(), "Clase sin padre");
        }
        for(Atributo a : padre.atributos.values()){
            if(this.atributos.containsKey(a.getNombre())){
                int linea = this.atributos.get(a.getNombre()).getLinea();
                throw new ExcepcionSemantica(a.getNombre(),linea, "Atributos con el mismo nombre");
            }
            this.insertarAtributo(a.getToken(),a);
        }
    }
    private void consolidarMetodos() throws ExcepcionSemantica {
        if (herencia == null) {
            setHerenciaObject();
            return;
        }
        Clase padre = TablaSimbolos.getInstance().obtenerClase(herencia.getLexema());
        if (padre == null) {
            throw new ExcepcionSemantica(herencia.getLexema(),nombre.getNroLinea(),"Clase sin padre");
        }
        chequearTipoRetornoMetodo();
        chequearTipoParametroMetodo();
        chequearRedefinicionMetodosAbstractos();
        for(Metodo m : metodos.values()){
            Metodo metodoPadre = padre.metodos.get(m.getNombreMetodo().getLexema());
            if(metodoPadre != null){
                if(metodoPadre.getModificador() == null && (m.getModificador() != null && m.getModificador().getLexema().equals("static"))){
                    throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(), m.getNombreMetodo().getNroLinea(),"Metodos inconsistentes");
                }
                if(!metodoPadre.getTipoRetorno().getNombre().equals(m.getTipoRetorno().getNombre())){
                    throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(), m.getNombreMetodo().getNroLinea(),"Tipos incompatible");
                }
                if(metodoPadre.getParametros().size() == m.getParametros().size()){
                    if(!metodoPadre.getParametros().isEmpty()) {
                        Parametro primeroHijo = m.getParametros().getFirst();
                        Parametro primeroPadre = metodoPadre.getParametros().getFirst();
                        for (int i = 0; i < m.getParametros().size(); i++) {
                            if (!primeroHijo.equals(primeroPadre)) {
                                throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(), primeroHijo.getLinea(), "Parametros incompatible");
                            }
                            primeroHijo = m.getParametros().get(i);
                            primeroPadre = metodoPadre.getParametros().get(i);
                        }
                    }
                }
                else{
                    throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(),m.getNombreMetodo().getNroLinea(),"Parametros incompatible");
                }
                if(metodoPadre.getModificador() != null && metodoPadre.getModificador().getLexema().equals("abstract")){
                    if(m.getModificador() != null && m.getModificador().getLexema().equals("abstract") && this.esClaseAbstracta()){
                        throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(),m.getNombreMetodo().getNroLinea(),"Metodo abstracto en clase concreta");
                    }
                }
                if(metodoPadre.getModificador() != null && metodoPadre.getModificador().getLexema().equals("final")){
                    throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(), m.getNombreMetodo().getNroLinea(),"No se puede redefinir metodos final");
                }
                if(metodoPadre.getModificador() != null && metodoPadre.getModificador().getLexema().equals("static")){
                    throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(), m.getNombreMetodo().getNroLinea(),"No se puede redefinir metodos estáticos");
                }
            }

        }
        for(Metodo metodoPadre : padre.metodos.values()){
            String nombreMetodoPadre = metodoPadre.getNombreMetodo().getLexema();
            if(!this.metodos.containsKey(metodoPadre.getNombreMetodo().getLexema())){
                this.metodos.put(nombreMetodoPadre, metodoPadre);
                //System.out.println("→ Heredado metodo "+nombreMetodoPadre+" de "+padre.getNombre().getLexema()+" en "+this.nombre.getLexema());
            }
            else{
                //System.out.println("→ Metodo "+nombreMetodoPadre+ "redefinido en "+this.nombre.getLexema());
            }
        }
    }
    public void chequearRedefinicionMetodosAbstractos() throws ExcepcionSemantica {
        Clase padre = TablaSimbolos.getInstance().obtenerClase(this.getHerencia().getLexema());
        if(padre != null && padre.esClaseAbstracta()){
            for(Metodo metodoPadre : padre.metodos.values()){
                if(metodoPadre.esMetodoAbstracto()){
                    Metodo metodoImplementado = this.metodos.get(metodoPadre.getNombreMetodo().getLexema());

                    boolean estaImplementado = false;
                    if(metodoImplementado != null && !metodoImplementado.esMetodoAbstracto()){

                        estaImplementado = true;
                    }
                    if(!estaImplementado && !this.esClaseAbstracta()){

                        throw new ExcepcionSemantica(this.getNombre().getLexema(),this.getNombre().getNroLinea(),"Metodo "+metodoPadre.getNombreMetodo().getLexema()+" no implementado en clase "+this.nombre.getLexema());
                    }
                }
            }
        }
    }
    private void chequearTipoRetornoMetodo() throws ExcepcionSemantica {
        for(Metodo m : metodos.values()){
            Tipo tipoRetorno = m.getTipoRetorno();
            if(tipoRetorno == null) continue;
            if(!tipoRetorno.getNombre().equals("void") && !tipoRetorno.esPrimitivo()){
                if(!TablaSimbolos.getInstance().clasePredefinidaDeclarada(tipoRetorno.getNombre()) && !TablaSimbolos.getInstance().claseDeclarada(tipoRetorno.getNombre())){
                    throw new ExcepcionSemantica(tipoRetorno.getNombre(),m.getNombreMetodo().getNroLinea(),"Tipo retorno incompatible");
                }
            }
        }
    }
    private void chequearTipoParametroMetodo() throws ExcepcionSemantica {
        for(Metodo m : metodos.values()){
            if(!m.getParametros().isEmpty()){
                for(Parametro p : m.getParametros()) {
                    if(!p.getTipo().esPrimitivo()){
                        Tipo tipoParametro = p.getTipo();
                        if(tipoParametro != null){
                            if(!TablaSimbolos.getInstance().claseDeclarada(tipoParametro.getNombre()) && !TablaSimbolos.getInstance().clasePredefinidaDeclarada(tipoParametro.getNombre())){
                                throw new ExcepcionSemantica(tipoParametro.getNombre(), m.getNombreMetodo().getNroLinea(), "Tipo de Parametro incompatible");
                            }
                        }
                    }
                }
            }
        }
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
                System.out.println("  - " + a.getNombre() + " : " + a.getTipo().getNombre());
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

                //  Mostrar parámetros si tiene
                if (m.getParametros() != null && !m.getParametros().isEmpty()) {
                    for (Parametro p : m.getParametros()) {
                        System.out.println("       ▹ Param: " + p.getNombre() + " : " + p.getTipo().getNombre());
                    }
                } else {
                    System.out.println("       ▹ (sin parámetros)");
                }
                if(m.getBloque() != null){
                    m.getBloque().imprimir(" ");
                }
            }
        }
        if (constructor != null) {
            System.out.println("  - " + constructor.getNombreConstructor().getLexema());
            if (constructor.getParametros() != null && !constructor.getParametros().isEmpty()) {
                for (Parametro p : constructor.getParametros()) {
                    System.out.println("       ▹ Param: " + p.getNombre() + " : " + p.getTipo().getNombre());
                }
                if(constructor.getBloque() != null){
                    constructor.getBloque().imprimir(" ");
                }
            } else {
                System.out.println("       ▹ (sin parámetros)");
            }
            if(constructor.getBloque() != null){
                constructor.getBloque().imprimir(" ");
            }
        } else {
            System.out.println("  (ninguno)");
        }
        System.out.println("=====================================\n");
    }
    public Token getModificador(){
        return modificador;
    }
    public HashMap<String, Metodo> metodos(){
        return metodos;
    }
    public Atributo getAtributo(String lexema){
        return atributos.get(lexema);
    }
    public void generarCodigo(ArchivoSalida archivo) throws ExcepcionSemantica {
        //ordenarMetodos
        //ordenarAtributos

        archivo.generar(".DATA");
        archivo.generar("VT@"+nombre.getLexema()+": ");

        if(TablaSimbolos.tablaSimbolos.getClasesPredefinidas().containsKey(nombre.getLexema()) || this.equals(TablaSimbolos.tablaSimbolos.obtenerClaseMain())){
            archivo.generar(""+Instrucciones.NOP);
        }
        else{
            //Clase definida por el usuario
            for(Metodo m : metodos.values()){
                if(!m.esMetodoEstatico()){
                    archivo.generar(Instrucciones.DW+" lbl_"+m.getNombreMetodo().getLexema()+"@"+nombre.getLexema());
                }
            }
        }


        archivo.generar("");
        archivo.generar(".CODE");
        for(Metodo m : metodosPropios.values()){
            System.out.println(m.getNombre()+" de clase "+nombre.getLexema());
            archivo.generar("lbl_"+m.getNombre()+"@"+nombre.getLexema()+": "+ Instrucciones.LOADFP);
            m.generar(archivo);
        }
        archivo.generar("");
        generarCodigoConstructor(archivo);

    }
    public void calcularOffsetMetodos(){

    }
    public List<Metodo> mapeoAlista(HashMap<String, Metodo> metodos){
        List<Metodo> lista;
        lista = new ArrayList<>(metodos.values());
        return lista;
    }
    public void generarCodigoConstructor(ArchivoSalida archivo){

        //ESTA HARDCODEADO DE MOMENTO!
        if(nombre.getLexema().equals("Object") || nombre.getLexema().equals("System") || nombre.getLexema().equals("String")){
            archivo.generar("lbl_constructor@"+nombre.getLexema()+": "+Instrucciones.NOP);
        }
        else if(constructor == null){
            archivo.generar("lbl_constructor@"+nombre.getLexema()+": "+Instrucciones.NOP);
        }
        else{
            archivo.generar("lbl_constructor@"+nombre.getLexema()+": LOADFP");
            archivo.generar("LOADSP");
            archivo.generar("STOREFP");
            generarRetornoConstructor(archivo);
        }
    }
    public void generarRetornoConstructor(ArchivoSalida archivo){
        archivo.generar("FMEM 0");
        archivo.generar("STOREFP");
        archivo.generar("RET 1");
    }
}
