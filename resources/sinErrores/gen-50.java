///A'&b&z&exitosamente
class A {
    void a() { System.printSln("a"); }
    void z() { System.printSln("z"); }
}
class B extends A {
    void b() { System.printSln("b"); }
    void a() { System.printSln("A'"); }
}
class Init {
    static void main() {
        var x = new B();
        x.a();
        x.b();
        x.z();
    }
}

