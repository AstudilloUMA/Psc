package Junio2021;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Convoy {

	Lock l = new ReentrantLock();
	boolean esperaConvoy = false;
	Condition cEsperaConvoy = l.newCondition();

	boolean esperaFurgoneta = true;
	Condition cEsperaFurgoneta = l.newCondition();

	boolean esperaSalenF = false;
	Condition cEsperaSalenF = l.newCondition();

	boolean esperaSalenF2 = false;
	Condition cEsperaSalenF2 = l.newCondition();

	private int lider;
	private int max;
	private int nConvoy = 0;

	public Convoy(int tam) {
		max = tam;
	}

	/**
	 * Las furgonetas se unen al convoy
	 * La primera es la lider, el resto son seguidoras
	 *
	 **/
	public int unir(int id) throws InterruptedException{
		l.lock();
		try
		{
			while (!esperaFurgoneta) cEsperaFurgoneta.await();
			nConvoy++;

			if(nConvoy == 1)
			{
				System.out.println("** Furgoneta " + id + " lidera del convoy **");
				lider = id;
			}
			else
			{
				System.out.println("Furgoneta " + id + " seguidora");

				if (nConvoy == max) {
					esperaConvoy = true;
					cEsperaConvoy.signal();
				}
			}
		}
		finally {
			l.unlock();
		}

		return lider;
	}

	/**
	 * La furgoneta lider espera a que todas las furgonetas se unan al convoy
	 * Cuando esto ocurre calcula la ruta y se pone en marcha
	 * */
	public synchronized void calcularRuta(int id) throws InterruptedException {
		l.lock();
		try
		{
			while(!esperaConvoy) cEsperaConvoy.await();
			System.out.println("** Furgoneta "+id+" lider:  ruta calculada, nos ponemos en marcha **");
		}
		finally {
			l.unlock();
		}
	}


	/**
	 * La furgoneta lider avisa al las furgonetas seguidoras que han llegado al destino y deben abandonar el convoy
	 * La furgoneta lider espera a que todas las furgonetas abandonen el convoy
	 **/
	public void destino(int id) throws InterruptedException{
		l.lock();
		try
		{
			esperaSalenF = true;
			cEsperaSalenF.signalAll();
			while(!esperaSalenF2) cEsperaSalenF2.await();
			System.out.println("** Furgoneta " + id + " lider abandona el convoy **");
		}
		finally {
			l.unlock();
		}
	}

	/**
	 * Las furgonetas seguidoras hasta que la lider avisa de que han llegado al destino
	 * y abandonan el convoy
	 **/
	public void seguirLider(int id) throws InterruptedException{
		l.lock();
		try
		{
			while(!esperaSalenF)cEsperaSalenF.await();
			nConvoy--;
			System.out.println("Furgoneta "+id+" abandona el convoy");

			if(nConvoy == 1)
			{
				esperaSalenF2 = true;
				cEsperaSalenF2.signal();
			}
		}
		finally {
			l.unlock();
		}
	}



	/**
	* Programa principal. No modificar
	**/
	public static void main(String[] args) {
		final int NUM_FURGO = 10;
		Convoy c = new Convoy(NUM_FURGO);
		Furgoneta flota[ ]= new Furgoneta[NUM_FURGO];

		for(int i = 0; i < NUM_FURGO; i++)
			flota[i] = new Furgoneta(i,c);

		for(int i = 0; i < NUM_FURGO; i++)
			flota[i].start();
	}

}
