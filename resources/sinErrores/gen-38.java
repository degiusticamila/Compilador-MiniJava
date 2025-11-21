///99&exitosamente

class A {
    int val;
    void set(int v) { val = v; }
    int get() { return val; }
}

class Util {
    static void printVal(A a) { System.printIln(a.get()); }
}

class Init {
    static void main() {
        var a = new A();
        a.set(99);
        Util.printVal(a);
    }
}
