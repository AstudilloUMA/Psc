package junio2017_monitores;


import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Guarderia {

	Lock l = new ReentrantLock();

	private int bebes; //numero de bebes
	private int adultos; //numero de adultos

	Condition okEntrar = l.newCondition();
	Condition okSalir = l.newCondition();


	//CS-1- bebe no puede entrar si al entrar no se cumple bebes <= 3xadultos
	//CS-2- un adulto no puede salir a noo ser que al salir se cumpla bebes <= 3xadultos

	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		l.lock();
		try
		{
			//Pre
			while(bebes >= 3*adultos) okEntrar.await();
			//Sc
			bebes++;
			System.out.println("Entra bebe " + id + ".\tBEBES: " + bebes + "\tADULTOS: " + adultos);
			//Post
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
		try {
			//Pre

			//Sc
			bebes--;
			System.out.println("Sale bebe " + id + ".\tBEBES: " + bebes + "\tADULTOS: " + adultos);
			//Post
			okSalir.signalAll();
		} finally {
			l.unlock();
		}
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		l.lock();
		try {
			//Pre

			//Sc
			adultos++;
			System.out.println("\tEntra adulto " + id + ".\tBEBES: " + bebes + "\tADULTOS: " + adultos);
			//Post
			okEntrar.signalAll();
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
		l.lock();
		try {
			//Pre
			while(bebes > 3*(adultos-1)) okSalir.await();
			//Sc
			adultos--;
			System.out.println("\tSale adulto " + id + ".\tBEBES: " + bebes + "\tADULTOS: " + adultos);
			//Post

		} finally {
			l.unlock();
		}

	}

}
