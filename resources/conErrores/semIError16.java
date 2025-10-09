///[Error:x|8]
//no es posible declarar una variable con
//el mismo nombre que una definida en una superclase
class A{
    int x;
}
class B extends A{
    int x;
}