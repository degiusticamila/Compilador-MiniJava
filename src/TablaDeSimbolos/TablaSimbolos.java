package TablaDeSimbolos;

import Utils.Token;

import java.util.HashMap;

public class TablaSimbolos {
    private static TablaSimbolos tablaSimbolos;

    private HashMap<String,Clase> clases;
    private HashMap<String,Clase> clasesPredefinidas;
    private Clase claseActual;
    private Metodo metodoActual;

    private TablaSimbolos() throws ExcepcionSemantica {
        clasesPredefinidas = new HashMap<>();
        insertarClasesPredefinidas();
        clases = new HashMap<String,Clase>();
    }
    public static TablaSimbolos getInstance() throws ExcepcionSemantica {
        if(tablaSimbolos == null){
            tablaSimbolos = new TablaSimbolos();
        }
        return tablaSimbolos;
    }
    public static void resetInstance() throws ExcepcionSemantica {
        tablaSimbolos = new TablaSimbolos();
    }
    public void insertarClase(String lexema, int numLine, Clase clase) throws ExcepcionSemantica {
        lexema = lexema.trim(); //new
        if(!claseDeclarada(lexema)){
            clases.put(lexema,clase);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine);
        }
    }
    public Metodo getMetodoActual(){
        return metodoActual;
    }
    public Clase getClaseActual(){
        return claseActual;
    }

    public void setClaseActual(Clase claseActual){
        this.claseActual = claseActual;
    }
    public void setMetodoActual(Metodo metodoActual){
        this.metodoActual = metodoActual;
    }
    public boolean claseDeclarada(String nombreClase){
        nombreClase = nombreClase.trim();
        System.out.println("Nombre clase: "+nombreClase);
       // System.out.println(clases.containsKey(nombreClase));
        return clases.containsKey(nombreClase);
        //return contiene(nombreClase);
    }
    public boolean clasePredefinidaDeclarada(String nombreClase){  System.out.println("Nombre clase: "+nombreClase);
        System.out.println( clases.containsKey(nombreClase));
        return clases.containsKey(nombreClase);}
    public HashMap<String, Clase> getClases(){
       /* for(String s : clases.keySet()){
            System.out.println(s);
        }
        */
        return clases;
    }
    private void insertarClasesPredefinidas() throws ExcepcionSemantica {
        Token nombreClaseObject = new Token("idClase", "Object", -1);
        Token nombreClaseSystem = new Token("idClase", "System", -1);
        Token nombreClaseString = new Token("idClase", "String", -1);

        Clase claseObject = new Clase(nombreClaseObject, null);
        Clase claseSystem = new Clase(nombreClaseSystem, null);
        Clase claseString = new Clase(nombreClaseString, null);

        clasesPredefinidas.put("Object",claseObject);
        clasesPredefinidas.put("System",claseSystem);
        clasesPredefinidas.put("String",claseString);

        claseSystem.insertarHerencia(claseObject.getNombre());
        claseString.insertarHerencia(claseObject.getNombre());

        insertarMetodosPredefinidosObject(claseObject);
        insertarMetodosPredefinidosSystem(claseSystem);
        insertarMetodosPredefinidosString(claseString);
    }
    public void insertarMetodosPredefinidosObject(Clase claseObject) throws ExcepcionSemantica {
        //static void debugPrint(int i)
        debugPrint(claseObject);
    }
    public void debugPrint(Clase claseObject) throws ExcepcionSemantica {
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","debugPrint",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid, nombreMetodo);
        claseObject.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoPrimitivo("int");
        Token nombreParam = new Token("idMetvar","i",-1);
        Parametro i = new Parametro(tipoParam,nombreParam,1);
        m.insertarParametro(nombreParam.getLexema(), i,-1);
    }
    public void insertarMetodosPredefinidosSystem(Clase claseSystem) throws ExcepcionSemantica {
        read(claseSystem);
        printB(claseSystem);
        printC(claseSystem);
        printI(claseSystem);
        printS(claseSystem);
        println(claseSystem);
        printBln(claseSystem);
        printCln(claseSystem);
        printIln(claseSystem);
        printSln(claseSystem);
    }
    public void read(Clase claseSystem) throws ExcepcionSemantica {
        //static int read()
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","read",-1);
        Tipo tipoRetorno = new TipoPrimitivo("int");
        Metodo m = new Metodo(modificador,tipoRetorno, nombreMetodo);
        claseSystem.insertarMetodo(nombreMetodo,m);
    }
    public void printB(Clase claseSystem) throws ExcepcionSemantica {
        //static void printB(boolean b)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printB",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoPrimitivo("boolean");
        Token nombreParam = new Token("idMetvar","b",-1);
        Parametro b = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), b,-1);

    }
    public void printC(Clase claseSystem) throws ExcepcionSemantica {
        //static void printC(char c)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printC",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoPrimitivo("char");
        Token nombreParam = new Token("idMetvar","c",-1);
        Parametro c = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), c,-1);
    }
    public void printI(Clase claseSystem) throws ExcepcionSemantica {
        //static void printI(int i)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printI",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoPrimitivo("int");
        Token nombreParam = new Token("idMetvar","i",-1);
        Parametro i = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), i,-1);
    }
    public void printS(Clase claseSystem) throws ExcepcionSemantica {
        //static void printS(String s)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printS",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoReferencia("String");
        Token nombreParam = new Token("idMetvar","s",-1);
        Parametro s = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), s,-1);
    }
    public void println(Clase claseSystem) throws ExcepcionSemantica {
        //static void println()
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","println",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid, nombreMetodo);
        claseSystem.insertarMetodo(nombreMetodo,m);
    }
    public void printBln(Clase claseSystem) throws ExcepcionSemantica {
        //static void printBln(boolean b)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printBln",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoPrimitivo("boolean");
        Token nombreParam = new Token("idMetvar","b",-1);
        Parametro b = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), b,-1);
    }
    public void printCln(Clase claseSystem) throws ExcepcionSemantica {
        //static void printCln(char c)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printCln",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoPrimitivo("char");
        Token nombreParam = new Token("idMetvar","c",-1);
        Parametro c = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), c,-1);
    }
    public void printIln(Clase claseSystem) throws ExcepcionSemantica {
        //static void printIln(int i)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printIln",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoPrimitivo("int");
        Token nombreParam = new Token("idMetvar","i",-1);
        Parametro i = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), i,-1);
    }
    public void printSln(Clase claseSystem) throws ExcepcionSemantica {
        //static void printSln(String s)
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","printSln",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid,nombreMetodo);

        claseSystem.insertarMetodo(nombreMetodo,m);

        Tipo tipoParam = new TipoReferencia("String");
        Token nombreParam = new Token("idMetvar","s",-1);
        Parametro s = new Parametro(tipoParam,nombreParam,1);

        m.insertarParametro(nombreParam.getLexema(), s,-1);
    }
    public void insertarMetodosPredefinidosString(Clase claseString){

    }
    public void consolidacion() throws ExcepcionSemantica {
       /* for(String clase : clases.keySet()){
            clases.get(clase).consolidarClase();
        }
        */
        for(Clase c : clases.values()){
            c.consolidarClase();
        }
    }
    public void imprimirClases() {
        System.out.println("Clases declaradas en TS:");
        for (String s : clases.keySet()) {
            System.out.println(" - " + s);
        }
    }
    public boolean contiene(String aux){
        for(String s : clases.keySet()){
            if(s.equals(aux)){
                return true;
            }
        }
        return false;
    }
}
