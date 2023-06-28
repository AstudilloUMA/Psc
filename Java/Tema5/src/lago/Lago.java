package lago;

public class Lago {

    private volatile int nivel;
    private volatile boolean r0, r1, p0, p1, rio, presa;
    private volatile int turno, turno2;

    public Lago()
    {
        nivel = 0;
    }

    public void incRio0()
    {
        //PrePeterson
        r0 = true;
        turno = 1;
        while(r1 &&  turno == 1) Thread.yield();
        //PrePeterson2
        rio = true;
        turno2 = 1;
        while(presa && turno2 == 1) Thread.yield();
        //SC
        nivel += 1000;
        System.out.println("El rio 0 incrementa \tNivel: " + nivel);
        //PostPeterson
        r0 = false;
        //PostPeterson2
        rio = false;
    }

    public void incRio1()
    {
        //PrePeterson
        r1 = true;
        turno = 0;
        while(r0 &&  turno == 0) Thread.yield();
        //PrePeterson2
        rio = true;
        turno2 = 1;
        while(presa && turno2 == 1) Thread.yield();
        //SC
        nivel += 1000;
        System.out.println("El rio 1 incrementa \tNivel: " + nivel);
        //PostPeterson
        r1 = false;
        //PostPeterson2
        rio = false;
    }

    public void decPresa0()
    {
        //PrePeterson
        p0 = true;
        turno = 3;
        while(p1 &&  turno == 3) Thread.yield();
        //Pre
        while(nivel == 0) Thread.yield();
        //PrePeterson2
        presa = true;
        turno2 = 0;
        while(rio && turno2 == 0) Thread.yield();
        //SC
        nivel -= 1000;
        System.out.println("\tLa presa 0 decrementa \tNivel: " + nivel);
        //PostPeterson
        p0 = false;
        //PostPeterson2
        presa = false;
    }

    public void decPresa1()
    {
        //PrePeterson
        p1 = true;
        turno = 2;
        while(p1 &&  turno == 2) Thread.yield();
        //Pre
        while(nivel == 0) Thread.yield();
        //PrePeterson2
        presa = true;
        turno2 = 0;
        while(rio && turno2 == 0) Thread.yield();
        //SC
        nivel -= 1000;
        System.out.println("\tLa presa 1 decrementa \tNivel: " + nivel);
        //PostPeterson
        p1 = false;
        //PostPeterson2
        presa = false;
    }

    public int nivel()
    {
        return nivel;
    }



}
