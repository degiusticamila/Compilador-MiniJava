///[SinErrores]
// Prueba una Clase vacia

class Prueba1{
    int numeroMagico = 5;
    char caracterBonito = 'a';
    boolean testSintactico = true;

    public Prueba1(){
       // int x; Esto se debería poder para el logro de variables clásicas
        numeroMagico = 100;
        caracterBonito = '*';
        testSintactico = false;
        ++numeroMagico;
        testSintactico = false;
    }
    public Prueba2(){
        //Llamando a metodo encadenados
        numeroMagico.metodoSumar();
        numeroMagico.metodoSumar().toString();
        numeroMagico.metodoSumar().toString().aprobarEtapa();
    }
    void metodoSumar(){
        var i = 0;
        while(i != 5){
            if(caracterBonito == '*'){
                caracterBonito = '*';
            }
            ++i;
        }
    }
    boolean testConstructor(){
        var a = 2 * (3 + 4);
        var miVariable = "hola";
        return true;

        var miclase = new MiClase(param1);
        var miclase = new MiClase(param1, param2);
    }
}
