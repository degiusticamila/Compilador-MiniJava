///10&exitosamente

class Punto {
    int x;
    int y;

    public Punto(int a, int b) {
        x = a;
        y = b;
    }

    int suma() {
        return x + y;
    }
}
class Init {
    static void main() {
        var p = new Punto(7, 3);
        System.printIln(p.suma());
    }
}
