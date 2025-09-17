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
        if(primeros.estaEnPrimeros(NoTerminales.Clase, tokenActual.getId())){
            clase();
            listaClases();
        }
        else{ /* $ */}
    }
    private void clase() throws ExcepcionLexica, IOException, ExcepcionSintactica {
            modificadorOpcional();
            match("class");
            match("idClase");
            herenciaOpcional();
            match("{");
            listaMiembros();
            match("}");
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
        }
        else{ /* $ */ }
    }
    void listaMiembros() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Miembro,tokenActual.getId())){
            miembro();
            listaMiembros();
        }
        else{ /* $ */}
    }
    void miembro() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        if(primeros.estaEnPrimeros(NoTerminales.Tipo,tokenActual.getId())){
            tipo();
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
        else{
            throw new ExcepcionSintactica(tokenActual,tokenActual.getId());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
        }
    }
    void varLocal() throws ExcepcionLexica, IOException, ExcepcionSintactica {
        match("var");
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
        /*else if(tokenActual.getId().equals("+=")){
            match("+=");
        }
        else if(tokenActual.getId().equals("-=")){
            match("-=");
        }
         */
        else{
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
    /*void primario() throws ExcepcionSintactica, ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("this")){
            match("this");
        }
        else if(tokenActual.getId().equals("stringLiteral")){
            match("stringLiteral");
        }
        else if(primeros.estaEnPrimeros(NoTerminales.AccesoVar, tokenActual.getId())){
            accesoVar();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.LlamadaConstructor, tokenActual.getId())){
            llamadaConstructor();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.LlamadaMetodo,  tokenActual.getId())){
            llamadaMetodo();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.LlamadaMetodoEstatico, tokenActual.getId())){
            llamadaMetodoEstatico();
        }
        else if(primeros.estaEnPrimeros(NoTerminales.ExpresionParentizada, tokenActual.getId())){
            expresionParentizada();
        }
        else{
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
        }
    }
    */
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
            throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
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
        argsActuales();
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

            listaExpsResto();
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
        System.out.println(nombreToken+" "+ tokenActual.getId());
        if(nombreToken.equals(tokenActual.getId())){

            analizadorLexico.setLexema("");
            tokenActual = analizadorLexico.proximoToken();
            //System.out.println(tokenActual.getLexema());
        }

        else{
                /*if (tokenActual.getId().equals("EOF")) {
                    throw new ExcepcionSintactica(tokenActual, "EOF");
                }else{
                    throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
                }

                 */
           throw new ExcepcionSintactica(tokenActual, tokenActual.getLexema());
        }
    }
}
