package variable_compartida;

public class VariableCompartida {
    private int v;
    public volatile boolean running = false;

    public void set (int v)
    {
        this.v = v;
    }

    public int get ()
    {
        return v;
    }

    public void inc ()
    {
        v++;
    }
}
