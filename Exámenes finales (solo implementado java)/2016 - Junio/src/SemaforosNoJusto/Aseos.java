package SemaforosNoJusto;


import java.util.concurrent.Semaphore;

public class Aseos {

	Semaphore mutex = new Semaphore(1);
	Semaphore puedenLimpiar = new Semaphore(0);
	Semaphore ocupado = new Semaphore(1);

	int genteDentro = 0;

	
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		mutex.acquire();
		ocupado.acquire();

		genteDentro++;

		System.out.println("Entra alguien. Hay " + genteDentro + " personas dentro.");

		ocupado.release();
		mutex.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException {
		mutex.acquire();

		genteDentro--;
		if (genteDentro == 0) {
			puedenLimpiar.release();
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
    	puedenLimpiar.acquire();
		ocupado.acquire();
		System.out.println("Entra equipo de limpieza");
	}

    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos
	 *
	 *
	 */
    public void saleEquipoLimpieza() throws InterruptedException {
		System.out.println("Sale equipo de limpieza\n");
		ocupado.release();
	}
}
