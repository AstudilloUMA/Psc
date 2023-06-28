package septiembre2017_monitores;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cuerda {

	Lock l = new ReentrantLock();
	private int babuinos = 0;
	private int ns = 0;
	private int sn = 0;

	Condition okNS = l.newCondition();
	Condition okSN = l.newCondition();

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		l.lock();
		try
		{
			//Pre
			while(sn > 0) okNS.await();
			//Sc
			babuinos++;
			ns++;
			System.out.println("\t" + id + " va hacia el sur. BABUINOS: "+ ns);
			if(babuinos < 3 && sn == 0) okNS.signal();
			//Post
		}
		finally {
			l.unlock();
		}

	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionSN(int id) throws InterruptedException{
		l.lock();
		try
		{
			//Pre
			while(ns > 0) okSN.await();
			//Sc
			sn++;
			babuinos++;
			System.out.println(id + " va hacia el norte. BABUINOS: "+ sn);
			if(ns == 0 && babuinos < 3) okSN.signal();
			//Post

		}
		finally {
			l.unlock();
		}
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		l.lock();
		try
		{
			//Pre

			//Sc
			babuinos--;
			ns--;
			System.out.println("\t" + id + " sale del sur");
			//Post
			if(ns == 0 && sn < 3) okSN.signal();
			else okNS.signal();
		}
		finally {
			l.unlock();
		}
	}

	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		l.lock();
		try
		{
			//Pre

			//Scç
			babuinos--;
			sn--;
			System.out.println(id + " sale del norte");
			//Post
			if(sn == 0 && ns < 3) okNS.signal();
			else okSN.signal();
		}
		finally {
			l.unlock();
		}
	}

}
