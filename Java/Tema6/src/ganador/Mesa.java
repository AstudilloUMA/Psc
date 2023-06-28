package ganador;

import java.util.concurrent.*;
public class Mesa {

	int numJugadores;
	int numCaras, numJugadas, ganador, ultimaCara, ultimaCruz;	
	
	public Mesa(int numJugadores){
		this.numJugadores = numJugadores;
		numCaras = 0; 
		numJugadas = 0;
		ganador = numJugadores;
		ultimaCara = numJugadores; //Almacena el id del ultimo jugador en poner cara
		ultimaCruz = numJugadores;
		
		
		//TODO
		
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
		
		System.out.println("Jugador "+id+" pone "+cara);
		
		return ganador;
	}


	/** Calcula quien es el ganador de la jugada, esté método lo llama el último 
	 * jugador de cada ronda en poner su jugada.*/
	private int hayGanador() {
		int i = numJugadores;
		if(numCaras == 1)
			i = ultimaCara;
		else if(numCaras == numJugadores-1)
			i = ultimaCruz;
		return i;
	}
}
