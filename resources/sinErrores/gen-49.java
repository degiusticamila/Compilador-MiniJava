///42&exitosamente
class A {
    int x;
    void set(int v) { x = v; }
    int get() { return x; }
}

class Holder {
    A a;
    public Holder() {
        a = new A();   // inicializa el atributo 'a'
        a.set(42);     // asigna un valor válido
    }
}

class Init {
    static void main() {
        var h = new Holder();
        System.printIln(h.a.get()); // acceso encadenado correcto
    }
}
