package Monitores;

import java.util.concurrent.Semaphore;

public class Concurso {

	int partidasGanadas = 0;
	int partidasGanadasA = 0;
	int partidasGanadasB = 0;
	int partidasEmpatadas = 0;
	int jugadorA = 0;
	int carasA = 0;

	int jugadorB = 0;
	int carasB = 0;

	Semaphore mutex = new Semaphore(1);


	public synchronized void  tirarMoneda(int id,boolean cara) throws InterruptedException {
		if (id == 0 && jugadorA < 10) {
			System.out.println("El jugador " + id + " lanza la moneda y sale " + cara);
			jugadorA++;
			if (cara) {
				carasA++;
			}
		} else if (id == 1 && jugadorB < 10) {
			System.out.println("El jugador " + id + " lanza la moneda y sale " + cara);
			jugadorB++;
			if (cara) {
				carasB++;
			}
		} else {
			System.out.println("El jugador " + id + " no ha podido lanzar la moneda");
		}
		System.out.println("El jugador A ha tirado " + jugadorA + " con " + carasA + " caras. " + "El jugador B ha tirado " + jugadorB + " con " + carasB + " caras. " +
				"Se han jugado " + partidasGanadas + " partidas. El jugador A ha ganado " + partidasGanadasA + ", el jugador B " + partidasGanadasB + ", y " +
				partidasEmpatadas + " partidas empatadas\n");

		if (jugadorA == 10 && jugadorB == 10) {
			partidasGanadas++;
			if (carasA >  carasB) {
				partidasGanadasA++;
			} else if (carasA == carasB) {
				partidasEmpatadas++;
			} else {
				partidasGanadasB++;
			}
			jugadorA = 0;
			jugadorB = 0;
			carasA = 0;
			carasB = 0;
		}
	}

	public  boolean concursoTerminado() {
		if (partidasGanadas == 3) {
			return true;
		}
		else {
			return false;
		}

	}
}