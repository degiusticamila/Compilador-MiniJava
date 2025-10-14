///[Error:A|3]
// Circularidad
class A extends B {}
class B extends C {}
class C extends A {}
