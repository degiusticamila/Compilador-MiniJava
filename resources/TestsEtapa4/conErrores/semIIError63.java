///[Error:m2|9]
// method no existe en clase A
class A {
    static B method() {return null;}
}

class B {
    void m1() {
        A.method().m2();
    }

    static void main() {}
}