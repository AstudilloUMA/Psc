package ejercicio2;

public class Buffer {
    private int[] elem;
    private volatile int p,c,nelem;
    public Buffer (int tam)
    {
        elem = new int[tam];
        /*
            p = 0;
            c = 0;
            nelem = 0;

            java por defecto inicializa las variables a 0
         */
    }

    public int consumir()
    {
        //Prs
        while(nelem == 0) Thread.yield(); //Mientras el buffer este vacio esperamos

        //SC
        int dato = elem[c];
        System.out.println("ejercicio2.Consumidor c: " + c + "\tDato: " + dato);

        //Post
        c = (c+1)%elem.length;
        nelem--;

        return dato;
    }

    public void producir(int dato)
    {
        //Prs
        while(nelem == elem.length) Thread.yield();

        //SC
        elem[p] = dato;
        System.out.println("ejercicio2.Productor p: " + p + "\tDato: " + dato);

        //Post
        p = (p+1)%elem.length;
        nelem++;
    }


}
