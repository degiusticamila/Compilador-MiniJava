///hola&exitosamente

class A {
    void hello() { System.printSln("hola"); }
}

class Init {
    static void main() {
        var a = new A();
        a.hello(); // resultado void → no debe dejar basura en la pila
    }
}
