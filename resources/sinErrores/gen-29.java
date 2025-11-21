///1&2&3&exitosamente

class A {
    int a;
    void setA(int v) { a = v; }
    void printA() { debugPrint(a); }
}

class B extends A {
    int b;
    void setB(int v) { b = v; }
    void printB() { debugPrint(b); }
}

class C extends B {
    int c;
    void setC(int v) { c = v; }
    void printC() { debugPrint(c); }
}

class Init {
    static void main() {
        var c = new C();
        c.setA(1);
        c.setB(2);
        c.setC(3);
        c.printA();
        c.printB();
        c.printC();
    }
}
