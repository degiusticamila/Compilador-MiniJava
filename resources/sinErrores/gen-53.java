///3&exitosamente

class A { int x; void set(int v) { x = v; } }
class Init {
    static void main() {
        var a = new A(); a.set(1);
        var b = new A(); b.set(2);
        System.printIln(a.x + b.x);
    }
}
