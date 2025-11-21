///14&exitosamente
class A { int x; void set(int v) { x = v; } int get() { return x; } }
class Util { static void twice(A a) { a.set(a.get() * 2); } }
class Init {
    static void main() {
        var a = new A();
        a.set(7);
        Util.twice(a);
        System.printIln(a.get());
    }
}