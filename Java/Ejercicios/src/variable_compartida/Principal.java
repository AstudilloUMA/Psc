package variable_compartida;

public class Principal {
    public static void main(String[] args) throws InterruptedException {
        VariableCompartida vc = new VariableCompartida();
        vc.set(0);

        MiHebra h1 = new MiHebra(vc);
        MiHebra h2 = new MiHebra(vc);

        h1.start();
        h2.start();

        h1.join();
        h2.join();

        System.out.println("El valor de la variable compartido es " + vc.get());
    }
}
