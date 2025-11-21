///42&exitosamente

class A {
    int x;
    void setX(int v) { x = v; }
    int getX() { return x; }
}

class B {
    A a;
    void init() { a = new A(); a.setX(42); }
    void print() { System.printIln(a.getX()); }
}

class Init {
    static void main() {
        var b = new B();
        b.init();
        b.print();
    }
}
