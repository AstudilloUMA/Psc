package variable_compartida;

public class Principal2 {
    public static void main(String[] args) throws InterruptedException {
        VariableCompartida vc = new VariableCompartida();

        MiHebraSet hs = new MiHebraSet(vc);
        MiHebraGet hg = new MiHebraGet(vc);

        hs.start();
        hg.start();

    }
}
