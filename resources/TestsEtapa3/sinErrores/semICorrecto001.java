/// [SinErrores]
abstract class A {
    abstract void m1();
    void m0(){}
    abstract void m2();
    void m4(){}

}
abstract class B extends A {
    void m4(){}
    void m1() {}
    void m5() {}
}
class C extends B {
    final void m4(){}
    final void m2() {}
    void m5(){}
}