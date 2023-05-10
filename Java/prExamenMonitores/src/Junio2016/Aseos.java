package Junio2016;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aseos {

	boolean limpiando = false;
	boolean hanDejadoPasar = false;
	int numClientes = 0;
	Lock l = new ReentrantLock(true);
	Condition okEntrar = l.newCondition();
	Condition okLimpiar = l.newCondition();
	private int numLimpiadores = 0;

	public Aseos(){
		this.numClientes = 0;
	}

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 *
	 */
	public void entroAseo(int id) throws InterruptedException {
		l.lock();
		try {
			//CS
			while (limpiando || numLimpiadores > 0) okEntrar.await();
			//SC
			okEntrar.signal();
			numClientes++;
			System.out.printf("El cliente %d entra al aseo. TOTAL: %d\n", id, numClientes);
		}
		finally
		{
			l.unlock();
		}
		//CS injusta
		/*
		while(limpiando) wait();
		//SC
		numClientes++;
		hanDejadoPasar = true;
		System.out.println("El cliente " + id + " esta utilizando el aseo");
		notifyAll();*/
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 *
	 */
	public void salgoAseo(int id){
		l.lock();
		try
		{
			//SC
			System.out.printf("El cliente %d sale del aseo. TOTAL: %d\n",id,numClientes);
			numClientes--;
			//POST
			if (numClientes == 0 && limpiando) okLimpiar.signal();
		}
		finally
		{
			l.unlock();
		}
		/*
		//SC
		numClientes--;
		System.out.println("El cliente " + id + " deja de utilizar el baño");
		//POST
		if(numClientes == 0) notifyAll();*/
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 *
	 */
    public void entraEquipoLimpieza() throws InterruptedException {
		l.lock();
		try
		{
			//CS
			limpiando = true;
			while (numClientes > 0) okLimpiar.await();
			//SC
			System.out.println("\t El equipo de limpieza entra a limpiar");

		}
		finally
		{
			l.unlock();
		}
		/*
		//CS
		while (!hanDejadoPasar) wait();
		//SC
		limpiando = true;
		while(numClientes > 0) wait();
		System.out.println("\t Se esta limpiando el aseo");
		*/

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
			System.out.println("\t El equipo de limpieza ha terminado\n");
			limpiando = false;
			okEntrar.signalAll();
		}
		finally
		{
			l.unlock();
		}
		/*
		limpiando = false;
		//SC
		System.out.println("\t Se ha limpiado el aseo");
		//POST
		hanDejadoPasar = false;
		notifyAll();

		 */
	}
}
