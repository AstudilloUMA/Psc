package variable_compartida;

public class MiHebraGet extends Thread{
    private VariableCompartida vc;

    public MiHebraGet (VariableCompartida vc)
    {
        this.vc = vc;
    }

    public void run() {
        for(int i = 0; i < 100; i++)
        {
            while(vc.running) Thread.yield();
            System.out.println("La variable compartida tiene el valor " + vc.get());
            vc.running = false;
        }
    }
}
