///42&exitosamente
///
class A { int x; void set(int v) { x = v; } int get() { return x; } }
class B { A a; void init() { a = new A(); } void setx(int v) { a.set(v); } int getx() { return a.get(); } }
class Init {
    static void main() {
        var b = new B();
        b.init();
        b.setx(42);
        System.printIln(b.getx());
    }
}

