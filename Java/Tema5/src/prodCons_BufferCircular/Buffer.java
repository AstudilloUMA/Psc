package prodCons_BufferCircular;


public class Buffer {
    private int elem[];
    private volatile int p, c, nelem, turno;
    private volatile boolean consumir, producir;

    public Buffer(int tam)
    {
        elem = new int[tam];
    }

    public void producir(int dato)
    {
        //Pre
        while(nelem == elem.length) Thread.yield();
        //PrePeterson
        preProP();
        //SC
        elem[p] = dato;
        System.out.printf("Productor: %d \t Dato: %d\n", p, dato);
        //Post
        p = (p+1)%elem.length;
        nelem++;
        //PostPeterson
        postProP();
    }

    public int consumir()
    {
        //Pre
        while(nelem == 0) Thread.yield();
        //PrePeterson
        preProC();
        //SC
        int dato = elem[c];
        System.out.printf("\t Consumidor: %d \t Dato: %d\n", c, dato);
        //Post
        c = (c+1)%elem.length;
        nelem--;
        //PostPeterson
        postProC();

        return dato;
    }

    private void preProP()
    {
        producir = true;
        turno = 1;
        while(consumir && turno == 1) Thread.yield();
    }

    private void preProC()
    {
        consumir = true;
        turno = 0;
        while(producir && turno == 0) Thread.yield();
    }

    private void postProP()
    {
        producir = false;
    }

    private void postProC()
    {
        consumir = false;
    }


}
