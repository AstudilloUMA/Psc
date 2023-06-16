package intercalar_letras;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        String letra1, letra2, letra3;
        int num1, num2, num3;

        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca la letra: ");
        letra1 = sc.nextLine();

        System.out.println("Introduzca el numero de veces: ");
        num1 = sc.nextInt();
        sc.nextLine();

        System.out.println("Introduzca la letra: ");
        letra2 = sc.nextLine();

        System.out.println("Introduzca el numero de veces: ");
        num2 = sc.nextInt();
        sc.nextLine();

        System.out.println("Introduzca la letra: ");
        letra3 = sc.nextLine();

        System.out.println("Introduzca el numero de veces: ");
        num3 = sc.nextInt();
        sc.nextLine();

        MiThread h1 = new MiThread(letra1, num1);
        MiThread h2 = new MiThread(letra2, num2);
        MiThread h3 = new MiThread(letra3, num3);

        h1.start();
        h2.start();
        h3.start();

    }
}
