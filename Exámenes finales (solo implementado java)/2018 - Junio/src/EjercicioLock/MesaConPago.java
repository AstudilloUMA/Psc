package EjercicioLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MesaConPago {

    int racion = 8; //Los 8 trozos de una pizza
    Lock l = new ReentrantLock();

    boolean hayRacion = true;
    Condition condQuierenComer = l.newCondition();

    boolean hanLlamado = false;
    Condition condLlamaUncliente = l.newCondition();

    Condition condHaPagado = l.newCondition();




    /**
     *
     * @param id
     * El estudiante id quiere una ración de pizza.
     * Si hay una ración la coge y sigue estudiante.
     * Si no hay y es el primero que se da cuenta de que la mesa está vacía
     * llama al pizzero y
     * espera hasta que traiga una nueva pizza. Cuando el pizzero trae la pizza
     * espera hasta que el estudiante que le ha llamado le pague.
     * Si no hay pizza y no es el primer que se da cuenta de que la mesa está vacía
     * espera hasta que haya un trozo para él.
     * @throws InterruptedException
     *
     */
    public void nuevaRacion(int id) throws InterruptedException {
        try {
            l.lock();

            System.out.println("Persona " + id + " quiere comer");

            while (!hayRacion) {
                condQuierenComer.await();
            }

            if (racion == 0) {
                System.out.format("Estudiante %d ha llamado al pizzero\n", id);
                hayRacion = false;
                hanLlamado = true;
                condLlamaUncliente.signal();
                condHaPagado.await();
                System.out.format("Estudiante %d ha pagado\n", id);
            }

            racion--;
            System.out.println("Persona " + id + " ha comido. Quedan " + racion);


        } finally {
            l.unlock();
        }

    }


    /**
     * El pizzero entrega la pizza y espera hasta que le paguen para irse
     * @throws InterruptedException
     */
    public void nuevoCliente() throws InterruptedException {
        try {
            l.lock();

            while (!hanLlamado) {
                condLlamaUncliente.await();
            }
            System.out.println("Cliente nuevo");

        } finally {
            l.unlock();
        }

    }


    /**
     * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
     * llevársela a domicilio
     * @throws InterruptedException
     */
    public void nuevaPizza() throws InterruptedException{
        try {
            l.lock();

            racion = 8;
            hanLlamado = false;
            hayRacion = true;
            condHaPagado.signal();
            System.out.println("Pizza entragada\n");
            condQuierenComer.signalAll();

        } finally {
            l.unlock();
        }

    }
}
