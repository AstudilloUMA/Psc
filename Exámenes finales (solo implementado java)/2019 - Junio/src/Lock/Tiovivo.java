package Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tiovivo {

	int espacio;
	int tamMax;

	boolean finViaje = false;

	Lock l = new ReentrantLock();

	boolean puedeBajar = false;
	Condition condBajar = l.newCondition();

	boolean puedeSubir = false;
	Condition condSubir = l.newCondition();

	boolean maquinistaEsperando = false;
	Condition condMaqunista = l.newCondition();

	public Tiovivo(int espacio) {
		this.espacio = espacio;
		this.tamMax = espacio;
	}
	
	public  void subir(int id) throws InterruptedException {
		l.lock();
		try {

			while (espacio == 0 || finViaje) {
				condSubir.await();
			}

			espacio--;
			System.out.println("Entra pasajero " + id + ". Quedan " + espacio + " asientos libres.");

			if (espacio == 0) {
				condMaqunista.signal();
			}

		} finally {
			l.unlock();
		}

	}
	
	public  void bajar(int id) throws InterruptedException {
		l.lock();
		try {

			while (!finViaje) {
				condBajar.await();
			}
			espacio++;
			System.out.println("Sale pasajero " + id + ". Quedan " + espacio + " asientos libres.");

			if (espacio == tamMax) {
				finViaje = false;
				condSubir.signalAll();
			}
		} finally {
			l.unlock();
		}

	}
	
	public  void esperaLleno () throws InterruptedException {
		l.lock();
		try {

			while (espacio != 0) { condMaqunista.await(); }

		} finally {
			l.unlock();
		}
	}
	
	public  void finViaje () {
		l.lock();
		try {

			System.out.println("Fin del viaje\n");
			finViaje = true;
			condBajar.signalAll();

		} finally {
			l.unlock();
		}
	}
}

