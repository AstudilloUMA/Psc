package ejercicio1;

import java.util.concurrent.Semaphore;

public class Mesa {
	private int ingrediente;
	private Semaphore puedeFumar = new Semaphore(0,true);
	private Semaphore puedePoner = new Semaphore(1,true);
	private volatile int ningredientes = 0;

	
		
	public Mesa() {
		ingrediente = -1;		
		//TODO
		
	}

	public  void quiereFumar(int id) throws InterruptedException {
		//TODO CS -  si el ingrediente es distinto al id del  fumador bloquea
		
		System.out.println("Fumador "+id+" empieza a fumar");
		
		
	}

	public void terminaFumar(int id) {
		//TODO
		
		System.out.println("Fumador "+id+" termina de fumar ");		
		
	}

	public void poneIngrediente(int ing) throws InterruptedException {
		//Pre
		while(ningredientes == 2) puedePoner.acquire();
		//SC

		//Post
		System.out.println("Agente falta ingrediente "+ingrediente);
	
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
