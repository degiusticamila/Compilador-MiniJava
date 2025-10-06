package Utils;

import Sintactico.NoTerminales;
import Sintactico.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
        inicializarEntrada(NoTerminales.Clase, "class");
        inicializarEntrada(NoTerminales.Interfaz, "interface");
        //inicializarEntrada(NoTerminales.ModificadorOpcionalInterfaz, "public");
        inicializarEntrada(NoTerminales.ModificadorOpcional, "abstract","static", "final");
        inicializarEntrada(NoTerminales.HerenciaOpcional, "extends");
        inicializarEntrada(NoTerminales.HerenciaOpcionalInterfaz, "extends");
        inicializarEntrada(NoTerminales.HerenciaOpcional, "implements");

        //Logro interfaces genericas
        inicializarEntrada(NoTerminales.ListaMiembrosInterfaz, "void");


        inicializarEntrada(NoTerminales.Miembro, "void");
        inicializarEntrada(NoTerminales.MiembroResto, ";");


        inicializarEntrada(NoTerminales.Constructor, "public");
        inicializarEntrada(NoTerminales.Constructor, "idClase");

        //inicializarEntrada(NoTerminales.VisibilidadOpcional, "public", "private");

        inicializarEntrada(NoTerminales.TipoMetodo, "void");

        inicializarEntrada(NoTerminales.Tipo, "idClase");

        inicializarEntrada(NoTerminales.TipoPrimitivo, "boolean", "int", "char");

        inicializarEntrada(NoTerminales.ArgsFormales, "(");

        inicializarEntrada(NoTerminales.ListaArgsFormalesResto, ",");
        inicializarEntrada(NoTerminales.BloqueOpcional, ";");
        inicializarEntrada(NoTerminales.Bloque, "{");

        inicializarEntrada(NoTerminales.Sentencia, ";");

        inicializarEntrada(NoTerminales.VarLocal, "var");

        inicializarEntrada(NoTerminales.Return, "return");

        inicializarEntrada(NoTerminales.If, "if");

        inicializarEntrada(NoTerminales.IfResto, "else");

        inicializarEntrada(NoTerminales.While, "while");

        inicializarEntrada(NoTerminales.OperadorAsignacion, "=");
        inicializarEntrada(NoTerminales.OperadorBinario, "||","&&","==","!=",">", "<", ">=", "<=", "+", "-","*", "/", "%");
        inicializarEntrada(NoTerminales.OperadorUnario, "+", "++", "-", "--", "!");

        inicializarEntrada(NoTerminales.Primitivo, "true", "false", "intLiteral", "charLiteral", "null");

        inicializarEntrada(NoTerminales.Primario, "this", "stringLiteral");

        inicializarEntrada(NoTerminales.LlamadaConstructor, "new");

        inicializarEntrada(NoTerminales.ExpresionParentizada, "(");

        inicializarEntrada(NoTerminales.LlamadaMetodo, "idMetVar");

        inicializarEntrada(NoTerminales.LlamadaMetodoEstatico, "idClase");

        inicializarEntrada(NoTerminales.ArgsActuales, "(");

        inicializarEntrada(NoTerminales.ListaExpsResto, ",");

        inicializarEntrada(NoTerminales.Encadenado, ".");

        inicializarEntrada(NoTerminales.TipoParametricoOpcional, "<");

        inicializarEntrada(NoTerminales.TipoParametricoInst, "<");
        inicializarEntrada(NoTerminales.TipoParametricoInstResto, ">", "idClase");
    }

    private void noterminales(){
        //primeros.get(NoTerminales.Inicial).addAll(primeros.get(NoTerminales.ListaClases));
        //primeros.get(NoTerminales.ListaClases).addAll(primeros.get(NoTerminales.Clase));
        //primeros.get(NoTerminales.Clase).addAll(primeros.get(NoTerminales.ModificadorOpcional));

       // primeros.get(NoTerminales.ListaMiembros).addAll(primeros.get(NoTerminales.Miembro));

       // primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.Tipo));
       // primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.ModificadorOpcional));
       // primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.Constructor));

       // primeros.get(NoTerminales.MiembroResto).addAll(primeros.get(NoTerminales.ArgsFormales));

      //  primeros.get(NoTerminales.TipoMetodo).addAll(primeros.get(NoTerminales.Tipo));

      //  primeros.get(NoTerminales.Tipo).addAll(primeros.get(NoTerminales.TipoPrimitivo));



        // primeros.get(NoTerminales.ListaMiembros).addAll(primeros.get(NoTerminales.Miembro));

       // primeros.get(NoTerminales.ListaArgsFormales).addAll(primeros.get(NoTerminales.ArgFormal));

//        primeros.get(NoTerminales.ArgFormal).addAll(primeros.get(NoTerminales.Tipo));

      //  primeros.get(NoTerminales.BloqueOpcional).addAll(primeros.get(NoTerminales.Bloque));

    //    primeros.get(NoTerminales.ListaSentencias).addAll(primeros.get(NoTerminales.Sentencia));


    //    primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Expresion));
    //    primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Return));
    //    primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.If));
    //    primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.While));
    //    primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Bloque));
    //    primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.VarLocal)); //ojo, segun drive es primeros de Asignacion

    //    primeros.get(NoTerminales.ExpresionOpcional).addAll(primeros.get(NoTerminales.Expresion));

    //    primeros.get(NoTerminales.Expresion).addAll(primeros.get(NoTerminales.ExpresionCompuesta));

    //    primeros.get(NoTerminales.ExpresionResto).addAll(primeros.get(NoTerminales.OperadorAsignacion));

    //    primeros.get(NoTerminales.ExpresionCompuesta).addAll(primeros.get(NoTerminales.ExpresionBasica));

   //     primeros.get(NoTerminales.ExpresionCompuestaResto).addAll(primeros.get(NoTerminales.OperadorBinario));

    //    primeros.get(NoTerminales.ExpresionBasica).addAll(primeros.get(NoTerminales.OperadorUnario));
    //    primeros.get(NoTerminales.ExpresionBasica).addAll(primeros.get(NoTerminales.Operando));

    //    primeros.get(NoTerminales.Operando).addAll(primeros.get(NoTerminales.Primitivo));
    //    primeros.get(NoTerminales.Operando).addAll(primeros.get(NoTerminales.Referencia));

    //    primeros.get(NoTerminales.Referencia).addAll(primeros.get(NoTerminales.Primario));

     //   primeros.get(NoTerminales.ReferenciaResto).addAll(primeros.get(NoTerminales.Encadenado));

     //   primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.AccesoVar));
     //   primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaConstructor));
     //   primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaMetodo));
     //   primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaMetodoEstatico));
     //   primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.ExpresionParentizada));

      //  primeros.get(NoTerminales.ListaExpsOpcional).addAll(primeros.get(NoTerminales.ListaExps));

       // primeros.get(NoTerminales.ListaExps).addAll(primeros.get(NoTerminales.Expresion));

       // primeros.get(NoTerminales.RestoEncadenado).addAll(primeros.get(NoTerminales.ArgsActuales));


        /* REACOMODADO!*/
        primeros.get(NoTerminales.RestoEncadenado).addAll(primeros.get(NoTerminales.ArgsActuales));

       // primeros.get(NoTerminales.ListaExps).addAll(primeros.get(NoTerminales.Expresion));

      //  primeros.get(NoTerminales.ListaExpsOpcional).addAll(primeros.get(NoTerminales.ListaExps));

        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.ExpresionParentizada));
        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaMetodoEstatico));
        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaMetodo));
        primeros.get(NoTerminales.Primario).addAll(primeros.get(NoTerminales.LlamadaConstructor));

        primeros.get(NoTerminales.ReferenciaResto).addAll(primeros.get(NoTerminales.Encadenado));

        primeros.get(NoTerminales.Referencia).addAll(primeros.get(NoTerminales.Primario));

        primeros.get(NoTerminales.Operando).addAll(primeros.get(NoTerminales.Referencia));
        primeros.get(NoTerminales.Operando).addAll(primeros.get(NoTerminales.Primitivo));

        primeros.get(NoTerminales.ExpresionBasica).addAll(primeros.get(NoTerminales.Operando));
        primeros.get(NoTerminales.ExpresionBasica).addAll(primeros.get(NoTerminales.OperadorUnario));

        primeros.get(NoTerminales.ExpresionCompuestaResto).addAll(primeros.get(NoTerminales.OperadorBinario));
        primeros.get(NoTerminales.ExpresionCompuesta).addAll(primeros.get(NoTerminales.ExpresionBasica));

        primeros.get(NoTerminales.ExpresionResto).addAll(primeros.get(NoTerminales.OperadorAsignacion));
        primeros.get(NoTerminales.Expresion).addAll(primeros.get(NoTerminales.ExpresionCompuesta));

        primeros.get(NoTerminales.ExpresionOpcional).addAll(primeros.get(NoTerminales.Expresion));

        primeros.get(NoTerminales.ListaExps).addAll(primeros.get(NoTerminales.Expresion));
        primeros.get(NoTerminales.ListaExpsOpcional).addAll(primeros.get(NoTerminales.ListaExps));

        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.VarLocal));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Bloque));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.While));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.If));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Return));
        primeros.get(NoTerminales.Sentencia).addAll(primeros.get(NoTerminales.Expresion));

        primeros.get(NoTerminales.ListaSentencias).addAll(primeros.get(NoTerminales.Sentencia));

        primeros.get(NoTerminales.BloqueOpcional).addAll(primeros.get(NoTerminales.Bloque));

        /*---------------------------Visibilidad mejorada----------------------------------------------*/
        //primeros.get(NoTerminales.ListaMiembros).addAll(primeros.get(NoTerminales.VisibilidadOpcional));
        /*---------------------------------------------------------------------------------------------*/

        primeros.get(NoTerminales.ListaMiembros).addAll(primeros.get(NoTerminales.Miembro));


        primeros.get(NoTerminales.Tipo).addAll(primeros.get(NoTerminales.TipoPrimitivo));
        primeros.get(NoTerminales.ArgFormal).addAll(primeros.get(NoTerminales.Tipo)); //nueva
        primeros.get(NoTerminales.ListaArgsFormales).addAll(primeros.get(NoTerminales.ArgFormal)); //nueva
        primeros.get(NoTerminales.ListaArgsFormalesOpcional).addAll(primeros.get(NoTerminales.ListaArgsFormales));
        primeros.get(NoTerminales.TipoMetodo).addAll(primeros.get(NoTerminales.Tipo));

        primeros.get(NoTerminales.MiembroResto).addAll(primeros.get(NoTerminales.ArgsFormales));

        primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.Tipo));
        primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.ModificadorOpcional));
        primeros.get(NoTerminales.Miembro).addAll(primeros.get(NoTerminales.Constructor));

        //primeros.get(NoTerminales.Interfaz).addAll(primeros.get(NoTerminales.ModificadorOpcionalInterfaz)); //NUEVO PARA PUBLIC

        primeros.get(NoTerminales.MiembrosInterfaz).addAll(primeros.get(NoTerminales.ModificadorOpcional));
        primeros.get(NoTerminales.MiembrosInterfaz).addAll(primeros.get(NoTerminales.TipoMetodo));
        primeros.get(NoTerminales.ListaMiembrosInterfaz).addAll(primeros.get(NoTerminales.MiembrosInterfaz));
        primeros.get(NoTerminales.Clase).addAll(primeros.get(NoTerminales.ModificadorOpcional));

        primeros.get(NoTerminales.Clase_Interfaz).addAll(primeros.get(NoTerminales.Clase));

        /*---------------------------Interfaces---------------------------------------------*/
        primeros.get(NoTerminales.Clase_Interfaz).addAll(primeros.get(NoTerminales.Interfaz));
        primeros.get(NoTerminales.ListaClases).addAll(primeros.get(NoTerminales.Clase_Interfaz));
        primeros.get(NoTerminales.Inicial).addAll(primeros.get(NoTerminales.ListaClases));

        //Primeros para los LOGROS
        /*--------------------------Atributos inicializados--------------------------------*/
        primeros.get(NoTerminales.MiembroResto).addAll(primeros.get(NoTerminales.OperadorAsignacion));




    }
    public boolean estaEnPrimeros(NoTerminales nt, String tokenID){
        //getMapeo();

        return primeros.get(nt).contains(tokenID);
    }
    public HashMap<NoTerminales, ArrayList<String>> getMapeo() {
        for (HashMap.Entry<NoTerminales, ArrayList<String>> entrada : primeros.entrySet()) {
            NoTerminales clave = entrada.getKey();
            ArrayList<String> valor = entrada.getValue();
            System.out.println("Clave: " + clave + ", Valor: " + valor);
        }
        return primeros;
    }

}

