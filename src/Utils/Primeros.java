package Utils;

import Sintactico.NoTerminales;
import Sintactico.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Primeros extends HashMap<NoTerminales, ArrayList<String>>{
    HashMap<NoTerminales, ArrayList<String>> primeros;

    public Primeros(){
        primeros = new HashMap<>();
        inicializarMapeo();
        inicializarPrimeros();
    }
    //por cada produccion crea el mapeo
    public void inicializarMapeo(){
        for(NoTerminales noTerminales : NoTerminales.values()){
            primeros.put(noTerminales, new ArrayList<>());
        }
    }
    public void inicializarPrimeros(){
        terminales();
        noterminales();
    }

    private void inicializarEntrada(NoTerminales key, String... tokens){
        primeros.get(key).addAll(Arrays.asList(tokens));

    }
    private void terminales(){

        inicializarEntrada(NoTerminales.Inicial,"EOF");
        inicializarEntrada(NoTerminales.ModificadorOpcional, "abstract","static", "final");
        inicializarEntrada(NoTerminales.HerenciaOpcional, "extends");

        inicializarEntrada(NoTerminales.Miembro, "void");

        inicializarEntrada(NoTerminales.MiembroResto, ";");

        inicializarEntrada(NoTerminales.Constructor, "public");

        inicializarEntrada(NoTerminales.TipoMetodo, "void");

        inicializarEntrada(NoTerminales.Tipo, "idClase");

        inicializarEntrada(NoTerminales.TipoPrimitivo, "boolean", "int", "char");

        inicializarEntrada(NoTerminales.ArgsFormales, "(");

        inicializarEntrada(NoTerminales.ListaArgsFormalesResto, ",");
        inicializarEntrada(NoTerminales.Bloque, "{");

        inicializarEntrada(NoTerminales.Sentencia, ";");

        inicializarEntrada(NoTerminales.VarLocal, "var");

        inicializarEntrada(NoTerminales.Return, "return");

        inicializarEntrada(NoTerminales.If, "if");

        inicializarEntrada(NoTerminales.If, "else");

        inicializarEntrada(NoTerminales.While, "while");

        inicializarEntrada(NoTerminales.OperadorAsignacion, "=", "+=", "-=");
        inicializarEntrada(NoTerminales.OperadorBinario, "||", "&&", "==", "!=", ">", "<", ">=", "<=", "+", "-","*", "/", "%");
        inicializarEntrada(NoTerminales.OperadorUnario, "+", "++", "-", "--", "!");

        inicializarEntrada(NoTerminales.Primitivo, "true", "false", "intLiteral", "charLiteral", "null");

        inicializarEntrada(NoTerminales.Primario, "this", "stringLiteral");

        inicializarEntrada(NoTerminales.AccesoVar, "idMetVar");

        inicializarEntrada(NoTerminales.LlamadaConstructor, "new");

        inicializarEntrada(NoTerminales.ExpresionParentizada, "(");

        inicializarEntrada(NoTerminales.LlamadaMetodo, "idMetVar");

        inicializarEntrada(NoTerminales.LlamadaMetodoEstatico, "idClase");

        inicializarEntrada(NoTerminales.ArgsActuales, "(");

        inicializarEntrada(NoTerminales.ListaExpsResto, ",");

        inicializarEntrada(NoTerminales.Encadenado, ".");
    }

    private void noterminales(){
        primeros.get(NoTerminales.Inicial).addAll(primeros.get(NoTerminales.ListaClases));
        primeros.get(NoTerminales.ListaClases).addAll(primeros.get(NoTerminales.Clase));
        primeros.get(NoTerminales.Clase).addAll(primeros.get(NoTerminales.ModificadorOpcional));
        primeros.get(NoTerminales.ListaMiembros).addAll(primeros.get(NoTerminales.Miembro));

        primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.Tipo));
        primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.ModificadorOpcional));
        primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.Constructor));

        primeros.get(NoTerminales.MiembroResto).addAll(primeros.get(NoTerminales.ArgsFormales));

        primeros.get(NoTerminales.TipoMetodo).addAll(primeros.get(NoTerminales.Tipo));

        primeros.get(NoTerminales.Tipo).addAll(primeros.get(NoTerminales.TipoPrimitivo));

        primeros.get(NoTerminales.ListaArgsFormales).addAll(primeros.get(NoTerminales.ArgFormal));

        primeros.get(NoTerminales.ArgFormal).addAll(primeros.get(NoTerminales.Tipo));

        primeros.get(NoTerminales.BloqueOpcional).addAll(primeros.get(NoTerminales.Bloque));

        primeros.get(NoTerminales.ListaSentancias).addAll(primeros.get(NoTerminales.Sentencia));


        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Expresion));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Return));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.If));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.While));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Bloque));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.VarLocal)); //ojo, segun drive es primeros de Asignacion

        primeros.get(NoTerminales.ExpresionOpcional).addAll(primeros.get(NoTerminales.Expresion));

        primeros.get(NoTerminales.Expresion).addAll(primeros.get(NoTerminales.ExpresionCompuesta));

        primeros.get(NoTerminales.ExpresionResto).addAll(primeros.get(NoTerminales.OperadorAsignacion));

        primeros.get(NoTerminales.ExpresionCompuesta).addAll(primeros.get(NoTerminales.ExpresionBasica));

        primeros.get(NoTerminales.ExpresionCompuestaResto).addAll(primeros.get(NoTerminales.OperadorBinario));

        primeros.get(NoTerminales.ExpresionBasica).addAll(primeros.get(NoTerminales.OperadorUnario));
        primeros.get(NoTerminales.ExpresionBasica).addAll(primeros.get(NoTerminales.Operando));

        primeros.get(NoTerminales.Operando).addAll(primeros.get(NoTerminales.Primitivo));
        primeros.get(NoTerminales.Operando).addAll(primeros.get(NoTerminales.Referencia));

        primeros.get(NoTerminales.Referencia).addAll(primeros.get(NoTerminales.Primario));

        primeros.get(NoTerminales.ReferenciaResto).addAll(primeros.get(NoTerminales.Encadenado));

        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.AccesoVar));
        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaConstructor));
        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaMetodo));
        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaMetodoEstatico));
        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.ExpresionParentizada));

        primeros.get(NoTerminales.ListaExpsOpcional).addAll(primeros.get(NoTerminales.ListaExps));

        primeros.get(NoTerminales.ListaExps).addAll(primeros.get(NoTerminales.Expresion));

        primeros.get(NoTerminales.RestoEncadenado).addAll(primeros.get(NoTerminales.ArgsActuales));
    }
    public ArrayList<String> getPrimeros(NoTerminales nt) {
        return primeros.get(nt);
    }

    //implementar memberPrimeros
    //dado un NT y un String? me fijo si ese valor esta en la clave
}

