package Sintactico;
import AST.NodoOperadorBinario;
import AST.NodosOperando.*;
import AST.NodosExpresion.*;
import AST.NodosSentencia.*;
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
        else{/* $ */}
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
            NodoBloque bloque = bloqueOpcional(m);
            tablaSimbolos.getMetodoActual().insertarBloque(bloque);
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
            NodoBloque bloque = bloqueOpcional(m);
            tablaSimbolos.getMetodoActual().insertarBloque(bloque);
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Constructor, tokenActual.getId())){
            constructor();
        }
        else{ /* $ */}
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
            NodoBloque bloque = bloqueOpcional(m);
            tablaSimbolos.getMetodoActual().insertarBloque(bloque);
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
        NodoBloque bloque = bloque();
        tablaSimbolos.getClaseActual().getConstructor().insertarBloque(bloque);
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
            return tipoPrimitivo;
        }
        else if(tokenActual.getId().equals("idClase")){
            Token tipoClase = tokenActual;
            match("idClase");
            return tipoClase;
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "TipoPrimitivo | identificador de clase");
        }
    }
    private Token tipoPrimitivo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("boolean")){
            match("boolean");
            return new Token(tokenActual.getId(),"boolean",0);
        }
        else if(tokenActual.getId().equals("char")){
            match("char");
            return new Token(tokenActual.getId(),"char",0);
        }
        else if(tokenActual.getId().equals("int")){
            match("int");
            return new Token(tokenActual.getId(),"int",0);
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
        else{ /* $ */}
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
        }
        else{ //es un metodo
            tablaSimbolos.getMetodoActual().insertarParametro(nombreParametro.getLexema(), p, nombreParametro.getNroLinea());
        }
        match("idMetVar");
    }
    private NodoBloque bloqueOpcional(Metodo m) throws ExcepcionLexica, IOException, ExcepcionSintactica, ExcepcionSemantica {
        if(primeros.estaEnPrimeros(NoTerminales.Bloque, tokenActual.getId())){
            if(m.getModificador() != null && m.getModificador().getLexema().equals("abstract")){
                throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(),m.getNombreMetodo().getNroLinea(), "Método abstracto con cuerpo");
            }
            NodoBloque bloque = bloque();
            return bloque;
        }
        else if(tokenActual.getId().equals(";")){
            match(";");
            if((m.getModificador() == null) || !m.getModificador().getLexema().equals("abstract")){
                throw new ExcepcionSemantica(m.getNombreMetodo().getLexema(), m.getNombreMetodo().getNroLinea(),"Metodo sin cuerpo");
            }
            return new NodoBloqueVacio();
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"Bloque | ;");
        }
    }
    private NodoBloque bloque() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("{");
        NodoBloque bloque = new NodoBloque();
        listaSentencias(bloque);
        match("}");
        return bloque;
    }
    private NodoSentencia listaSentencias(NodoBloque bloque) throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Sentencia, tokenActual.getId())){
            NodoSentencia sentencia = sentencia();
            bloque.getSentencias().add(sentencia);
            listaSentencias(bloque);
            return sentencia;
        }
        else{/* $ */}
        return new NodoSentenciaVacia();
    }
    private NodoSentencia sentencia() throws ExcepcionLexica, IOException, ExcepcionSintactica {

        if(tokenActual.getId().equals(";")){
            match(";");
            return new NodoSentenciaVacia();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Expresion, tokenActual.getId())){
            NodoExpresion nodoExpresion = expresion();
            NodoSentenciaExpresion nodoSentenciaExpresion = new NodoSentenciaExpresion(nodoExpresion);
            match(";");
            return nodoSentenciaExpresion;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.VarLocal, tokenActual.getId())){
            NodoSentencia nodoVarLocal = varLocal();
            match(";");
            return nodoVarLocal;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Return, tokenActual.getId())){
            NodoSentencia nodoSentenciaReturn = Return();
            match(";");
            return nodoSentenciaReturn;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.If, tokenActual.getId())){
            NodoSentencia nodoSentenciaIf = If();
            return nodoSentenciaIf;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.While, tokenActual.getId())){
            NodoSentencia nodoSentenciaWhile = While();
            return nodoSentenciaWhile;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Bloque, tokenActual.getId())){
            NodoBloque bloque = bloque();
            return bloque;
        }
        else{
            throw new ExcepcionSintactica(tokenActual, "; | Expresion | Variable local| return | if | while | bloque");
        }
    }
    private NodoSentencia varLocal() throws ExcepcionLexica, IOException, ExcepcionSintactica {

        match("var");
        tipoParametricoOpcional();
        NodoVarLocal nodoVar = new NodoVarLocal(tokenActual);
        match("idMetVar");


        nodoVar.setOperador(tokenActual);
        match("=");

        NodoExpresion ladoDerecho = expresionCompuesta();
        nodoVar.setLadoDerecho(ladoDerecho);

       // NodoExpresion nodoExpresionAsignacion = new NodoExpAsignacion(tokenActual,nodoVar,ladoDerecho);
      //  NodoAsignacion nodoAsignacion = new NodoAsignacion(nodoExpresionAsignacion);
        return nodoVar;
    }
    private NodoSentencia Return() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        NodoReturn nodoReturn = new NodoReturn(tokenActual);
        match("return");
        NodoExpresion expresionReturn = expresionOpcional();
        nodoReturn.setExpresionOpcional(expresionReturn);
        return nodoReturn;
    }
    private NodoExpresion expresionOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Expresion, tokenActual.getId())){
            NodoExpresion nodoExpresion = expresion();
            return nodoExpresion;
        }
        else{
            return new NodoExpresionVacia();
        }
    }
    private NodoSentencia If() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("if");
        match("(");
        NodoExpresion nodoExpresionIf = expresion();
        match(")");
        NodoSentencia nodoSentenciaIf = sentencia();
        NodoSentencia nodoSentenciaElse = IfResto();
        NodoSentencia nodoIf = new NodoIf(nodoExpresionIf, nodoSentenciaIf, nodoSentenciaElse);
        return nodoIf;
    }
    private NodoSentencia IfResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("else")){
            match("else");
            NodoSentencia nodoSentenciaElse = sentencia();
            return nodoSentenciaElse;
        }
        else{
            return new NodoSentenciaVacia();
        }
    }
    private NodoSentencia While() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("while");
        match("(");
        NodoExpresion expresionWhile = expresion();
        match(")");
        NodoSentencia sentenciaWhile = sentencia();
        NodoSentencia nodoWhile = new NodoWhile(expresionWhile, sentenciaWhile);
        return nodoWhile;
    }
    private NodoExpresion expresion() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        NodoExpresion nodoExpresionCompuesta = expresionCompuesta();
        nodoExpresionCompuesta = expresionResto(nodoExpresionCompuesta);
        return nodoExpresionCompuesta;
    }
    private NodoExpresion expresionResto(NodoExpresion ladoIzquierdo) throws ExcepcionLexica, IOException, ExcepcionSintactica {
        NodoExpresion expresion = ladoIzquierdo;
        if(primeros.estaEnPrimeros(NoTerminales.OperadorAsignacion, tokenActual.getId())){
            Token operador = operadorAsignacion();
            NodoExpresion ladoDerecho = expresionCompuesta();
            expresion = new NodoExpAsignacion(operador,ladoIzquierdo,ladoDerecho);
            return expresion;
        }
        else{/* $ */}
        return expresion;
    }
    private Token operadorAsignacion() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("=")){
            Token operador = tokenActual;
            match("=");
            return operador;
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"=");
        }
    }
    private NodoExpresion expresionCompuesta() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        NodoExpresion nodoExpresion = expresionBasica();
        nodoExpresion = expresionCompuestaResto(nodoExpresion);
        return nodoExpresion;
    }
    private NodoExpresion expresionCompuestaResto(NodoExpresion ladoIzquierdo) throws ExcepcionLexica, IOException, ExcepcionSintactica {
        NodoExpresion expresion = ladoIzquierdo;
        if(primeros.estaEnPrimeros(NoTerminales.OperadorBinario, tokenActual.getId())){
            Token operadorBinario = operadorBinario();
            NodoExpresion ladoDerecho = expresionBasica();
            ladoDerecho = expresionCompuestaResto(ladoDerecho);
            expresion = new NodoExpresionBinaria(operadorBinario,ladoIzquierdo,ladoDerecho);
            return expresion;
        }
        else{
            return expresion;
        }
    }
    private Token operadorBinario() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("||")){
            Token operadorOr = tokenActual;
            match("||");
            return operadorOr;
        }
        else if(tokenActual.getId().equals("&&")){
            Token operadorAnd = tokenActual;
            match("&&");
            return operadorAnd;
        }
        else if(tokenActual.getId().equals("==")){
            Token operador = tokenActual;
            match("==");
            return operador;
        }
        else if(tokenActual.getId().equals("!=")){
            Token operadorDesigual  = tokenActual;
            match("!=");
            return tokenActual;
        }
        else if(tokenActual.getId().equals("<")){
            Token operadorMenor = tokenActual;
            match("<");
            return operadorMenor;
        }
        else if(tokenActual.getId().equals(">")){
            Token operadorMayor = tokenActual;
            match(">");
            return operadorMayor;
        }
        else if(tokenActual.getId().equals("<=")){
            Token menorIgual = tokenActual;
            match("<=");
            return menorIgual;
        }
        else if(tokenActual.getId().equals(">=")){
            Token mayorIgual = tokenActual;
            match(">=");
            return mayorIgual;
        }
        else if(tokenActual.getId().equals("+")){
            Token operadorMas = tokenActual;
            match("+");
            return operadorMas;
        }
        else if(tokenActual.getId().equals("-")){
            Token operadorMenos = tokenActual;
            match("-");
            return operadorMenos;
        }
        else if(tokenActual.getId().equals("*")){
            Token operadorMult = tokenActual;
            match("*");
            return operadorMult;
        }
        else if(tokenActual.getId().equals("/")){
            Token operadorDiv = tokenActual;
            match("/");
            return operadorDiv;
        }
        else if(tokenActual.getId().equals("%")){
            Token operadorPorc = tokenActual;
            match("%");
            return operadorPorc;
        }
        else{
            throw new ExcepcionSintactica(tokenActual, "Operador Binario");
        }
    }
    private NodoExpresion expresionBasica() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(primeros.estaEnPrimeros(NoTerminales.OperadorUnario, tokenActual.getId())){
            NodoOperadorUnario operadorUnario = operadorUnario();
            NodoOperando operando = operando();
            operadorUnario.setLadoDerecho(operando);
            //NodoExpresionBasica nodoExpresionBasica = new NodoExpresionBasica(operadorUnario,operando);
            return operadorUnario;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Operando, tokenActual.getId())){
            NodoOperando nodoOperando = operando();
            return nodoOperando;
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "operador unario | operando");
        }
    }
    private NodoOperadorUnario operadorUnario() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("+")){
            NodoOperadorUnario operadorMas = new NodoOperadorUnario(tokenActual,new NodoExpresionVacia());
            match("+");
            return operadorMas;
        }
        else if(tokenActual.getId().equals("++")){
            NodoOperadorUnario operadorMasMas = new NodoOperadorUnario(tokenActual,new NodoExpresionVacia());
            match("++");
            return operadorMasMas;
        }
        else if(tokenActual.getId().equals("-")){
            NodoOperadorUnario operadorMenos = new NodoOperadorUnario(tokenActual,new NodoExpresionVacia());
            match("-");
            return operadorMenos;
        }
        else if(tokenActual.getId().equals("--")){
            NodoOperadorUnario operadorMenosMenos = new NodoOperadorUnario(tokenActual,new NodoExpresionVacia());
            match("--");
            return operadorMenosMenos;
        }
        else if(tokenActual.getId().equals("!")){
            NodoOperadorUnario operadorNegacion = new NodoOperadorUnario(tokenActual,new NodoExpresionVacia());
            match("!");
            return operadorNegacion;
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "operador unario");
        }
    }
    private NodoOperando operando() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(primeros.estaEnPrimeros(NoTerminales.Primitivo, tokenActual.getId())){
            NodoOperando nodoOperando = primitivo();
            return nodoOperando;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Referencia, tokenActual.getId())){
            NodoOperando nodoOperando = referencia();
            return nodoOperando;
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"Operando");
        }
    }
    private NodoOperando primitivo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("true")){
            NodoBoolean nodoBooleanTrue = new NodoBoolean(tokenActual);
            match("true");
            return nodoBooleanTrue;
        }
        else if(tokenActual.getId().equals("false")){
            NodoBoolean nodoBooleanFalse = new NodoBoolean(tokenActual);
            match("false");
            return nodoBooleanFalse;
        }
        else if(tokenActual.getId().equals("intLiteral")){
            NodoIntLiteral nodoIntLiteral = new NodoIntLiteral(tokenActual);
            match("intLiteral");
            return nodoIntLiteral;
        }
        else if(tokenActual.getId().equals("charLiteral")){
            NodoCharLiteral nodoCharLiteral = new NodoCharLiteral(tokenActual);
            match("charLiteral");
            return nodoCharLiteral;
        }
        else if(tokenActual.getId().equals("null")){
            NodoNull nodoNull = new NodoNull(tokenActual);
            match("null");
            return nodoNull;
        }
        else {
            throw new ExcepcionSintactica(tokenActual,"primitivo");
        }
    }
    private NodoOperando referencia() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        NodoOperando nodoOperando = primario();
        referenciaResto();
        return nodoOperando;
    }
    private void referenciaResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Encadenado, tokenActual.getId())){
            encadenado();
            referenciaResto();
        }
        else{/* $ */}
    }
    private NodoOperando primario() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        NodoOperando nodoOperando;
        if(tokenActual.getId().equals("this")){
            match("this");
            return new NodoOperandoVacio();
        }
        else if(tokenActual.getId().equals("stringLiteral")){
            match("stringLiteral");
            return new NodoOperandoVacio();
        }
        else if(tokenActual.getId().equals("idMetVar")){
            nodoOperando = new NodoAccesoVar(tokenActual);
            match("idMetVar");
            llamadaMetodoResto();
            return nodoOperando;
        }
        else if(primeros.estaEnPrimeros(NoTerminales.LlamadaConstructor, tokenActual.getId())){
            llamadaConstructor();
            return new NodoOperandoVacio();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.LlamadaMetodoEstatico, tokenActual.getId())){
            llamadaMetodoEstatico();
            return new NodoOperandoVacio();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.ExpresionParentizada, tokenActual.getId())){
            expresionParentizada();
            return new NodoOperandoVacio();
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
