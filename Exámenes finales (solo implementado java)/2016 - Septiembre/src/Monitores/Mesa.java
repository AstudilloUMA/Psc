package Monitores;

import java.util.Arrays;

public class Mesa {

	int numeroJugadores;
	boolean[] jugadores;
	boolean[] saleCara;
	int monedasJugadas;
	int resultado;

	
	public Mesa(int N){
		numeroJugadores = N;
		jugadores = new boolean[N];
		saleCara = new boolean[N];
		monedasJugadas = 0;
		resultado = N;
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
	public synchronized int nuevaJugada(int id,boolean cara) throws InterruptedException {

		resultado = numeroJugadores;

		System.out.format("La persona %d tira y le sale %b\n ", id, cara);

		jugadores[id] = true;
		saleCara[id] = cara;
		monedasJugadas++;

		if (monedasJugadas == numeroJugadores) {
			System.out.print("Todos han tirado y ha quedado así: ");
			System.out.println(Arrays.toString(saleCara));

			int verdadero = 0, falsos = 0;
			for (int i = 0; i < numeroJugadores; i++) {
				if (saleCara[i]) {
					verdadero++;
				} else {
					falsos++;
				}
			}

			boolean encontrado = false;
			int i = 0;
			if (verdadero == 1) {
				while (!encontrado && i < numeroJugadores) {
					if (saleCara[i]) {
						encontrado = true;
						resultado = i;
					}
					i++;
				}
			} else if (falsos == 1) {
				while (!encontrado && i < numeroJugadores) {
					if (!saleCara[i]) {
						encontrado = true;
						resultado = i;
					}
					i++;
				}
			}

			Arrays.fill(jugadores, false);
			Arrays.fill(saleCara, false);
			monedasJugadas = 0;
			notifyAll();

			if (resultado == numeroJugadores) {
				System.out.println("EMPATE\n");
			} else {
				System.out.format("Ha ganado %d\n", resultado);
			}
		}

		while (jugadores[id]) {
			wait();
		}
		return resultado;
	}
}
