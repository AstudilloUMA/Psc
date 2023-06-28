package Semaforos;


import java.util.concurrent.Semaphore;

public class Guarderia {

	private int nBebes=0;
	private int esperandoB=0;
	private int esperandoA=0;
	private int nAdulto=0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore entraBebe = new Semaphore(0);
	private Semaphore saleAdulto = new Semaphore(0);


	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 *
	 */
	public void entraBebe(int id) throws InterruptedException{
		mutex.acquire();
		if((nBebes+1)>=3*nAdulto) {
			System.out.println("el bebe " + id +" espera para poder entrar");
			esperandoB++;
			mutex.release();
			entraBebe.acquire();
			mutex.acquire();

		}
		System.out.println("el bebe " + id +" ha entrado a la guarderia. Hay " + nBebes + " bebes");
		nBebes++;
		mutex.release();
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo *
	 */
	public void saleBebe(int id) throws InterruptedException{
		mutex.acquire();
		nBebes--;
		System.out.println("el bebe " + id +" sale de la guarderia. Hay " + nBebes + " bebes");
		if(esperandoB>0 && nBebes>=3*nAdulto)entraBebe.release();
		if(esperandoA>0 && nBebes>=3*nAdulto)saleAdulto.release();
		mutex.release();
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo *
	 */
	public void entraAdulto(int id) throws InterruptedException{
		mutex.acquire();
		nAdulto++;
		System.out.println("el adulto " + id +" entra de la guarderia. Hay " + nAdulto + " adultos");
		if(esperandoB>0 && nBebes>=3*nAdulto)entraBebe.release();
		if(esperandoA>0 && nBebes>=3*nAdulto)saleAdulto.release();
		mutex.release();
	}

	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 *
	 */
	public void saleAdulto(int id) throws InterruptedException{
		mutex.acquire();
		if(nBebes>=3*(nAdulto-1)) {
			esperandoA++;
			System.out.println("el adulto " + id +" espera para poder salir");
			mutex.release();
			saleAdulto.acquire();
			mutex.acquire();
		}

		nAdulto--;
		System.out.println("el adulto " + id +" sale de la guarderia. Hay " + nAdulto + " adultos");
		mutex.release();

	}

}


/*
public class Guarderia {

	Semaphore mutex = new Semaphore(1); //nBebes

	Semaphore puedeEntrarBebe = new Semaphore(0);
	Semaphore puedeSalirPadre = new Semaphore(1);

	int nBebes = 0;
	int nPadres = 0;

	int bebesqQuierenEntrar = 0;
	int padresQueQuierenSalir = 0;


	public void entraBebe(int id) throws InterruptedException{
		bebesqQuierenEntrar++;
		puedeEntrarBebe.acquire();
		bebesqQuierenEntrar--;
		mutex.acquire();
		nBebes++;
		System.out.format("Ha entrado el bebe %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);

		if (nBebes < nPadres * 3 ) {
			puedeEntrarBebe.release();
		}
		mutex.release();
	}



	public void saleBebe(int id) throws InterruptedException{
		mutex.acquire();

		nBebes--;
		System.out.format("Ha salido el bebe %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
		if (nBebes <= nPadres - 1) {
			puedeSalirPadre.release();
		}
		mutex.release();
	}


	public void entraAdulto(int id) throws InterruptedException {

		mutex.acquire();

		nPadres++;
		System.out.format("Ha entrado el padre %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
		if (nBebes <= 3 * (nPadres - 1)) {// || puedeEntrarBebe.availablePermits() == 0) {
			puedeEntrarBebe.release();
		}

		mutex.release();
	}


	public void saleAdulto(int id) throws InterruptedException {
		puedeSalirPadre.acquire();
		mutex.acquire();
		nPadres--;
		System.out.format("Ha salido el padre %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
		mutex.release();
	}
}
*/
