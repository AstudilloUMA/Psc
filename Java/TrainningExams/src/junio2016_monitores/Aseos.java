package junio2016_monitores;


import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aseos {
	//CS1 - num de clientes infinito en los aseos
	//CS2 - nadie en el aseo con el equipo de limpieza

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 *
	 */

	private int numClientes;
	Lock l = new ReentrantLock();

	boolean hanPasado = true;
	Condition okEntrar = l.newCondition();

	boolean quierenLimpiar = true;
	Condition okLimpiar = l.newCondition();


	public void entroAseo(int id) throws InterruptedException {
		l.lock();
		try
		{
			//Pre
			while(quierenLimpiar) okEntrar.await();
			//SC
			hanPasado = true;
			numClientes++;
			System.out.println("Entra " + id + ". TOTAL: " + numClientes);
			okLimpiar.signal();
			//Post
		}finally {
			l.unlock();
		}
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 *
	 */
	public void salgoAseo(int id) throws InterruptedException {
		l.lock();
		try
		{
			numClientes--;
			System.out.println("Sale "+ id+ ". TOTAL: " + numClientes);
			if(numClientes == 0)
			{
				okLimpiar.signal();
			}
		}finally {
			l.unlock();
		}
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 *
	 */
    public void entraEquipoLimpieza() throws InterruptedException {
		l.lock();
		try
		{
			while(!hanPasado) okLimpiar.await();
			System.out.println("\tEl equipo de limpieza quiere entrar");
			quierenLimpiar = true;
			while (numClientes != 0) okLimpiar.await();
			System.out.println("\tEntra el equipo de limpieza");
		}finally {
			l.unlock();
		}
	}

    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos
	 *
	 *
	 */
    public void saleEquipoLimpieza() throws InterruptedException {
		l.lock();
		try
		{
			quierenLimpiar = false;
			System.out.println("\tLos limpiadores se van");
			hanPasado = false;
			okEntrar.signalAll();
		}finally {
			l.unlock();
		}
	}
}
