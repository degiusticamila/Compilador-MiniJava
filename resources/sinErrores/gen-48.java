///5&exitosamente
class A { int x; void set(int v) { x = v; } int get() { return x; } }
class Init {
    static void main() {
        var a = new A();
        var b = a;
        b.set(5);
        System.printIln(a.get());
    }
}


