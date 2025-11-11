/// SinErrores
// Conformidad de tipos en una expresion binaria

class A {
    A a;
}
class B extends A {
    boolean r;
    B b;


    void m1(){
        r = b == a;
        r = b != a;
    }
}
class Init{
    static void main()
    { }
}