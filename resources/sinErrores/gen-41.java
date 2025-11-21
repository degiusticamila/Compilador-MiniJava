///1&2&exitosamente
class Init {
    static void main() {
        var x = 0;
        if (x == 0) System.printIln(1); else System.printIln(0);
        if ((x != 0) && (1/x > 0)) System.printIln(999); // no debe ejecutar el RHS
        if ((x == 0) || (1/x > 0)) System.printIln(2);   // debe ejecutar por OR corto
    }
}
