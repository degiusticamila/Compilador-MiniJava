package TablaDeSimbolos;

import Utils.Token;

public abstract class OffsetElemento {
    private Token n;
    public abstract int getOffset();

    public abstract void setOffset(int n);
}
