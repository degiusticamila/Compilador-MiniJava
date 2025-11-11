///[SinErrores]

class A {
    B a1;
    int a2;
    void m1(B p1)
    {
        var v1 = new B();
        (m2().a3).a2 = 4;
    }
    B m2(){
        return a1;
    }
}
class B{
    A a3;
    A m3(){
        return a3;
    }
}
class Init{
    static void main()
    {

    }
}