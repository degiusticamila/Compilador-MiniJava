///[Error:a1|8]

class A {
    int a1;
    B b1;
    C c1;
    boolean m1(){
        return a1;
    }

}
class B extends A {}

class C extends B{}



class Init{
    static void main()
    { }
}