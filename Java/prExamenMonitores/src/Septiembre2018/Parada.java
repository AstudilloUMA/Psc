package Septiembre2018;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Parada {

	Lock l = new ReentrantLock();
	private boolean estaBus = false;
	Condition okSubir1 = l.newCondition();
	Condition okSubir2 = l.newCondition();

	Condition busVuelve = l.newCondition();
	private int grupo = 0;

	private int nPorSubir = 0;

	
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
			while(estaBus)
			{
				grupo = 2;
				System.out.printf("El alumno %d se une al grupo %d y se espera al autobus\n",id,grupo);
				okSubir2.await();
			}

			while(!estaBus)
			{
				grupo = 1;
				nPorSubir++;
				System.out.printf("El alumno %d se une al grupo %d y se sube al autobus\n",id,grupo);
				okSubir1.await();

			}
		}
		finally {
			l.unlock();
		}
		return grupo; //comentar esta línea
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
			nPorSubir--;
			System.out.printf("Se ha subido el alumno %d, FALTAN: %d\n",id,nPorSubir);

			if(nPorSubir == 0)
			{
				estaBus = false;
				System.out.println("El autobus se va");
				okSubir2.signalAll();
				busVuelve.signal();
			}
		}
		finally {
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
			while(estaBus) busVuelve.await();

			System.out.println("Ha llegado el bus");
			estaBus = true;
			okSubir1.signalAll();

		}
		finally {
			l.unlock();
		}
		
	}
}
