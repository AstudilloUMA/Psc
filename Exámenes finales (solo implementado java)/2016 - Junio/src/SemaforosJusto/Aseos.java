package SemaforosJusto;


import java.util.concurrent.Semaphore;


public class Aseos {

	Semaphore mutex = new Semaphore(1);
	Semaphore puedenEntrarClientes = new Semaphore(1);
	Semaphore aseoSucio = new Semaphore(0);
	Semaphore todosFuera = new Semaphore(0);

	int genteDentro = 0;


	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 *
	 */
	public void entroAseo(int id) throws InterruptedException {
		puedenEntrarClientes.acquire();
		mutex.acquire();

		if (genteDentro == 0) {
			aseoSucio.release();
		}

		genteDentro++;
		System.out.println("Entra alguien. Hay " + genteDentro + " personas dentro.");
		mutex.release();
		puedenEntrarClientes.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 *
	 */
	public void salgoAseo(int id) throws InterruptedException {
		mutex.acquire();

		genteDentro--;

		if (genteDentro == 0) {
			todosFuera.release();
		}
		System.out.println("Sale alguien. Hay " + genteDentro + " personas dentro.");
		mutex.release();
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 *
	 */
	public void entraEquipoLimpieza() throws InterruptedException {
		aseoSucio.acquire();
		System.out.println("\tEl equipo de limpieza quiere entrar");
		puedenEntrarClientes.acquire();
		todosFuera.acquire();

		mutex.acquire();
		System.out.println("\tEntra equipo de limpieza");
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos
	 *
	 *
	 */
	public void saleEquipoLimpieza() throws InterruptedException {
		System.out.println("\tSale equipo de limpieza\n");
		puedenEntrarClientes.release();
		mutex.release();
	}
}


/*
public class Aseos {

	Semaphore mutex = new Semaphore(1);
	Semaphore ocupado = new Semaphore(1);
	Semaphore todosFuera = new Semaphore(0);
	Semaphore aseosSucios = new Semaphore(0);

	int genteDentro = 0;

	
	*/
/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 * 
	 *//*

	public void entroAseo(int id) throws InterruptedException {
		ocupado.acquire();
		mutex.acquire();

		if (genteDentro == 0) {
			aseosSucios.release();
		}

		genteDentro++;

		System.out.println("Entra alguien. Hay " + genteDentro + " personas dentro.");

		mutex.release();
		ocupado.release();
	}

	*/
/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 *//*

	public void salgoAseo(int id) throws InterruptedException {
		mutex.acquire();

		genteDentro--;
		System.out.println("Sale alguien. Hay " + genteDentro + " personas dentro.");

		if (genteDentro == 0) {
			todosFuera.release();
		}

		mutex.release();
	}
	
	*/
/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 * 
	 *//*

    public void entraEquipoLimpieza() throws InterruptedException {
		aseosSucios.acquire();
		ocupado.acquire();
		System.out.println("\tEl equipo de limpieza quiere entrar");
		todosFuera.acquire();
		System.out.println("\tEntra equipo de limpieza");
	}

    */
/**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos
	 *
	 *
	 *//*

    public void saleEquipoLimpieza() throws InterruptedException {
		System.out.println("\tSale equipo de limpieza\n");
		ocupado.release();
	}
}
*/
