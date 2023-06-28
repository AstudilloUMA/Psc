package septiembre2018_monitores;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Parada {

	Lock l = new ReentrantLock();


	Condition principal = l.newCondition();
	Condition secundario = l.newCondition();
	Condition esperaBus = l.newCondition();

	private int esperando = 0;
	private int esperandoPrincipal = 0;
	private int esperandoSecundario = 0;
	private int pasajeros = 0;
	private boolean estaBus = false;

	public Parada(){
		
	}
	/**
	 * El pasajero id llama a este metodo cuando llega a la parada.
	 * Siempre tiene que esperar el siguiente autobus en uno de los
	 * dos grupos de personas que hay en la parada
	 * El metodo devuelve el grupo en el que esta esperando el pasajero
	 * 
	 */
	public int esperoBus(int id) throws InterruptedException{
		l.lock();
		try
		{
			if(estaBus)
			{
				esperandoSecundario++;
				System.out.println("\t\tEl pasajero " + id + " espera en el grupo 2. ESPERANDO: " + esperandoSecundario);
			}
			else
			{
				esperandoPrincipal++;
				System.out.println("\tEl pasajero " + id + " espera en el grupo 1. ESPERANDO: " + esperandoPrincipal);
			}
			//Pre
			while (estaBus) secundario.await();
			esperando++;
			while (!estaBus) principal.await();
			//Sc
			//Post
		} finally {
			l.unlock();
		}
		return 1;
	}
	/**
	 * Una vez que ha llegado el autobús, el pasajero id que estaba
	 * esperando en el grupo i se sube al autobus
	 *
	 */
	public void subeAutobus(int id,int i){
		l.lock();
		try
		{
			esperando--;
			pasajeros++;
			System.out.println("El pasajero " + id + " se sube al autobus. PASAJEROS: " + pasajeros);
			if(esperando == 0) esperaBus.signal();
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
	public void llegoParada() throws InterruptedException{
		l.lock();
		try
		{
			estaBus = true;
			principal.signalAll();
			while(esperando > 0) esperaBus.await();
			System.out.println("\t\t\tEL AUTOBUS SE VA");
			esperandoSecundario = 0;
			esperandoPrincipal = 0;
			secundario.signalAll();
			estaBus = false;
			pasajeros = 0;
		} finally {
			l.unlock();
		}
	}
}
