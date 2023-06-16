package variable_compartida;

public class MiHebra extends Thread{

    private VariableCompartida vc;


    public MiHebra (VariableCompartida vc)
    {
        this.vc = vc;
    }

    public void run()
    {
        for(int i = 0; i < 10000; i++) vc.inc();
    }
}
