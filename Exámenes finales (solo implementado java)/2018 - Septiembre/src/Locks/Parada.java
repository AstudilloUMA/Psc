package Locks;


import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Parada {
	
	boolean hayBus = false;
	boolean todoSubidos = false;
	int genteEsperando = 0;

	Lock l = new ReentrantLock();

	Condition condPrincipal = l.newCondition();
	Condition condSecunadario = l.newCondition();

	boolean primeracolaVacia = false;
	Condition condCambiarCola = l.newCondition();

	Condition condBuses = l.newCondition();

	public Parada(){
		
	}
	/**
	 * El pasajero id llama a este metodo cuando llega a la parada.
	 * Siempre tiene que esperar el siguiente autobus en uno de los
	 * dos grupos de personas que hay en la parada
	 * El metodo devuelve el grupo en el que esta esperando el pasajero
	 * 
	 */
	public  int esperoBus(int id) throws InterruptedException{
		try {
			l.lock();

			int grupo;
			if (!hayBus) {
				System.out.println("Pasajero " + id + " llega y no hay bus. Va la cola principal");
				genteEsperando++;
				condPrincipal.await();
				grupo = 0;
			} else {
				System.out.println("Pasajero " + id + " llega tarde. Va la cola secundaria");
				condSecunadario.await();
				grupo = 1;
			}

			return grupo; //comentar esta línea

		} finally {
			l.unlock();
		}
	}
	/**
	 * Una vez que ha llegado el autobús, el pasajero id que estaba
	 * esperando en el grupo i se sube al autobus
	 *
	 */
	public  void subeAutobus(int id, int i) throws InterruptedException {
		try {
			l.lock();

			if (i == 0) {
				System.out.format("%d se sube la bus.\n", id);
				genteEsperando--;
			} else {
				while (!primeracolaVacia) {
					condCambiarCola.await();
				}
				System.out.format("%d pasa a la cola primaria.\n", id);
				genteEsperando++;
				condPrincipal.await();
				genteEsperando--;
				System.out.format("%d se sube la bus.\n", id);
			}

			if (genteEsperando == 0) {
				primeracolaVacia = true;
				condCambiarCola.signalAll();
				todoSubidos = true;
				condBuses.signal();
			}

		} finally {
			l.unlock();
		}

	}
	/**
	 * El autobus llama a este metodo cuando llega a la parada
	 * Espera a que se suban todos los viajeros que han llegado antes
	 * que el, y se va
	 * 
	 */
	public  void llegoParada() throws InterruptedException {
		try {
			l.lock();

			hayBus = true;
			condPrincipal.signalAll();
			condSecunadario.signalAll();
			System.out.println("\nLlega el bus\n");

			while (!todoSubidos) {
				condBuses.await();
			}

			hayBus = false;
			todoSubidos = false;
			primeracolaVacia = false;
			System.out.println("Los pasajeros de la cola secuandaria pasar a la principal.");
			System.out.println("\nEl bus se va. \n");

		} finally {
			l.unlock();
		}
	}
}
