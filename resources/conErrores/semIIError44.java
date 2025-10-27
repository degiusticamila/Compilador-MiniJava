///[Error:=|10]

class A {
    void m1(){}
}

class B extends A {
    void m2(){
        var i = 10;
        i = m1();
    }
}
class Init{
    static void main()
    { }
}