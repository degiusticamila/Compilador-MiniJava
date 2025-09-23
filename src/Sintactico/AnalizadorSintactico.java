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
    private void inicial() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    private void modificadorOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    private void listaMiembros() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    private void miembro() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    private void miembroResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    private void tipoMetodo() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
    private void tipo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
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
    private void tipoPrimitivo() throws ExcepcionSintactica, ExcepcionLexica, IOException {
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
    private void argsFormales() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("(");
        listaArgsFormalesOpcional();
        match(")");
    }
    private void listaArgsFormalesOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ListaArgsFormales, tokenActual.getId())){
            listaArgsFormales();
        }
        else{/* $ */}
    }
    private void listaArgsFormales() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.ArgFormal, tokenActual.getId())){
            argFormal();
            listaArgsFormalesResto();
        }
        else{ /* $ dado que listaArgsFormalesResto tiene e */}
    }

    private void listaArgsFormalesResto() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(tokenActual.getId().equals(",")){
            match(",");
            argFormal();
            listaArgsFormalesResto();
        }
        else{/* $ */}
    }
    private void argFormal() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        tipo();
        match("idMetVar");
    }
    private void bloqueOpcional() throws ExcepcionLexica, IOException, ExcepcionSintactica {
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
            throw new ExcepcionSintactica(tokenActual, "; | Expresion | varLocal | return | if | while | bloque");
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
            throw new ExcepcionSintactica(tokenActual, "this | stringLiteral | idMetVar | llamadaConstructor | LlamadaMetodoEstatico | expParentizada ");
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
            throw new ExcepcionSintactica(tokenActual,  "> | idClase");
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
}
