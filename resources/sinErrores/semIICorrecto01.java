// Prueba un lado izquierdo simple

class A {
    int a1;
    int a2;

    void m1(){
        {
            var v1 = 6;
        }
        {
            var v1 = 7;
        }
        ++a1;
        var a3 = 5;
        ++a3;
    }
    void m2(){
        m1();
    }

}
class Init{
    static void main()
    { }
}