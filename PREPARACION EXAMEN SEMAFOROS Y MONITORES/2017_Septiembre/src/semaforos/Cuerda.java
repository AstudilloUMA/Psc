package semaforos;

import java.util.concurrent.Semaphore;

public class Cuerda {
	private int monosNorteSur =0;
	private int monosSurNorte = 0;
	
	private Semaphore mutex = new Semaphore(1);
	private Semaphore puedeNorteSur = new Semaphore(1);
	private Semaphore puedeSurNorte = new Semaphore(1);
	//private Semaphore cuerda = new Semaphore(1);

	/**
	 *Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 *cuerda en dirección Norte-Sur
	 *Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 *las dos condiciones de sincronización
	 *@param id del babuino que entra en la cuerda
	 *@throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		puedeNorteSur.acquire();
		monosNorteSur++;
		if(monosNorteSur == 0 || monosNorteSur == 3) {
			mutex.acquire();
		}
		System.out.println("Llega un MonoNorteSur");
		System.out.println("MONOS NS: "+monosNorteSur+" MONOS SN: "+ monosSurNorte);
		
		puedeNorteSur.release();
			
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
		puedeSurNorte.acquire();
		monosSurNorte++;
		if(monosSurNorte == 0 || monosSurNorte == 3) {
			mutex.acquire();
		}
		System.out.println ("Llega un MonoSurNorte");
		
		System.out.println("MONOS NS: "+monosNorteSur+" MONOS SN: "+ monosSurNorte);
		
		puedeNorteSur.release();
		
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public void saleDireccionNS(int id) throws InterruptedException{
		puedeNorteSur.acquire();
		monosNorteSur--;
		if(monosNorteSur == 0 ) {
			mutex.release();
			System.out.println("Va a salir el �ltimo mono");
		}
		System.out.println("Va a salir un monNorteSur");
		
		System.out.println("MONOS NS: "+monosNorteSur+" MONOS SN: "+ monosSurNorte);		
		puedeNorteSur.release();
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public void saleDireccionSN(int id) throws InterruptedException{
		puedeSurNorte.acquire();
		monosSurNorte--;
		if(monosSurNorte == 0 ) {
			mutex.release();
			System.out.println("Va a salir el �ltimo mono");
		}
		System.out.println("Va a salir un monNorteSur");
		
		System.out.println("MONOS NS: "+monosNorteSur+" MONOS SN: "+ monosSurNorte);		
		puedeSurNorte.release();
	
	}	
		
}
