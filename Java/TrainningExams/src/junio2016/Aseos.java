package junio2016;


import java.util.concurrent.Semaphore;

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
	Semaphore mutex = new Semaphore(1);
	Semaphore entrar = new Semaphore(1);
	Semaphore limpiar = new Semaphore(0);
	Semaphore salir = new Semaphore(0);


	public void entroAseo(int id) throws InterruptedException {
		//Pre
		entrar.acquire();
		mutex.acquire();
		//Sc
		if(numClientes == 0) limpiar.release();

		numClientes++;
		System.out.println("Entra " + id + ". TOTAL: " + numClientes);
		//Post
		mutex.release();
		entrar.release();

		/*
		//Pre
		mutex.acquire();
		entrar.acquire();
		//SC
		numClientes++;
		System.out.println("Entra el cliente " + id + ". TOTAL: " + numClientes);
		//Post
		entrar.release();
		mutex.release();
		*/
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 *
	 */
	public void salgoAseo(int id) throws InterruptedException {
		//Pre
		mutex.acquire();
		//Sc
		numClientes--;
		System.out.println("Sale " + id + ". TOTAL: " + numClientes);
		//Post
		if(numClientes == 0) salir.release();

		mutex.release();
		/*
		//Pre
		mutex.acquire();
		//SC
		numClientes--;
		System.out.println("Sale el cliente " + id + ". TOTAL: " + numClientes);
		//Post
		if(numClientes == 0) limpiando.release();
		mutex.release();
		*/
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 *
	 */
    public void entraEquipoLimpieza() throws InterruptedException {
		//Pre
		limpiar.acquire();
		System.out.println("\tEl equipo de limpieza quiere entrar");
		entrar.acquire();
		salir.acquire();
		//Sc
		System.out.println("\tEl equipo de limpieza entra");
		//Post
		mutex.acquire();

		/*
		//Pre
		limpiando.acquire();
		entrar.acquire();
		//SC
		System.out.println("\tEntra el equipo de limpieza");
		//Post
		*/
	}

    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos
	 *
	 *
	 */
    public void saleEquipoLimpieza() throws InterruptedException {
		//Pre
		//Sc
		System.out.println("El equipo de limpieza ha terminado");
		//Post
		entrar.release();
		mutex.release();

		/*
    	//Pre
		//SC
		System.out.println("\tSale el equipo de limpieza");
		//Post
		entrar.release();
		 */

	}
}
