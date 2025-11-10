///[Error:=|6]
// Si se tiene var x = m2() y m2 es void, tiene que tirar error.
class A {

    void m1(){
        var x = m2();
    }
    void m2(){}
    static void main() {
        var x = 10;
        var y = 20;
    }
}