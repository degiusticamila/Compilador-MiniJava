///[Error:=|8]

class B extends A {}
class A {}
class C {
    B b;
    void m1(){
        b = new A();
    }
}

class Test {
    static void main() {}
}