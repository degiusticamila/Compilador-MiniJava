///[Error:A|10]
// Clase B no declarada
class A {

    public A(int x, char y) {

    }

    void m1() {
        var obj = new A(5, "hola");
    }

    static void main() {}
}