package fibonacci;

import java.util.Scanner;

public class FibonacciCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el numero de fibonacci a calcular: ");
        int termino = sc.nextInt();

        while (termino > 0)
        {
            FibonacciThread fb = new FibonacciThread(termino);
            fb.start();

            try {
                fb.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.printf("El resultado es %d\n", fb.getResultado());

            System.out.println("Introduzca el numero de fibonacci a calcular: ");
            termino = sc.nextInt();
        }
    }
}
