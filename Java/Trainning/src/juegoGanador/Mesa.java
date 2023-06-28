package juegoGanador;

import java.util.concurrent.*;
public class Mesa {

	int numJugadores;
	boolean jugadas[];
	int numCaras, numCruces, hanJugado, ganador;
	//TODO declara otras variables necesarias para controlar el juego
	private Semaphore mutex, esperarTodos, nuevaRonda;

	public Mesa(int numJugadores){
		this.numJugadores = numJugadores;
		ganador = numJugadores;
		jugadas = new boolean[numJugadores];
		numCaras = 0; numCruces = 0; hanJugado =0;
		mutex = new Semaphore(1, true);
		esperarTodos = new Semaphore(0,true);
		nuevaRonda = new Semaphore(1, true);
	}


	/**
	 *
	 * @param id del jugador que llama al método
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ningún jugador
	 * ha ganado y hay que repetir la partida. Si  devuelve un número menor que N, es el id del
	 * jugador ganador.
	 *
	 * Un jugador llama al método nuevaJugada cuando quiere poner
	 * el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el último, espera a que el resto de
	 * los jugadores pongan sus jugadas sobre la mesa.
	 * El último jugador comprueba si hay o no un ganador, y despierta
	 * al resto de jugadores
	 *
	 */
	public int nuevaJugada(int id,boolean cara) throws InterruptedException{
		nuevaRonda.acquire();
		//Pongo jugada - SC
		mutex.acquire();
		if(cara) {
			numCaras++;
		}else {
			numCruces++;
		}
		jugadas[id] = cara;
		hanJugado++;
		System.out.println("J"+id+" jugada "+cara);
		//si no soy el ultimo  --> bloqueo
		if(hanJugado < numJugadores) {
			nuevaRonda.release();
			mutex.release();
			esperarTodos.acquire();
			mutex.acquire();
		}
		//en otro caso --> calculo ganador
		else {
			ganador = hayGanador();
			if(ganador == numJugadores)
				System.out.println("Empate");
			else
				System.out.println("Ganador Jugador "+ganador);
			numCaras =0; numCruces = 0;
		}

		hanJugado--;
		//y despertarmos resto jugadores
		if(hanJugado>0) esperarTodos.release();
		else nuevaRonda.release();

		mutex.release();
		return ganador;
	}


	/** Calcula quien es el ganador de la jugada, esté método lo llama el último
	 * jugador de cada ronda en poner su jugada.*/
	private int hayGanador() {
		if (numCaras ==1) {
			for(int i=0; i < jugadas.length; i++) {
				if(jugadas[i] == true)
					return i;
			}
		}else if(numCruces == 1) {
			for(int i=0; i < jugadas.length; i++) {
				if(jugadas[i] == false)
					return i;
			}
		}
		return numJugadores;
	}
}
