package septiembre2016_monitores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {

	Lock l = new ReentrantLock();

	Condition okJugar = l.newCondition();
	Condition okGanador = l.newCondition();

	private int jugadas;
	private int numJugadores;
	private boolean tiradas[];
	private int numCaras;
	private int ganador;

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
		l.lock();
		try
		{
			//Pre
			if(jugadas == numJugadores) okJugar.await();
			//SC
			if(cara) numCaras++;
			tiradas[id] = cara;
			jugadas++;
			System.out.println(id + " saca " + cara);
			//Post
			if(jugadas < numJugadores) okGanador.await();
			else
			{
				ganador = buscarGanador();
				if(ganador == numJugadores) System.out.println("\t EMPATE");
				else System.out.println("\t GANA " + ganador);
				jugadas = 0;
				numCaras = 0;
				okGanador.signalAll();
				okJugar.signal();
			}
		} finally {
			l.unlock();
		}
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
