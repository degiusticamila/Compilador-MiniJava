///[Error:=|14]

class A {}

class B extends A {
    void m1(){}
}

class Init{
    int x;
    B y;

    static void main(){
        y = new A();
    }
}
