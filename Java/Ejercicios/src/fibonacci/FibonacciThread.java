package fibonacci;

public class FibonacciThread extends Thread{

    private int termino;
    private int resultado;

    public FibonacciThread(int termino)
    {
        this.termino = termino;
    }

    public int getResultado() {
        return resultado;
    }

    @Override
    public void run() {
        if (termino == 0)
        {
            resultado = 0;
            return;
        }
        else if (termino == 1)
        {
            resultado = 1;
            return;
        }

        FibonacciThread term1 = new FibonacciThread(termino-1);
        FibonacciThread term2 = new FibonacciThread(termino-2);

        term1.start();
        term2.start();

        try
        {
            term1.join();
            term2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        resultado = term1.getResultado() + term2.getResultado();
    }
}
