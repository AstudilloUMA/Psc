package septiembre2016;

import java.util.concurrent.*;
public class Mesa {

	private int jugadas;
	private int numJugadores;
	private boolean tiradas[];
	private int numCaras;
	private int ganador;
	Semaphore mutex = new Semaphore(1);
	Semaphore hayGanador = new Semaphore(0);
	Semaphore nuevaRonda = new Semaphore(1);


	public Mesa(int N){
		numJugadores = N;
		tiradas = new boolean[N];
	}
	
	
	
	/**
	 * 
	 * @param id del jugador que llama al m�todo
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ning�n jugador 
	 * ha ganado y hay que repetir la partida. Si  devuelve un n�mero menor que N, es el id del
	 * jugador ganador.
	 * 
	 * Un jugador llama al m�todo nuevaJugada cuando quiere poner
	 * el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el �ltimo, espera a que el resto de 
	 * los jugadores pongan sus jugadas sobre la mesa.
	 * El �ltimo jugador comprueba si hay o no un ganador, y despierta
	 * al resto de jugadores
	 *  
	 */
	public int nuevaJugada(int id,boolean cara) throws InterruptedException{
		nuevaRonda.acquire();
		//Pre
		mutex.acquire();
		//Sc
		if(cara) numCaras++;
		tiradas[id] = cara;
		jugadas++;
		System.out.println(id + " ha sacado " + cara);
		//Post
		if(jugadas < numJugadores)
		{
			nuevaRonda.release();
			mutex.release();
			hayGanador.acquire();
			mutex.acquire();
		}
		else {
			ganador = buscarGanador();
			if(ganador == numJugadores) System.out.println("\tHAY EMPATE");
			else System.out.println("\tEl ganador es " + id);
			numCaras = 0;
		}
		jugadas--;
		if(jugadas > 0) hayGanador.release();
		else nuevaRonda.release();
		mutex.release();
		return ganador;
	}

	private int buscarGanador()
	{
		if(numCaras == 1)
		{
			for(int i = 0; i < numJugadores; i++)
			{
				if(tiradas[i] == true) return i;
			}
		}
		else if (numCaras == numJugadores-1) {
			for (int i = 0; i < numJugadores; i++)
			{
				if (tiradas[i] == false) return i;
			}
		}
		return numJugadores;
	}
}
