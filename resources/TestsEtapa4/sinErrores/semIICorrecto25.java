//chequeando parametros nulos
class A {
    B b1;
    public A(B b){

    }
    void m1(B b){

    }
}
class B{

}
class Init{
    A a;
    static void main()
    {
       a = new A(null);
       a.m1(null);
       a = new A(new B());

    }
}