///6&exitosamente

class Calc {
    int sum(int a, int b, int c) { return a + b + c; }
}

class Init {
    static void main() {
        var c = new Calc();
        System.printIln(c.sum(1, 2, 3));
    }
}
