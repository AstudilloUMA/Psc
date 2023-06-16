package variable_compartida;

public class MiHebraSet extends Thread{
    private VariableCompartida vc;

    public MiHebraSet (VariableCompartida vc)
    {
        this.vc = vc;
    }

    public void run()
    {
        for(int i = 0; i < 100; i++)
        {
            while(!vc.running) Thread.yield();

            vc.set(i);
            vc.running = true;
        }
    }

}
