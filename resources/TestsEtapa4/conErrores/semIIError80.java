///[Error:a1|12]
// Si a1 es un atributo declarado, la sentencia a1;

class A{
    A a1;
    A a2;
    void m1(){

    }
    static  void m2(){
        var r = true;
        r = a1 == a2;
    }

}

class Init{
    static void main()
    { }
}
