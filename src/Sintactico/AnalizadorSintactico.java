package Sintactico;

import Lexico.AnalizadorLexico;
import Lexico.ExcepcionLexica;
import Utils.Token;

import java.io.IOException;

public class AnalizadorSintactico {
    private AnalizadorLexico analizadorLexico;
    private Token tokenActual;
    public AnalizadorSintactico(AnalizadorLexico analizadorLexico) throws ExcepcionLexica, IOException {
        this.analizadorLexico = analizadorLexico;
        this.tokenActual = analizadorLexico.proximoToken();
        inicial();
    }
    private void inicial() throws ExcepcionLexica, IOException {
        listaClases();
        match("EOF");
    }
    private void listaClases() throws ExcepcionLexica, IOException {
        //if token actual esta en primeros de clase ?
        if(!tokenActual.getId().equals("EOF")){
            clase();
            listaClases();
        }
        else{ /* $ */}
    }
    private void clase() throws ExcepcionLexica, IOException {
        modificadorOpcional();
        match("class");
        match("idClase");
        herenciaOpcional();
        match("{");
        listaMiembros();
        match("{");
    }
    void modificadorOpcional() throws ExcepcionLexica, IOException {
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
    private void herenciaOpcional() throws ExcepcionLexica, IOException {
        if(tokenActual.getId().equals("extends")){
            match("extends");
            match("idClase");
        }
        else{ /* $ */ }
    }
    void listaMiembros(){

    }
    void miembro(){

    }
    void miembroResto(){

    }
    private void metodo(){} //creo que se iba
    private void constructor() throws ExcepcionLexica, IOException {
        match("public");
        match("idClase");
        argsFormales();
        bloque();
    }
    void tipoMetodo(){

    }
    void tipoPrimitivo(){

    }
    void argsFormales(){

    }
    void listaArgsFormalesOpcional(){}
    void listaArgsFormalesResto(){}
    void argFormal(){}
    void bloqueOpcional(){}
    void bloque(){}
    void listaSentencias(){
    }
    void sentancia(){}
    void varLocal(){}
    void Return(){}
    void expresionOpcional(){}
    void If(){}
    void IfResto(){}
    void While(){}
    void expresion(){}
    void expresionResto(){}
    void operadorAsignacion(){}
    void expresionCompuesta(){}
    void expresionCompuestaResto(){}
    void operadorBinario(){}
    void expresionBasica(){}
    void operadorUnario(){}
    void operando(){}
    void primitivo(){}
    void referencia(){}
    void referenciaResto(){}
    void primario(){}
    void accesoVar(){}
    void llamadaConstructor(){}
    void expresionParentizada(){}
    void llamadaMetodo(){}
    void llamadaMetodoEstatico(){}
    void argsActuales(){}
    void listaExpsOpcional(){}
    void listaExps(){}
    void listaExpsResto(){}
    void encadenado(){}
    void restoEncadenado(){}

    void match(String nombreToken) throws ExcepcionLexica, IOException {
        if(nombreToken.equals(tokenActual.getLexema())){
            tokenActual = analizadorLexico.proximoToken();
        }
        else{
            //throw new ExcepcionSintactica(tokenActual, nombreToken);
        }
    }
}
