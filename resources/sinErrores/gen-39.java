///5&-1&exitosamente

class A {
    int x;
    void set(int v) { x = v; }
    void print() { System.printIln(x); }
}

class B extends A {
    void set(int v) {
        if (v > 0) x = v;
        else x = -1;
    }
}

class Init {
    static void main() {
        var b = new B();
        b.set(5);
        b.print();
        b.set(-3);
        b.print();
    }
}
