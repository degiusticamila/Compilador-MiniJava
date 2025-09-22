package Sintactico;

import Lexico.AnalizadorLexico;
import Lexico.ExcepcionLexica;
import Utils.Primeros;
import Utils.Token;

import java.io.IOException;

public class AnalizadorSintactico {
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;
    private Primeros primeros;
    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws ExcepcionLexica, IOException, ExcepcionSintactica {
        primeros = new Primeros();
        this.analizadorLexico = analizadorLexico;
        this.tokenActual = analizadorLexico.proximoToken();
        inicial();
    }
    public void inicial() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        listaClases();
        match("EOF");
    }
    private void listaClases() throws ExcepcionLexica, IOException, ExcepcionSintactica {

        if(primeros.estaEnPrimeros(NoTerminales.Clase_Interfaz, tokenActual.getId())){
            clase_interfaz();
            listaClases();
        }
        else{
            /* $ */
        }
    }
    private void clase_interfaz() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(primeros.estaEnPrimeros(NoTerminales.Clase, tokenActual.getId())){
            clase();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Interfaz, tokenActual.getId())){
            interfaz();
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"idClase o interface");
        }
    }
    private void clase() throws ExcepcionLexica, IOException, ExcepcionSintactica {
            modificadorOpcional();
            match("class");
            match("idClase");

            //OPCIONAL GENERICIDAD
            tipoParametricoOpcional();

            herenciaOpcional();
            match("{");
            listaMiembros();
            match("}");
    }
    private void interfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    private void listaMiembrosInterfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica {
       if(primeros.estaEnPrimeros(NoTerminales.MiembrosInterfaz, tokenActual.getId())){
           miembrosInterfaz();
           listaMiembrosInterfaz();
       }
       else{
           /* $ */
       }
    }
    private void miembrosInterfaz() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        //Para el logro de visibilidad agregar mod interfaz
        modificadorOpcional();
        tipoMetodo();
        tipoParametricoOpcional();
        match("idMetVar");
        argsFormales();
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
    void modificadorOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("abstract")){
            match("abstract");
        }
        else if(tokenActual.getId().equals("static")){
            match("static");
        }
        else if(tokenActual.getId().equals("final")){
            match("final");
        }
        else{ /* $ */ }
    }
    private void herenciaOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("extends")){
            match("extends");
            match("idClase");
            //GENERICIDAD E2
            tipoParametricoOpcional();
        }
        else if(tokenActual.getId().equals("implements")){
            match("implements");
            match("idClase");
            tipoParametricoOpcional();
        }
        else{ /* $ */ }
    }
    void listaMiembros() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Miembro,tokenActual.getId())){
            miembro();
            listaMiembros();
        }
        else{ /* $ */}
       /* if(primeros.estaEnPrimeros(NoTerminales.VisibilidadOpcional,tokenActual.getId())){

            visibilidadOpcional();
            miembro();
            listaMiembros();
        }
        else{/*$*///}
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
    void miembro() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Tipo,tokenActual.getId())){
            tipo();
            tipoParametricoOpcional();
            match("idMetVar");
            miembroResto();
        }
        else if(tokenActual.getId().equals("void")){
            match("void");
            match("idMetVar");
            argsFormales();
            bloqueOpcional();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.ModificadorOpcional,tokenActual.getId())){
            modificadorOpcional();
            tipoMetodo();

            //GENERICIDAD E2
            tipoParametricoOpcional();
            match("idMetVar");
            argsFormales();
            bloqueOpcional();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.Constructor, tokenActual.getId())){
            constructor();
        }
        else{ /* $  no hago nada pq modificadorOpcional tiene a e en sus primeros! */}
    }
    void miembroResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals(";")){
            match(";");
        }
        else if(primeros.estaEnPrimeros(NoTerminales.ArgsFormales, tokenActual.getId())){
            argsFormales();
            bloqueOpcional();
        }
        //ATRIBUTOS INICIALIZADOS
        else if (primeros.estaEnPrimeros(NoTerminales.OperadorAsignacion, tokenActual.getId())){
            operadorAsignacion();
            expresion();
            match(";");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"; | argFormal | =");
        }
    }
    private void constructor() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("public");
        match("idClase");
        argsFormales();
        bloque();
    }
    void tipoMetodo() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Tipo,tokenActual.getId())){
            tipo();
        }
        else if (tokenActual.getId().equals("void")){
            match("void");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"un tipo | void");
        }
    }
    void tipo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(primeros.estaEnPrimeros(NoTerminales.TipoPrimitivo, tokenActual.getId())){
            tipoPrimitivo();
        }
        else if(tokenActual.getId().equals("idClase")){
            match("idClase");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "TipoPrimitivo | idClase");
        }
    }
    void tipoPrimitivo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("boolean")){
            match("boolean");
        }
        else if(tokenActual.getId().equals("char")){
            match("char");
        }
        else if(tokenActual.getId().equals("int")){
            match("int");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"boolean | char | int");
        }
    }
    void argsFormales() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("(");
        listaArgsFormalesOpcional();
        match(")");
    }
    void listaArgsFormalesOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ListaArgsFormales, tokenActual.getId())){
            listaArgsFormales();
        }
        else{/* $ */}
    }
    void listaArgsFormales() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ArgFormal, tokenActual.getId())){
            argFormal();
            listaArgsFormalesResto();
        }
        else{ /* $ dado que listaArgsFormalesResto tiene e */}
    }

    void listaArgsFormalesResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals(",")){
            match(",");
            argFormal();
            listaArgsFormalesResto();
        }
        else{/* $ */}
    }
    void argFormal() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        tipo();
        match("idMetVar");
    }
    void bloqueOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Bloque, tokenActual.getId())){
            bloque();
        }
        else if(tokenActual.getId().equals(";")){
            match(";");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"Bloque | ;");
        }
    }
    void bloque() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("{");
        listaSentencias();
        match("}");
    }
    void listaSentencias() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Sentencia, tokenActual.getId())){
            sentencia();
            listaSentencias();
        }
        else{/* $ */}
    }
    void sentencia() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
            throw new ExcepcionSintactica(tokenActual, "; | Expresion | varLocal | return | if | while | bloque");
        }
    }
    void varLocal() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("var");

        //OPCIONAL GENERICIDAD
        tipoParametricoOpcional();

        match("idMetVar");
        match("=");
        expresionCompuesta();
    }
    void Return() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("return");
        expresionOpcional();
    }
    void expresionOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Expresion, tokenActual.getId())){
            expresion();
        }
        else{/* $ */}
    }
    void If() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("if");
        match("(");
        expresion();
        match(")");
        sentencia();
        IfResto();
    }
    void IfResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("else")){
            match("else");
            sentencia();
        }
        else{/* $ */}
    }
    void While() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("while");
        match("(");
        expresion();
        match(")");
        sentencia();
    }
    void expresion() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        expresionCompuesta();
        expresionResto();
    }
    void expresionResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.OperadorAsignacion, tokenActual.getId())){
            operadorAsignacion();
            expresionCompuesta();
        }
        else{/* $ */}
    }
    void operadorAsignacion() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("=")){
            match("=");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,"=");
        }
    }
    void expresionCompuesta() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        expresionBasica();
        expresionCompuestaResto();
    }
    void expresionCompuestaResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.OperadorBinario, tokenActual.getId())){
            operadorBinario();
            expresionBasica();
            expresionCompuestaResto();
        }
        else{/* $ */}
    }
    void operadorBinario() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    void expresionBasica() throws ExcepcionSintactica, ExcepcionLexica, IOException {
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
    void operadorUnario() throws ExcepcionSintactica, ExcepcionLexica, IOException {
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
    void operando() throws ExcepcionSintactica, ExcepcionLexica, IOException {
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
    void primitivo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
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
    void referencia() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        primario();
        referenciaResto();
    }
    void referenciaResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
            throw new ExcepcionSintactica(tokenActual, "this | stringLiteral | idMetVar | llamadaConstructor | LlamadaMetodoEstatico | expParentizada ");
        }
    }
    void llamadaMetodoResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ArgsActuales, tokenActual.getId())){
            argsActuales();
        }
        else{
            /* $ es el caso de accesoVar*/
        }
    }
    void accesoVar() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("idMetVar");
    }
    void llamadaConstructor() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("new");
        match("idClase");

        //OPCIONAL GENERICIDAD
        if(primeros.estaEnPrimeros(NoTerminales.TipoParametricoInst, tokenActual.getId())){
            tipoParametricoInst();
        }
        argsActuales();
    }
    void tipoParametricoOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("<")){
            match("<");
            match("idClase");
            match(">");
        }
        else{ /* $ */}
    }
    void tipoParametricoInst() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals("<")){
            match("<");
            tipoParametricoInstResto();
        }
        else{/*$*/}
    }
    void tipoParametricoInstResto() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals(">")){
            match(">");
        }
        else if(tokenActual.getId().equals("idClase")){
            match("idClase");
            match(">");
        }
        else{
            throw new ExcepcionSintactica(tokenActual,  "> | idClase");
        }
    }
    void expresionParentizada() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("(");
        expresion();
        match(")");
    }
    void llamadaMetodo() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("idMetVar");
        argsActuales();
    }
    void llamadaMetodoEstatico() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("idClase");
        match(".");
        match("idMetVar");
        argsActuales();
    }
    void argsActuales() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("(");
        listaExpsOpcional();
        match(")");
    }
    void listaExpsOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ListaExps, tokenActual.getId())){
            listaExps();
        }
        else{/* $ */}
    }
    void listaExps() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        expresion();
        listaExpsResto();
    }
    void listaExpsResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals(",")){
            match(",");
            listaExps();
        }
        else{/* $ */}
    }
    void encadenado() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match(".");
        match("idMetVar");
        restoEncadenado();
    }
    void restoEncadenado() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ArgsActuales, tokenActual.getId())){
            argsActuales();
        }
        else{/* $ */}
    }

    void match(String nombreToken) throws ExcepcionSintactica, IOException, ExcepcionLexica {
        //System.out.println(nombreToken+" "+ tokenActual.getId());
        if(nombreToken.equals(tokenActual.getId())){

            analizadorLexico.setLexema("");
            tokenActual = analizadorLexico.proximoToken();
            //System.out.println(tokenActual.getLexema());
        }
        else{
           throw new ExcepcionSintactica(tokenActual, nombreToken);
        }
    }
}
