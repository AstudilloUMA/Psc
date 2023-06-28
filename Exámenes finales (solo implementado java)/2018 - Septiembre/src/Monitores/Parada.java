package Monitores;


import java.util.Arrays;

public class Parada {
	
	boolean hayBus = false;
	boolean todoSubidos = false;

	boolean[] colaPrincipal = new boolean[100];
	boolean[] colaSecundaria = new boolean[100];

	public Parada(){
		
	}
	/**
	 * El pasajero id llama a este metodo cuando llega a la parada.
	 * Siempre tiene que esperar el siguiente autobus en uno de los
	 * dos grupos de personas que hay en la parada
	 * El metodo devuelve el grupo en el que esta esperando el pasajero
	 * 
	 */
	public synchronized int esperoBus(int id) throws InterruptedException{

		int grupo;
		if (!hayBus) {
			System.out.println("Pasajero " + id + " llega y no hay bus. Va la cola principal");
			colaPrincipal[id] = true;
			grupo = 0;
		} else {
			System.out.println("Pasajero " + id + " llega tarde. Va la cola secundaria");
			colaSecundaria[id] = true;
			grupo = 1;
		}
		
		return grupo; //comentar esta línea
	}
	/**
	 * Una vez que ha llegado el autobús, el pasajero id que estaba
	 * esperando en el grupo i se sube al autobus
	 *
	 */
	public synchronized void subeAutobus(int id, int i) throws InterruptedException {
		while (!colaPrincipal[id] || !hayBus) {
			wait();
		}
		colaPrincipal[id] = false;
		System.out.println("El pasajero " + id + " se ha montado en el bus.");

		int j = 0;
		boolean encontrado = false;
		while (j < colaPrincipal.length && !encontrado) {
			if (colaPrincipal[j]) {
				encontrado = true;
			}
			j++;
		}
		if (!encontrado) {
			todoSubidos = true;
			notifyAll();
		}
	}
	/**
	 * El autobus llama a este metodo cuando llega a la parada
	 * Espera a que se suban todos los viajeros que han llegado antes
	 * que el, y se va
	 * 
	 */
	public synchronized void llegoParada() throws InterruptedException {
		hayBus = true;
		notifyAll();
		System.out.println("Llega el bus");

		while (!todoSubidos) {
			wait();
		}

		Arrays.fill(colaPrincipal, false);

		for (int i = 0; i < colaSecundaria.length; i++) {
			colaPrincipal[i] = colaSecundaria[i];
		}

		Arrays.fill(colaSecundaria, false);
		hayBus = false;
		todoSubidos = false;
		System.out.println("Los pasajeros de la cola secuandaria pasar a la principal.");
		System.out.println("El bus se va. \n");
		notifyAll();
	}
}
