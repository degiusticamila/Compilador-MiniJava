///[Error:method|9]
// method no existe en clase A
class A {

}

class B {
    void m1() {
        A.method();
    }

    static void main() {}
}