///[Error:=|11]
// Si a1 es un atributo declarado, la sentencia a1;

class A{
    A a1;

    void m1(){

    }
    void m2(){
        a1.m1() = 5;

    }
}

class Init{
    static void main()
    { }
}
