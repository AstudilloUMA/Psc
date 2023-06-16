package intercalar_letras;

import java.util.Random;

public class MiThread extends Thread{

    String letra;
    int num;

    public MiThread(String letra, int num)
    {
        this.letra = letra;
        this.num = num;
    }

    public void run ()
    {
        for(int i = 0; i < num; i++)
        {
            System.out.printf("\t%s\n", letra);
            Thread.yield();
        }
    }
}
