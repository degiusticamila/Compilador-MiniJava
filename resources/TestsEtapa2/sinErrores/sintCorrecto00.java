///[SinErrores]

 class Prueba1{
    void declaracionesVariables(){
        var num = 5;
        var a = 'a';
        var bool1 = true;
        var bool2 = false;
        var mivarNula = null;
        var sum = 5 + 10;
    }
    void chequearConstructores(){
        var num = new Prueba1();
        var num = new Prueba1(12, true, 'a', null);
        var num = new Prueba1<T>();
        var nm = new Prueba1<>();
        Prueba1.metodo(12, 'b');
        var i = 0;
        while (i < 10) {
            i = 1;
            ++i;
        }
    }
    int sum(int a, int b) { return a + b; }
}
