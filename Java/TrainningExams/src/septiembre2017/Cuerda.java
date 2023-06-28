package septiembre2017;


import java.util.concurrent.Semaphore;

public class Cuerda {

	private int ns, sn;
	private boolean irN = false;
	private boolean irS = false;

	Semaphore mutex = new Semaphore(1);
	Semaphore mutex2 = new Semaphore(1);
	Semaphore mutex3 = new Semaphore(1);
	Semaphore haciaSur = new Semaphore(1);
	Semaphore haciaNorte = new Semaphore(1);

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		//Pre
		haciaSur.acquire();
		if(ns == 0) mutex.acquire();
		mutex2.acquire();
		//Sc
		ns++;
		System.out.println("Entra " + id + " hacia sur. BABUINOS: " + ns);
		//Post
		if(ns < 3)
		{
			haciaSur.release();
		}
		mutex2.release();
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
		//Pre
		haciaNorte.acquire();
		if(sn == 0) mutex.acquire();
		mutex3.acquire();
		//Sc
		sn++;
		System.out.println("\tEntra " + id + " hacia norte. BABUINOS: " + sn);
		//Post
		if(sn < 3)
		{
			haciaNorte.release();
		}
		mutex3.release();
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
			//Pre
			mutex2.acquire();
			//Sc
			ns--;
			System.out.println("Ha llegado " + id + ". BABUINOS: " + ns);
			//Post
			if(ns == 0)
			{
				mutex.release();
				haciaSur.release();
			}
			mutex2.release();
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		//Pre
		mutex3.acquire();
		//Sc
		sn--;
		System.out.println("\tHa llegado " + id + ". BABUINOS: " + sn);
		//Post
		if(sn == 0)
		{
			mutex.release();
			haciaNorte.release();
		}
		mutex3.release();
	}	
		
}
