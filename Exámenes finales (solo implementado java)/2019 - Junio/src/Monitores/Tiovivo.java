package Monitores;


public class Tiovivo {

	int espacio;
	int tamMax;

	boolean finViaje = false;

	public Tiovivo(int espacio) {
		this.espacio = espacio;
		this.tamMax = espacio;
	}
	
	public synchronized void subir(int id) throws InterruptedException {

		while (espacio == 0 || finViaje) {
			wait();
		}

		espacio--;
		System.out.println("Entra pasajero " + id + ". Quedan " + espacio + " asientos libres.");

		if (espacio == 0) {
			notifyAll();
		}
	}
	
	public synchronized void bajar(int id) throws InterruptedException {
		while (!finViaje) {
			wait();
		}
		espacio++;
		System.out.println("Sale pasajero " + id + ". Quedan " + espacio + " asientos libres.");

		if (espacio == tamMax) {
			finViaje = false;
		}
	}
	
	public synchronized void esperaLleno () throws InterruptedException {
		while (espacio != 0) { wait(); }
	}
	
	public synchronized void finViaje () {
		System.out.println("Fin del viaje\n");
		finViaje = true;
		notifyAll();
	}
}

