///Asignando null

class A {
    B b;
    void m1(){
        b  = null;
        var string = "Hola camila";
        string = null;
    }

}
class B extends A {}

class C extends B{}



class Init{
    static void main()
    { }
}