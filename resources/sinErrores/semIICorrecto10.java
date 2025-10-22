// Acceso simple a una variable de instancia
// Chequea el uso de operadores unarios de incremento

class A {
    boolean a1;
    boolean a2;

    
     void m1(){
        a1 = true;
        a1 = 3 >= 4;
        a2 = ((4 > 5) || (6 > 7));
        a2 = ((4 > 5) && (6 > 7));
        a2 = 3 != 5;
        a2 = 2 == 2;

    }
    

}


class B extends A{
    
}


class Init{
    static void main()
    { }
}


