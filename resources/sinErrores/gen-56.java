///33&exitosamente


class A { int x; void set(int v) { x = v; } int get() { return x; } }
class U { static void printTwice(A a) { System.printI(a.get()); System.printIln(a.get()); } }
class Init { static void main() { var a = new A(); a.set(3); U.printTwice(a); } }
