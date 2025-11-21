///4&exitosamente

//PARAMETROS

class Calc {
    int f(int a, int b, int c) { return a - b * c; }
}
class Init {
    static void main() {
        var k = new Calc();
        System.printIln(k.f(10, 2, 3));
    }
}
