package Junio2017;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Guarderia {

	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 *
	 */
	private int nAdultos, nBebes;

	Lock l = new ReentrantLock();
	Condition okOcupar = l.newCondition();
	Condition okSalir = l.newCondition();

	public void entraBebe(int id) throws InterruptedException{
		l.lock();
		try
		{
			while(nBebes+1 > 3*nAdultos) okOcupar.await();
			nBebes++;
			System.out.printf("Entra el bebe %d. TOTAL: %d\n",id,nBebes);
			okOcupar.signalAll();
		}
		finally {
			l.unlock();
		}
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo *
	 */
	public void saleBebe(int id) throws InterruptedException{
		l.lock();
		try
		{
			nBebes--;
			System.out.printf("Sale el bebe %d. TOTAL: %d\n",id,nBebes);
			okOcupar.signalAll();
		}
		finally {
			l.unlock();
		}

	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo *
	 */
	public void entraAdulto(int id) throws InterruptedException{
		l.lock();
		try
		{
			nAdultos++;
			System.out.printf("\tEntra el adulto %d. TOTAL: %d\n",id,nAdultos);
		}
		finally {
			l.unlock();
		}

	}

	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 *
	 */
	public void saleAdulto(int id) throws InterruptedException{
		l.lock();
		try
		{
			while(3*(nAdultos-1) < nBebes) okSalir.await();
			nAdultos--;
			System.out.printf("\tSale el adulto %d. TOTAL: %d\n",id,nAdultos);
			okSalir.signal();
		}
		finally {
			l.unlock();
		}


	}

}
