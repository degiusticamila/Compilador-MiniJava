package TablaDeSimbolos;

import AST.NodosSentencia.Bloques.*;
import AST.NodosSentencia.NodoBloque;
import AST.NodosSentencia.NodoBloqueVacio;
import ArchivoSalida.ArchivoSalida;
import GeneracionCodigo.Instrucciones;
import Utils.SourceManager;
import Utils.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TablaSimbolos {

    public static TablaSimbolos tablaSimbolos;
    private List<ExcepcionSemantica> erroresSemanticos = new ArrayList<>();
    private HashMap<String,Clase> clases;
    private HashMap<String,Clase> clasesPredefinidas;
    private Clase claseActual;
    private Metodo metodoActual;
    private NodoBloque bloqueActual;
    private TablaSimbolos() throws ExcepcionSemantica {
        clasesPredefinidas = new HashMap<>();
        insertarClasesPredefinidas();
        clases = new HashMap<String,Clase>();
        bloqueActual = new NodoBloqueVacio();
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
        lexema = lexema.trim();
        if(!clasePredefinidaDeclarada(lexema) && !claseDeclarada(lexema)){
            clases.put(lexema,clase);
        }
        else{
            throw new ExcepcionSemantica(lexema,numLine,"Clase ya declarada");
        }
    }
    public Metodo getMetodoActual(){
        return metodoActual;
    }
    public Clase getClaseActual(){
        return claseActual;
    }
    public NodoBloque getBloqueActual(){
        return bloqueActual;
    }
    public void setClaseActual(Clase claseActual){
        this.claseActual = claseActual;
    }
    public void setMetodoActual(Metodo metodoActual){
        this.metodoActual = metodoActual;
    }
    public void setBloqueActual(NodoBloque  bloqueActual){
        this.bloqueActual = bloqueActual;
    }
    public boolean claseDeclarada(String nombreClase){
        nombreClase = nombreClase.trim();
        return clases.containsKey(nombreClase);
    }
    public boolean clasePredefinidaDeclarada(String nombreClase){
        return clasesPredefinidas.containsKey(nombreClase);
    }
    public HashMap<String, Clase> getClases(){
        return clases;
    }
    public HashMap<String, Clase> getClasesPredefinidas(){ return clasesPredefinidas;}
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

        insertarMetodosPredefinidosObject(claseObject);
        insertarMetodosPredefinidosSystem(claseSystem);
        insertarMetodosPredefinidosString(claseString);

        claseSystem.insertarHerencia(claseObject.getNombre());
        claseString.insertarHerencia(claseObject.getNombre());
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

        m.insertarBloque(new BloqueDebugPrint(),new NodoBloqueVacio());
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

        m.insertarBloque(new BloqueRead(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintB(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintC(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintI(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintS(),new NodoBloqueVacio());
    }
    public void println(Clase claseSystem) throws ExcepcionSemantica {
        //static void println()
        Token modificador = new Token("static","static",-1);
        Token nombreMetodo = new Token("idMetVar","println",-1);
        Tipo tipoVoid = new TipoVoid();
        Metodo m = new Metodo(modificador,tipoVoid, nombreMetodo);
        claseSystem.insertarMetodo(nombreMetodo,m);
        m.insertarBloque(new BloquePrintln(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintBln(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintCln(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintIln(),new NodoBloqueVacio());
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
        m.insertarBloque(new BloquePrintSln(),new NodoBloqueVacio());
    }
    public void insertarMetodosPredefinidosString(Clase claseString){

    }
    public void consolidacion() throws ExcepcionSemantica {
       chequearCircularidad();
        for(Clase c :clasesPredefinidas.values()){
            if(c.getHerencia() != null){
                c.consolidarClase();
            }
        }
        for(Clase c : clases.values()){
            if(claseDeclarada(c.getNombre().getLexema()) || clasePredefinidaDeclarada( c.getNombre().getLexema() )){
                if(tablaSimbolos.obtenerClase(c.getHerencia().getLexema()) != null && tablaSimbolos.obtenerClase(c.getHerencia().getLexema()).esClaseFinal()){
                    throw new ExcepcionSemantica(tablaSimbolos.obtenerClase(c.getHerencia().getLexema()).getNombre().getLexema(),c.getNombre().getNroLinea(), "Padre final");
                }
                if(tablaSimbolos.obtenerClase(c.getHerencia().getLexema()) != null && tablaSimbolos.obtenerClase(c.getHerencia().getLexema()).esClaseEstatica()){
                    throw new ExcepcionSemantica(tablaSimbolos.obtenerClase(c.getHerencia().getLexema()).getNombre().getLexema(),c.getNombre().getNroLinea(), "Clase padre static");
                }
            }
            else{
                throw new ExcepcionSemantica(tablaSimbolos.obtenerClase(c.getHerencia().getLexema()).getNombre().getLexema(),c.getNombre().getNroLinea(), "Clase no declarada");
            }
            consolidarHerencia();
            c.consolidarClase();

        }
        //TablaSimbolos.getInstance().imprimirDetalleClases();
    }
    private void consolidarHerencia(){
        for(Clase c : clases.values()){
            if(c.getHerencia() == null){
                c.setHerenciaObject();
            }
        }
    }
    private void chequearCircularidad() throws ExcepcionSemantica {
        for(Clase c : clases.values()){
            HashSet<String> clasesVisitadas = new HashSet<>();
            chequearCircularidadClase(c,clasesVisitadas, new StringBuilder());
        }
    }
    private void chequearCircularidadClase(Clase clase, HashSet<String> clasesVisitadas, StringBuilder diagramaClases) throws ExcepcionSemantica {
        if(clase.getHerencia() == null) return;
        String nombreClase = clase.getNombre().getLexema();
        String nombrePadre = clase.getHerencia().getLexema();
        if (!clases.containsKey(nombrePadre)) return;
        if(clasesVisitadas.contains(nombreClase)){
            diagramaClases.append(nombreClase);
            throw new ExcepcionSemantica(nombreClase,clase.getNombre().getNroLinea(),"Herencia circular detectada: "+diagramaClases);
        }

        clasesVisitadas.add(nombreClase);
        diagramaClases.append(nombreClase).append(" -> ");

        Clase padre = clases.get(nombrePadre);
        if(padre != null){
            chequearCircularidadClase(padre, clasesVisitadas, diagramaClases);
        }
        clasesVisitadas.remove(nombreClase);
    }
    public Clase obtenerClaseDeclarada(String nombreClase){
        return clases.get(nombreClase);
    }
    public Clase obtenerClasePredefinida(String nombreClase){
        return clasesPredefinidas.get(nombreClase);
    }
    public Clase obtenerClase(String nombreClase){
        Clase clase = clases.get(nombreClase);
        if(clase == null){
            clase = clasesPredefinidas.get(nombreClase);
        }
        return clase;
    }
    public void imprimirClases() {
        System.out.println("=== Clases declaradas ===");
        for (String nombreClase : clases.keySet()) {
            System.out.println(" - " + nombreClase);
        }

        System.out.println("=========================\n");
    }

    public void imprimirDetalleClases() {
        System.out.println("=== Detalle de todas las clases ===");
        for (Clase c : clasesPredefinidas.values()) {
            c.imprimirResumen();
        }
        for (Clase c : clases.values()) {
            c.imprimirResumen();
        }
        System.out.println("==================================\n");
    }
    public void mostrarErroresSemanticos(){
        if(!erroresSemanticos.isEmpty()){
            for(ExcepcionSemantica e : erroresSemanticos){
                System.out.println("-"+e.getMessage());
            }
        }
    }
    public boolean existeMetodoMain() throws ExcepcionSemantica {
        int count = 0;
        for(Clase c : clases.values()){
            for (Metodo m : c.getMetodosPropios().values()){
                if(m.esMetodoEstatico() && m.getNombreMetodo().getLexema().equals("main")){
                    count++;

                }
            }
        }
       if(count != 1){
           String eof = Character.toString(SourceManager.END_OF_FILE);
           throw new ExcepcionSemantica(eof, SourceManager.END_OF_FILE," Metodo main no declarado");
       }
       return true;
    }
    public void generarCodigo(ArchivoSalida archivo) throws ExcepcionSemantica {
        generarCodigoLlamadaMain(archivo);
        generarCodigoHalt(archivo);
        generarPrimitivasMalloc_HeapInit(archivo);
        generarCodigoClasesPredefinidas(archivo);
        generarCodigoClases(archivo);

    }
    public void generarCodigoLlamadaMain(ArchivoSalida archivo) throws ExcepcionSemantica {
        archivo.generar(".CODE");
        Clase nombreClaseMain = obtenerClaseMain();
        archivo.generar(Instrucciones.PUSH+" lbl_main@"+nombreClaseMain.getNombre().getLexema());
        archivo.generar(""+Instrucciones.CALL);
        archivo.generar("");

    }
    public void generarCodigoHalt(ArchivoSalida archivo){
        archivo.generar(""+Instrucciones.HALT);
        archivo.generar("");
    }
    public void generarPrimitivasMalloc_HeapInit(ArchivoSalida archivo){

        archivo.generar("simple_heap_init: "+Instrucciones.RET+" 0");
        archivo.generar("");
        archivo.generar("simple_malloc: ");
        archivo.generar(""+Instrucciones.LOADFP);
        archivo.generar(""+Instrucciones.LOADSP);
        archivo.generar(""+Instrucciones.STOREFP);
        archivo.generar(""+Instrucciones.LOADHL);
        archivo.generar(""+Instrucciones.DUP);
        archivo.generar(Instrucciones.PUSH+" 1");
        archivo.generar(""+Instrucciones.ADD);
        archivo.generar(Instrucciones.STORE+" 4");
        archivo.generar(Instrucciones.LOAD+" 3");
        archivo.generar(""+Instrucciones.ADD);
        archivo.generar(""+Instrucciones.STOREHL);
        archivo.generar(""+Instrucciones.STOREFP);
        archivo.generar(Instrucciones.RET+" 1");
    }
    public void generarCodigoClasesPredefinidas(ArchivoSalida archivo) throws ExcepcionSemantica {
        for(Clase clasePredefinida : clasesPredefinidas.values()){
            clasePredefinida.generarCodigo(archivo);
        }
    }
    public void generarCodigoClases(ArchivoSalida archivo) throws ExcepcionSemantica {
        for(Clase clase: clases.values()){
            System.out.println("Generando codigo para la clase: "+clase.getNombre());
            clase.generarCodigo(archivo);
        }
    }
    public Clase obtenerClaseMain() throws ExcepcionSemantica {
        Clase claseMain = null;
        for(Clase c : clases.values()){
            for (Metodo m : c.getMetodosPropios().values()){
                if(m.esMetodoEstatico() && m.getNombreMetodo().getLexema().equals("main")){
                    claseMain = c;
                }
            }
        }
        if(claseMain == null){
            String eof = Character.toString(SourceManager.END_OF_FILE);
            throw new ExcepcionSemantica(eof, SourceManager.END_OF_FILE," Metodo main no declarado");
        }
        return claseMain;
    }
    public void calcularOffsets(){
        for(Clase c: clases.values()){
            for(Metodo m : c.getMetodosPropios().values()){
                m.calcularOffsets();
            }
        }
    }
}
