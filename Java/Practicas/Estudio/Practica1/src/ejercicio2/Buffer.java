package ejercicio2;

import java.net.StandardSocketOptions;

public class Buffer {
    private int[] elem;
    private volatile int p,c,nelem;

    public Buffer(int tam)
    {
        p = 0;
        c = 0;
        nelem = 0;
        elem = new int[tam];
    }

    public int consumir(){
        //Preprotocolo
        while(nelem == 0) Thread.yield();

        //SC
        int dato = elem[c];
        System.out.println("Consume: " + c + "\tDato: " + dato);

        //Post
        c = (c+1)%elem.length;
        nelem--;

        return dato;
    }

    public void producir(int dato){
        //Pre
        while(nelem == elem.length) Thread.yield();

        //SC
        elem[p] = dato;
        System.out.println("Produce: " + p + "\tDato: " + dato);

        //Post
        p = (p+1)%elem.length;
        nelem++;
    }

}
