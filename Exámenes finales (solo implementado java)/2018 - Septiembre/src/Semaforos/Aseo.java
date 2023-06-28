package Semaforos;


import java.util.concurrent.Semaphore;

public class Aseo {

	Semaphore mutex = new Semaphore(1);
	Semaphore mutex2 = new Semaphore(1);
	Semaphore aseo = new Semaphore(1);

	int hombres = 0, mujeres = 0;

	
	/**
	 * El hombre id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay alguna mujer en ese
	 * momento en el aseo
	 */
	public void llegaHombre(int id) throws InterruptedException {
		mutex.acquire();

		if (hombres == 0) {
			aseo.acquire();
		}
		hombres++;

		System.out.format("Entra un hombre, hay %d hombres.\n", hombres);

		mutex.release();
	}
	/**
	 * La mujer id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay algun hombre en ese
	 * momento en el aseo
	 */
	public void llegaMujer(int id) throws InterruptedException {
		mutex2.acquire();

		if (mujeres == 0) {
			aseo.acquire();
		}
		mujeres++;

		System.out.format("Entra una mujer, hay %d mujeres.\n", mujeres);

		mutex2.release();
	}
	/**
	 * El hombre id, que estaba en el aseo, sale
	 */
	public void saleHombre(int id)throws InterruptedException {
		mutex.acquire();

		hombres--;
		if (hombres == 0) {
			aseo.release();
		}
		System.out.format("Sale un hombre, hay %d hombres.\n", hombres);

		mutex.release();
	}
	
	public void saleMujer(int id)throws InterruptedException {
		mutex2.acquire();

		mujeres--;
		if (mujeres == 0) {
			aseo.release();
		}
		System.out.format("Sale una mujer, hay %d mujeres.\n", mujeres);

		mutex2.release();
	}
}
