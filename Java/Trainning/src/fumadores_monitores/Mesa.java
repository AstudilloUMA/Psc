package fumadores_monitores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
	private int ingrediente;
	boolean fumando = false;
	
	public Mesa() {
		ingrediente = -1;
	}

	public synchronized void quiereFumar(int id) throws InterruptedException {
		//Pre
		while(ingrediente != id) wait();
		//SC
		fumando = true;
		ingrediente = -1;
		System.out.println("El fumador " + id + " esta fumando");
		//Post
	}

	public synchronized void terminaFumar(int id) {
		//Pre

		//SC
		System.out.println("\tEl fumador " + id + " termina de fumar");
		//Post
		fumando = false;
		notifyAll();
	}

	public synchronized void poneIngrediente(int ing) throws InterruptedException {
		//Pre
		while(fumando || ingrediente > -1) wait();
		//Sc
		System.out.println("\t\tFalta el ingrediente " + ing);
		ingrediente = ing;
		//Post
		notifyAll();
	}

	public static void main(String[] args) {
		Mesa m = new Mesa();
		Agente ag = new Agente(m);
		Fumador  fumadores[] = new Fumador[3];  // 0 - Tabaco || 1 - Papel || 2 - Cerilla
		for(int i = 0 ; i < 3; i++) 
			fumadores[i] = new Fumador(i, m);
		ag.start();
		for(int i = 0 ; i < 3; i++) 
			fumadores[i].start();
		
	}

}
