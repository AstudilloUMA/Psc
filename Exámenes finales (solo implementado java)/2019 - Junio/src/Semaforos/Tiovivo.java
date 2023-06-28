package Semaforos;

import java.util.concurrent.Semaphore;


public class Tiovivo {

	int espacio;
	int espacioMax;

	Semaphore mutex = new Semaphore(1);
	Semaphore puedenSubir = new Semaphore(1);
	Semaphore puedenBajar = new Semaphore(1);
	Semaphore puedeArrancar = new Semaphore(0);

	public Tiovivo(int espacio) {
		this.espacio = espacio;
		this.espacioMax = espacio;
	}

	public void subir(int id) throws InterruptedException {
		puedenSubir.acquire();
		mutex.acquire();
		espacio--;
		System.out.println("Entra pasajero " + id + ". Quedan " + espacio + " asientos libres.");

		if (espacio > 0) {
			puedenSubir.release();
		} else if (espacio == 0) {
			puedeArrancar.release();
		}
		mutex.release();
	}

	public void bajar(int id) throws InterruptedException {
		puedenBajar.acquire();
		mutex.acquire();

		espacio++;
		System.out.println("Sale pasajero " + id + ". Quedan " + espacio + " asientos libres.");

		if (espacio < espacioMax) {
			puedenBajar.release();
		} else if (espacio == espacioMax) {
			puedenSubir.release();
		}
		mutex.release();
	}

	public void esperaLleno () throws InterruptedException {
		puedeArrancar.acquire();
	}

	public void finViaje () {
		System.out.println("Fin del viaje\n");
		puedenBajar.release();
	}
}


/*public class Tiovivo {

	int espacio;
	int espacioMax;

	Semaphore mutex = new Semaphore(1);
	Semaphore puedeArrancar = new Semaphore(0);
	Semaphore puedenEntrar = new Semaphore(1);
	Semaphore puedenBajar = new Semaphore(1);

	public Tiovivo(int espacio) {
		this.espacio = espacio;
		this.espacioMax = espacio;
	}
	
	public void subir(int id) throws InterruptedException {

		puedenEntrar.acquire();
		mutex.acquire();

		if (espacio == espacioMax) {
			puedenBajar.acquire();
		}

		espacio--;
		System.out.println("Entra pasajero " + id + ". Quedan " + espacio + " asientos libres.");

		mutex.release();

		if (espacio == 0) {
			puedeArrancar.release();
		}
		puedenEntrar.release();
	}
	
	public void bajar(int id) throws InterruptedException {
		puedenBajar.acquire();
		mutex.acquire();

		espacio++;
		System.out.println("Sale pasajero " + id + ". Quedan " + espacio + " asientos libres.");

		if (espacio == espacioMax) { //Porque el ultimo q entró está bloqueado
			puedenEntrar.release();
		}

 		mutex.release();
 		puedenBajar.release();
	}
	
	public void esperaLleno () throws InterruptedException {
		puedeArrancar.acquire();
		puedenEntrar.acquire();
		mutex.acquire();
	}
	
	public void finViaje () {
		System.out.println("Fin del viaje\n");
		puedenBajar.release();
		mutex.release();
	}
}*/
