///[SinErrores]
// Prueba una Clase vacia

class Prueba1{
    int numeroMagico = 5;
    char caracterBonito = 'a';
    boolean testSintactico = true;

    public Prueba1(){
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
}
