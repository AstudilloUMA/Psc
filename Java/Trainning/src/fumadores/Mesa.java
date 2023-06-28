package fumadores;

import java.util.concurrent.Semaphore;

public class Mesa {
	private int ingrediente;
	private Semaphore puedeFumar[] = new Semaphore[3];
	private Semaphore puedePoner = new Semaphore(1);
	
		
	public Mesa() {
		ingrediente = -1;
		for(int i = 0; i < 3; i++) puedeFumar[i] = new Semaphore(0);
	}

	public  void quiereFumar(int id) throws InterruptedException {
		//TODO CS -  si el ingrediente es distinto al id del  fumador bloquea
		puedeFumar[id].acquire();
		System.out.println("Fumador "+id+" empieza a fumar");
		ingrediente = -1;
	}

	public void terminaFumar(int id) throws InterruptedException {
		System.out.println("Fumador "+id+" termina de fumar ");
		puedePoner.release();
	}

	public void poneIngrediente(int ing) throws InterruptedException {
		puedePoner.acquire();
		ingrediente = ing;
		System.out.println("\tAgente falta ingrediente "+ingrediente);
		puedeFumar[ingrediente].release();
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
