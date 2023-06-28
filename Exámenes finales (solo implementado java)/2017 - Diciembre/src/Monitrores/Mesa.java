package Monitrores;

import java.util.Arrays;

public class Mesa {
    //0 - piedra, 1 - papel, 2 - tijeras

	int personasQueHanJugado = 0;
	int jugadaGanadora = -1;
	int[] loQueSeSaca = new int[3];

	boolean[] personaQueHanJugado = new boolean[3];

	/**
	 * 
	 * @param jug jugador que llama al m�todo (0,1,2)
	 * @param juego jugada del jugador (0-piedra,1-papel, 2-tijeras)
	 * @return  si ha habido un ganador en esta jugada se devuelve 
	 *          la jugada ganadora
	 *         o -1, si no ha habido ganador
	 * @throws InterruptedException
	 * 
	 * El jugador que llama a este m�todo muestra su jugada, y espera a que 
	 * est�n la de los otros dos. 
	 * Hay dos condiciones de sincronizaci�n
	 * CS1- Un jugador espera en el m�todo hasta que est�n las tres jugadas
	 * CS2- Un jugador tiene que esperar a que finalice la jugada anterior para
	 *     empezar la siguiente
	 * 
	 */
	public synchronized int nuevaJugada(int jug,int juego) throws InterruptedException {

		while (personasQueHanJugado >= 3 || personaQueHanJugado[jug]) {
			wait();
		}
		personasQueHanJugado++;
		System.out.println("El jugador " + jug + " a sacado " + juego + ". Han jugado " + personasQueHanJugado + " personas.");
		loQueSeSaca[juego] ++;
		personaQueHanJugado[jug] = true;

		if (personasQueHanJugado == 3) {

			if (loQueSeSaca[0] == 2 && loQueSeSaca[1] == 1) {
				personasQueHanJugado = 1;
			} else if (loQueSeSaca[1] == 2 && loQueSeSaca[2] == 1) {
				personasQueHanJugado = 2;
			} else if (loQueSeSaca[2] == 2 && loQueSeSaca[0] == 1) {
				personasQueHanJugado = 0;
			} else {
				personasQueHanJugado = - 1;
			}
			System.out.println("Ha ganado " + personasQueHanJugado+ "\n");
			personasQueHanJugado = 0;
			Arrays.fill(personaQueHanJugado, false);
			Arrays.fill(loQueSeSaca, 0);
			notifyAll();
		}
		return jugadaGanadora;
	}
}
