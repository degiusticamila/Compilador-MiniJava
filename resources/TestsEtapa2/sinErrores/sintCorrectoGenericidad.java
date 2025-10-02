///[SinErrores]

class MiClaseGenerica<T>{
    MiClase y = "hola";
    MiClase<T> miVariable;
}
class MiClase2<T> extends MiClase<T> {
    public MiClase2(){

    }
    void metodo(){

        var miclase = new MiClase();
        var miClase = new MiClase<>();
        var miClase = new MiClase<T>();

        var<T> mivariable = new MiClase<T>();
        var<T> mivariable = new MiClase<>();
    }
}
