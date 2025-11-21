///10&20&exitosamente

class A {
    int x;
    void setX(int v) { x = v; }
    void printX() { debugPrint(x); }
}

class B extends A {
    void setX(int v) { x = v * 2; } // redefinición
}

class Init {
    static void main() {
        var a = new A();
        a.setX(10);
        a.printX();
        var b = new B();
        b.setX(10);
        b.printX();
    }
}
