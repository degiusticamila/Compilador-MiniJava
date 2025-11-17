///1234&33&3&exitosamente

class A{
    int x;
    public A(){

    }
      void mc(){
        debugPrint(1234);
        x = 33;
        debugPrint(x);
      }
}


class Init{
    static void main()
    {
        var a = new A();
        var b = 3;
        debugPrint(b);
        a.mc();
        
    }
}


