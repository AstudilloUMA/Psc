package fibonacci;

public class Fibonacci_Thread extends Thread{
    private int termino;
    private int fibN, fibN_1;

    public Fibonacci_Thread(int termino)
    {
        this.termino = termino;
    }

    public int getFibN() {
        return fibN;
    }

    public int getFibN_1() {
        return fibN_1;
    }

    @Override
    public void run() {
        if (termino == 0)
        {
            fibN = 0;
            return;
        }
        else if (termino == 1)
        {
            fibN = 1;
            fibN_1 = 0;
            return;
        }

        Fibonacci_Thread previo = new Fibonacci_Thread(termino-1);

        previo.start();
        try {
            previo.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        fibN_1 = previo.getFibN();
        fibN = fibN_1 + previo.getFibN_1();
    }
}
