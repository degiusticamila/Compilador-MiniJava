///5&5&exitosamente

class A { int x; void set(int v) { x = v; } int get() { return x; } void print() { System.printIln(x); } }
class Init {
    static void main() {
        var a = new A();
        a.set(5);
        System.printIln(a.get());
        a.print();
        a.get();
    }
}

