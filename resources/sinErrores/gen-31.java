///12&exitosamente

class A {
    int sum(int x, int y) { return x + y; }
}

class Init {
    static void main() {
        var a = new A();
        debugPrint(a.sum(7, 5));
    }
}

