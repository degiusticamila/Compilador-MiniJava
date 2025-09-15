///[SinErrores]
// Prueba una Clase vacia

class Prueba1{
    int numeroMagico;
    char caracterBonito;
    boolean testSintactico;

    public Prueba1(){
        numeroMagico = 100;
        caracterBonito = '*';
        ++numeroMagico;
        //numeroMagico++ en teoría según la gramática esto no es correcto
        testSintactico = false;
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
