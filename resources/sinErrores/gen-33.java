///A.f&A.g&A.f&B.g&B.h&exitosamente

class A {
    void f() { System.printSln("A.f"); }
    void g() { System.printSln("A.g"); }
}

class B extends A {
    void g() { System.printSln("B.g"); } // redefinición
    void h() { System.printSln("B.h"); } // nuevo
}

class Init {
    static void main() {
        var a = new A();
        a.f(); a.g();
        var b = new B();
        b.f(); b.g(); b.h();
    }
}
