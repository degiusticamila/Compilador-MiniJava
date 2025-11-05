///[Error:=|9]
// El lado izquierdo de la asignación es no asignable - ln: 9
class A {
    int a1;

    static void m1(int p1)

    {
        new A() = 5;
    }

}
class B extends A{

}
class Init{
    static void main()
    { }
}