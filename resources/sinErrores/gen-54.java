///10&exitosamente
class A {

    int x;
    void set(int v) { x = v; }
    int get() { return x; }
}

class Init {
    static void main() {
        var a = new A();
        a.set(10);
        System.printIln(a.get()); // llamada a método con retorno int
    }
}