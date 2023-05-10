package ejercicio3;

public class Lago {
    private volatile int nivel;
    //Variables Peterson para rios
    private volatile boolean fr0, fr1;
    private volatile int turnoR;
    //Variables Peterson para presas
    private volatile boolean fp0, fp1;
    private volatile int turnoP;
    //Variables Peterson Rio(0) - Presa(1)
    private volatile boolean frp0, frp1;
    private volatile int turnoRP;

    public Lago()
    {
        nivel = 0;
    }

    public int getNivel()
    {
        return nivel;
    }

    public void incRio0()
    {
        preRio0();
        preRioPresa0();
        //SC
        nivel++;
        System.out.println("El rio 0 incrementa el nivel en: " + nivel);
        postRioPresas0();
        postRio0();
    }

    public void incRio1()
    {
        preRio1();
        preRioPresa0();
        //SC
        nivel++;
        System.out.println("El rio 1 incrementa el nivel en: " + nivel);
        postRioPresas0();
        postRio1();
    }

    public void decPresa0()
    {
        prePresas0();
        //Pre
        while(nivel == 0) Thread.yield();
        preRioPresa1();
        //SC
        nivel--;
        System.out.println("\t Presa 0 decrementa el nivel a " + nivel);
        //Post
        postRioPresas1();
        postPresas0();
    }

    public void decPresa1()
    {
        prePresas1();
        //Pre
        while(nivel == 0) Thread.yield();
        preRioPresa1();
        //SC
        nivel--;
        System.out.println("\t Presa 0 decremental el nivel a " + nivel);
        //Post
        postRioPresas1();
        postPresas0();
    }

    //Metodos Peterson para presas
    private void prePresas0()
    {
        fp0 = true;
        turnoP = 1;
        while(fp1 && turnoP == 1) Thread.yield();
    }

    private void prePresas1()
    {
        fp1 = true;
        turnoP = 0;
        while(fp0 && turnoP == 0) Thread.yield();
    }

    private void postPresas0()
    {
        fp0 = false;
    }

    private void postPresas1()
    {
        fp1= false;
    }

    //Metodos Peterson para rios

    private void preRio0()
    {
        fr0 = true;
        turnoR = 1;
        while(fr1 && turnoR == 1) Thread.yield();
    }

    private void postRio0()
    {
        fr0 = false;
    }

    private void preRio1()
    {
        fr1 = true;
        turnoR = 0;
        while(fr0 && turnoR == 0) Thread.yield();
    }

    private void postRio1()
    {
        fr1 = false;
    }

    //Metodos Peterson para rio y presa

    private void postRioPresas1()
    {
        frp0 = false;
    }

    private void postRioPresas0()
    {
        frp0 = false;
    }

    private void preRioPresa0()
    {
        frp0 = true;
        turnoRP = 1;
        while(frp1 && turnoRP == 1) Thread.yield();
    }

    private void preRioPresa1()
    {
        frp1 = true;
        turnoRP = 0;
        while(frp0 && turnoRP == 0) Thread.yield();
    }

}
