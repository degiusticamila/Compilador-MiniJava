///[Error:=|17]

class A {}

class B extends A {
    void m1(){}
}

class Init{
    int x;
    B y;

    static void main(){

    }
    void m1(){
        y = new A();
    }
}
