package fumadores;

import java.util.concurrent.Semaphore;

public class Mesa {
	private int ingrediente;
	//TODO
	
	public Mesa() {
		ingrediente = -1;		
		//TODO		
	}

	public  void quiereFumar(int id)  {
		//TODO
	}

	public void terminaFumar(int id) {
		//TODO		
	}

	public void poneIngrediente(int ing) {
		//TODO
		
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
