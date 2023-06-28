package Locks;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Guarderia {

	int nBebes = 0;
	int nPadres = 0;

	Lock l = new ReentrantLock();

	Condition condEntraBebe = l.newCondition();
	Condition condSalePadre = l.newCondition();
	
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException {
		try {
			l.lock();

			while (nBebes + 1 > 3 * nPadres) {
				condEntraBebe.await();
			}
			nBebes++;
			System.out.format("Ha entrado el bebe %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);

		} finally {
			l.unlock();
		}
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
		try {
			l.lock();

			nBebes--;
			System.out.format("Ha salido el bebe %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
			condSalePadre.signal();

		} finally {
			l.unlock();
		}
	}

	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException {
		try {
			l.lock();

			nPadres++;
			System.out.format("Ha entrado el padre %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
			condEntraBebe.signalAll();

		} finally {
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
		try {
			l.lock();

			while (nBebes > 3 * (nPadres - 1)) {
				condSalePadre.await();
			}
			nPadres--;
			System.out.format("Ha salido el padre %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);

		} finally {
			l.unlock();
		}
	}
}
