///Retornando null

class A {
    int a1;
    B b1;
    C c1;
    A m1(){
        return null;
    }

}
class B extends A {}

class C extends B{}



class Init{
    static void main()
    { }
}