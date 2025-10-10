package Sintactico;

import Lexico.AnalizadorLexico;
import Lexico.ExcepcionLexica;
import TablaDeSimbolos.*;
import Utils.Primeros;
import Utils.Token;

import java.io.IOException;

public class AnalizadorSintactico {
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;
    private Primeros primeros;
    private TablaSimbolos tablaSimbolos;
    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        primeros = new Primeros();
        //tablaSimbolos = new TablaSimbolos();
        TablaSimbolos.resetInstance();
        tablaSimbolos = TablaSimbolos.getInstance();
        this.analizadorLexico = analizadorLexico;
        this.tokenActual = analizadorLexico.proximoToken();
        inicial();
    }
    private void inicial() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        listaClases();
        match("EOF");

        //debug
       // tablaSimbolos.imprimirClases();
    }
    private void listaClases() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {

        if(primeros.estaEnPrimeros(NoTerminales.Clase_Interfaz, tokenActual.getId())){
            clase_interfaz();
            listaClases();
        }
        else{
            /* $ */
        }
    }
    private void clase_interfaz() throws ExcepcionSintactica, ExcepcionLexica, IOException, ExcepcionSemantica {
        if(primeros.estaEnPrimeros(NoTerminales.Clase, tokenActual.getId())){
            clase();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Interfaz, tokenActual.getId())){
            interfaz();
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"identificador de clase o interface");
        }
    }
    private void clase() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
            Token modificador = modificadorOpcional();
            match("class");
            Token clase = tokenActual;
            match("idClase");
            Clase c = new Clase(clase,modificador);
            tablaSimbolos.setClaseActual(c);
            tipoParametricoOpcional();

            tablaSimbolos.insertarClase(clase.getLexema(),clase.getNroLinea(),tablaSimbolos.getClaseActual());
            Token ancestro = herenciaOpcional();
            tablaSimbolos.getClaseActual().insertarHerencia(ancestro);

            match("{");
            listaMiembros();
            match("}");
    }
    private void interfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
            //modificadorOpcionalInterfaz();

            match("interface");
            match("idClase");
            tipoParametricoOpcional();
            herenciaOpcionalInterfaz();
            match("{");
            listaMiembrosInterfaz();
            match("}");

    }
    private void modificadorOpcionalInterfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("public")){
            match("public");
        }
        else {/* $ */}
    }
    private void listaMiembrosInterfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
       if(primeros.estaEnPrimeros(NoTerminales.MiembrosInterfaz, tokenActual.getId())){
           miembrosInterfaz();
           listaMiembrosInterfaz();
       }
       else{
           /* $ */
       }
    }
    private void miembrosInterfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        //Para el logro de visibilidad agregar mod interfaz
        modificadorOpcional();
        tipoMetodo();
        tipoParametricoOpcional();
        match("idMetVar");
        argsFormales(new Token(null,null,-1));
        match(";");
    }
    private void herenciaOpcionalInterfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("extends")){
            match("extends");
            match("idClase");
            tipoParametricoOpcional();
        }
        else{/* $*/}
    }
    private Token modificadorOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("abstract")){
            match("abstract");
            return new Token("abstract","abstract",0);
        }
        else if(tokenActual.getId().equals("static")){
            match("static");
            return new Token("static","static",0);
        }
        else if(tokenActual.getId().equals("final")){
            match("final");
            return new Token("final","final",0);
        }
        else{
            return null;
        }
    }
    private Token herenciaOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(tokenActual.getId().equals("extends")){
            match("extends");
            Token nombreHerencia =  tokenActual;
            match("idClase");
            return nombreHerencia;



            //GENERICIDAD E2
            //tipoParametricoOpcional();
        }
        /*else if(tokenActual.getId().equals("implements")){
            match("implements");
            match("idClase");
            tipoParametricoOpcional();
        }
         */
        else{
           return new Token("idClase","Object",0);
        }
    }
    private void listaMiembros() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(primeros.estaEnPrimeros(NoTerminales.Miembro,tokenActual.getId())){
            miembro();
            listaMiembros();
        }
        else{ /* $ */}
    }
    private void visibilidadOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("public")){
            match("public");
        }
        else if(tokenActual.getId().equals("private")){
            match("private");
        }
        else{/*$*/}
    }
    private void miembro() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(primeros.estaEnPrimeros(NoTerminales.Tipo,tokenActual.getId())){
            Token tokenTipo = tipo();
            tipoParametricoOpcional();
            Token nombreIdMetVar = tokenActual;
            match("idMetVar");
            miembroResto(nombreIdMetVar, tokenTipo);


        }
        else if(tokenActual.getId().equals("void")){
            match("void");
            Token tokenMetodo = tokenActual;
            //Token modificador = modificadorOpcional();

            Tipo tipoRetorno = new TipoVoid();

            Metodo m = new Metodo(null,tipoRetorno,tokenMetodo);
            tablaSimbolos.setMetodoActual(m);
            match("idMetVar");
            argsFormales(tokenMetodo);
            tablaSimbolos.getClaseActual().insertarMetodo(tokenMetodo,m);
            bloqueOpcional(m);
        }
        else if(primeros.estaEnPrimeros(NoTerminales.ModificadorOpcional,tokenActual.getId())){

            Token modificador = modificadorOpcional();
            Token tokenTipo = tipoMetodo();

            Tipo tipo = construirTipoDesdeToken(tokenTipo);

            Token tokenMetodo = tokenActual;

            Metodo m = new Metodo(modificador,tipo,tokenMetodo);
            tablaSimbolos.setMetodoActual(m);
            //GENERICIDAD E2
            tipoParametricoOpcional();

            match("idMetVar");
            argsFormales(tokenMetodo);
            tablaSimbolos.getClaseActual().insertarMetodo(tokenMetodo,m);
            bloqueOpcional(m);
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Constructor, tokenActual.getId())){
            constructor();
        }
        else{ /* $  no hago nada pq modificadorOpcional tiene a e en sus primeros! */}
    }
    private void miembroResto(Token nombreIdMetVar, Token tokenTipo) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(tokenActual.getId().equals(";")){
            Tipo tipo = construirTipoDesdeToken(tokenTipo);
            Atributo a = new Atributo(tipo, nombreIdMetVar);
            match(";");
            tablaSimbolos.getClaseActual().insertarAtributo(nombreIdMetVar,a);
        }
        else if(primeros.estaEnPrimeros(NoTerminales.ArgsFormales, tokenActual.getId())){

            Token modificador = modificadorOpcional();
            Token tokenMetodo = tokenActual;
            Tipo tipo = construirTipoDesdeToken(tokenTipo);
            Metodo m = new Metodo(modificador,tipo,nombreIdMetVar);
            tablaSimbolos.setMetodoActual(m);
            argsFormales(tokenMetodo);
            tablaSimbolos.getClaseActual().insertarMetodo(nombreIdMetVar,m);
            bloqueOpcional(m);
        }
        //ATRIBUTOS INICIALIZADOS
        else if (primeros.estaEnPrimeros(NoTerminales.OperadorAsignacion, tokenActual.getId())){
            operadorAsignacion();
            expresion();
            match(";");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"; | Argumento formal | =");
        }
    }
    private void constructor() throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        match("public");
        Token tokenConstructor = tokenActual;
        Constructor c = new Constructor(tokenConstructor);
        match("idClase");
        tablaSimbolos.getClaseActual().insertarConstructor(tokenConstructor,c);
        argsFormales(tokenConstructor);
        bloque();
    }
    private Token tipoMetodo() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Tipo,tokenActual.getId())){
            return tipo();
        }
        else if (tokenActual.getId().equals("void")){
            Token tokenTipoVoid = tokenActual;
            match("void");
            return tokenTipoVoid;
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"un tipo | void");
        }
    }
    private Token tipo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(primeros.estaEnPrimeros(NoTerminales.TipoPrimitivo, tokenActual.getId())){
            Token tipoPrimitivo = tipoPrimitivo();
            return tipoPrimitivo; //new
        }
        else if(tokenActual.getId().equals("idClase")){
            Token tipoClase = tokenActual; //new
            match("idClase");
            return tipoClase; //new
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "TipoPrimitivo | identificador de clase");
        }
    }
    private Token tipoPrimitivo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("boolean")){
            match("boolean");
            return new Token(tokenActual.getId(),"boolean",0); // new
        }

        else if(tokenActual.getId().equals("char")){
            match("char");
            return new Token(tokenActual.getId(),"char",0); //new
        }
        else if(tokenActual.getId().equals("int")){
            match("int");
            return new Token(tokenActual.getId(),"int",0); //new
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"boolean | char | int");
        }
    }
    private void argsFormales(Token construtorOmetodo) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        match("(");
        listaArgsFormalesOpcional(construtorOmetodo);
        match(")");
    }
    private void listaArgsFormalesOpcional(Token construtorOmetodo) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(primeros.estaEnPrimeros(NoTerminales.ListaArgsFormales, tokenActual.getId())){
            listaArgsFormales(construtorOmetodo);
        }
        else{/* $ */}
    }
    private void listaArgsFormales(Token construtorOmetodo) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(primeros.estaEnPrimeros(NoTerminales.ArgFormal, tokenActual.getId())){
            argFormal(construtorOmetodo);
            listaArgsFormalesResto(construtorOmetodo);
        }
        else{ /* $ dado que listaArgsFormalesResto tiene e */}
    }

    private void listaArgsFormalesResto(Token construtorOmetodo) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(tokenActual.getId().equals(",")){
            match(",");
            argFormal(construtorOmetodo);
            listaArgsFormalesResto(construtorOmetodo);
        }
        else{/* $ */}
    }
    private void argFormal(Token construtorOmetodo) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        Token tokenTipoParametro = tipo();
        Tipo tipoParametro = construirTipoDesdeToken(tokenTipoParametro);
        Token nombreParametro = tokenActual;
        Parametro p = new Parametro(tipoParametro,nombreParametro,1 );

        if(construtorOmetodo.getId().equals("idClase")){
            //es un constructor
            tablaSimbolos.getClaseActual().getConstructor().insertarParametro(nombreParametro.getLexema(), p, nombreParametro.getNroLinea());
            tablaSimbolos.getClaseActual().getConstructor().getParametros(); //
        }
        else{ //es un metodo
            tablaSimbolos.getMetodoActual().insertarParametro(nombreParametro.getLexema(), p, nombreParametro.getNroLinea());
            tablaSimbolos.getMetodoActual().getParametros(); //
        }
        match("idMetVar");
    }
    private void bloqueOpcional(Metodo m) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(primeros.estaEnPrimeros(NoTerminales.Bloque, tokenActual.getId())){
            if(m.getModificador() != null && m.getModificador().getLexema().equals("abstract")){
                throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(),m.getNombreMetodo().getNroLinea(), "Método abstracto con cuerpo");
            }
            bloque();
        }
        else if(tokenActual.getId().equals(";")){
            match(";");
            if((m.getModificador() == null) || !m.getModificador().getLexema().equals("abstract")){
                throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(), m.getNombreMetodo().getNroLinea(),"Metodo sin cuerpo");
            }
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"Bloque | ;");
        }
    }
    private void bloque() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("{");
        listaSentencias();
        match("}");
    }
    private void listaSentencias() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Sentencia, tokenActual.getId())){
            sentencia();
            listaSentencias();
        }
        else{/* $ */}
    }
    private void sentencia() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals(";")){
            match(";");
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Expresion, tokenActual.getId())){
            expresion();
            match(";");
        }
        else if(primeros.estaEnPrimeros(NoTerminales.VarLocal, tokenActual.getId())){
            varLocal();
            match(";");
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Return, tokenActual.getId())){
            Return();
            match(";");
        }
        else if(primeros.estaEnPrimeros(NoTerminales.If, tokenActual.getId())){
            If();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.While, tokenActual.getId())){
            While();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Bloque, tokenActual.getId())){
            bloque();
        }
        else{
            throw new ExcepcionSintactica(tokenActual, "; | Expresion | Variable local| return | if | while | bloque");
        }
    }
    private void varLocal() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("var");

        //OPCIONAL GENERICIDAD
        tipoParametricoOpcional();

        match("idMetVar");
        match("=");
        expresionCompuesta();
    }
    private void Return() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("return");
        expresionOpcional();
    }
    private void expresionOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Expresion, tokenActual.getId())){
            expresion();
        }
        else{/* $ */}
    }
    private void If() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("if");
        match("(");
        expresion();
        match(")");
        sentencia();
        IfResto();
    }
    private void IfResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("else")){
            match("else");
            sentencia();
        }
        else{/* $ */}
    }
    private void While() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("while");
        match("(");
        expresion();
        match(")");
        sentencia();
    }
    private void expresion() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        expresionCompuesta();
        expresionResto();
    }
    private void expresionResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.OperadorAsignacion, tokenActual.getId())){
            operadorAsignacion();
            expresionCompuesta();
        }
        else{/* $ */}
    }
    private void operadorAsignacion() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("=")){
            match("=");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"=");
        }
    }
    private void expresionCompuesta() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        expresionBasica();
        expresionCompuestaResto();
    }
    private void expresionCompuestaResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.OperadorBinario, tokenActual.getId())){
            operadorBinario();
            expresionBasica();
            expresionCompuestaResto();
        }
        else{/* $ */}
    }
    private void operadorBinario() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("||")){
            match("||");
        }
        else if(tokenActual.getId().equals("&&")){
            match("&&");
        }
        else if(tokenActual.getId().equals("==")){
            match("==");
        }
        else if(tokenActual.getId().equals("!=")){
            match("!=");
        }
        else if(tokenActual.getId().equals("<")){
            match("<");
        }
        else if(tokenActual.getId().equals(">")){
            match(">");
        }
        else if(tokenActual.getId().equals("<=")){
            match("<=");
        }
        else if(tokenActual.getId().equals(">=")){
            match(">=");
        }
        else if(tokenActual.getId().equals("+")){
            match("+");
        }
        else if(tokenActual.getId().equals("-")){
            match("-");
        }
        else if(tokenActual.getId().equals("*")){
            match("*");
        }
        else if(tokenActual.getId().equals("/")){
            match("/");
        }
        else if(tokenActual.getId().equals("%")){
            match("%");
        }
        else{
            throw new ExcepcionSintactica(tokenActual, "Operador Binario");
        }
    }
    private void expresionBasica() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(primeros.estaEnPrimeros(NoTerminales.OperadorUnario, tokenActual.getId())){
            operadorUnario();
            operando();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Operando, tokenActual.getId())){
            operando();
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "operador unario | operando");
        }
    }
    private void operadorUnario() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("+")){
            match("+");
        }
        else if(tokenActual.getId().equals("++")){
            match("++");
        }
        else if(tokenActual.getId().equals("-")){
            match("-");
        }
        else if(tokenActual.getId().equals("--")){
            match("--");
        }
        else if(tokenActual.getId().equals("!")){
            match("!");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "operador unario");
        }
    }
    private void operando() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(primeros.estaEnPrimeros(NoTerminales.Primitivo, tokenActual.getId())){
            primitivo();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Referencia, tokenActual.getId())){
            referencia();
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"Operando");
        }
    }
    private void primitivo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("true")){
            match("true");
        }
        else if(tokenActual.getId().equals("false")){
            match("false");
        }
        else if(tokenActual.getId().equals("intLiteral")){
            match("intLiteral");
        }
        else if(tokenActual.getId().equals("charLiteral")){
            match("charLiteral");
        }
        else if(tokenActual.getId().equals("null")){
            match("null");
        }
        else {
            throw new ExcepcionSintactica(tokenActual,"primitivo");
        }
    }
    private void referencia() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        primario();
        referenciaResto();
    }
    private void referenciaResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Encadenado, tokenActual.getId())){
            encadenado();
            referenciaResto();
        }
        else{/* $ */}
    }
    private void primario() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("this")){
            match("this");
        }
        else if(tokenActual.getId().equals("stringLiteral")){
            match("stringLiteral");
        }
        else if(tokenActual.getId().equals("idMetVar")){
            match("idMetVar");
            llamadaMetodoResto();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.LlamadaConstructor, tokenActual.getId())){
            llamadaConstructor();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.LlamadaMetodoEstatico, tokenActual.getId())){
            llamadaMetodoEstatico();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.ExpresionParentizada, tokenActual.getId())){
            expresionParentizada();
        }
        else{
            throw new ExcepcionSintactica(tokenActual, "identificador metodo variable | constructor | llamada metodo estatico | expresion parentizada");
        }
    }
    private void llamadaMetodoResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ArgsActuales, tokenActual.getId())){
            argsActuales();
        }
        else{
            /* $ es el caso de accesoVar*/
        }
    }
    private void llamadaConstructor() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("new");
        match("idClase");

        //OPCIONAL GENERICIDAD
        if(primeros.estaEnPrimeros(NoTerminales.TipoParametricoInst, tokenActual.getId())){
            tipoParametricoInst();
        }
        argsActuales();
    }
    private void tipoParametricoOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("<")){
            match("<");
            match("idClase");
            match(">");
        }
        else{ /* $ */}
    }
    private void tipoParametricoInst() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("<")){
            match("<");
            tipoParametricoInstResto();
        }
        else{/*$*/}
    }
    private void tipoParametricoInstResto() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals(">")){
            match(">");
        }
        else if(tokenActual.getId().equals("idClase")){
            match("idClase");
            match(">");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "> | identificador de clase");
        }
    }
    private void expresionParentizada() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("(");
        expresion();
        match(")");
    }
    private void llamadaMetodoEstatico() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("idClase");
        match(".");
        match("idMetVar");
        argsActuales();
    }
    private void argsActuales() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("(");
        listaExpsOpcional();
        match(")");
    }
    private void listaExpsOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ListaExps, tokenActual.getId())){
            listaExps();
        }
        else{/* $ */}
    }
    private void listaExps() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        expresion();
        listaExpsResto();
    }
    private void listaExpsResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals(",")){
            match(",");
            listaExps();
        }
        else{/* $ */}
    }
    private void encadenado() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match(".");
        match("idMetVar");
        restoEncadenado();
    }
    private void restoEncadenado() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ArgsActuales, tokenActual.getId())){
            argsActuales();
        }
        else{/* $ */}
    }
    private void match(String nombreToken) throws ExcepcionSintactica, IOException, ExcepcionLexica {
        if(nombreToken.equals(tokenActual.getId())){
            analizadorLexico.setLexema("");
            tokenActual = analizadorLexico.proximoToken();
        }
        else{
           throw new ExcepcionSintactica(tokenActual, nombreToken);
        }
    }
    public void consolidarTS() throws ExcepcionSemantica {
        tablaSimbolos.consolidacion();
        tablaSimbolos.mostrarErroresSemanticos();
    }
    private Tipo construirTipoDesdeToken(Token tokenTipo) {
        String lexema = tokenTipo.getLexema();

        if (lexema.equals("int") || lexema.equals("boolean") || lexema.equals("char")) {
            return new TipoPrimitivo(lexema);
        } else {
            return new TipoReferencia(lexema);
        }
    }
}
