package esqueleto;

import java.util.concurrent.Semaphore;

public class Concurso {
	private Semaphore mutex = new Semaphore(1);
	private Semaphore[] haTerminado = new Semaphore[2];

	private int[] wins = new int[2];

	private int[] tiradas = new int[2];

	private int[] caras = new int[2];

	public Concurso() {
		for (int i = 0; i < 2; i++) {
			haTerminado[i] = new Semaphore(1);
		}
	}

	public void tirarMoneda(int id, boolean cara) throws InterruptedException {
		haTerminado[id].acquire();
		mutex.acquire();
		tiradas[id]++;
		if (cara) {
			caras[id]++;
		}

		if (tiradas[id] < 10)
			haTerminado[id].release(); // Vuelvo a comparar si tengo que liberar el
										// sem�foro, mirando si tengo m�s tiradas disponibles
		if (tiradas[id] == 10 && tiradas[1 - id] == 10) {// Comparo si los dos jugadores han llegado a las diez tiradas

			if (caras[id] > caras[1 - id]) {
				wins[id]++;
				System.out.println("Ha ganado el jugador " + id + "con estas caras: " + caras[id]);
			} else if (caras[1 - id] > caras[id]) {
				wins[1 - id]++;
				System.out.println("Ha ganado el jugador " + (1 - id) + " con estas caras: " + caras[1 - id]);
			} else {
				System.out.println("Los jugadores han empatado");
			} // Restablezco los valores por defecto, porque tiene que empezar otra ronda
				// nueva
			tiradas[id] = 0;
			tiradas[1 - id] = 0;
			caras[id] = 0;
			caras[1 - id] = 0;
			// Libero los dos semaforos para que las hebras no se bloqueen
			haTerminado[0].release();
			haTerminado[1].release();

		}

		mutex.release();

	}

	public boolean concursoTerminado() throws InterruptedException {
		boolean res = false;
		mutex.acquire();
		if (wins[0] == 3) {
			System.out.println("Gan� el jugador 0");
			res = true;

		} else if (wins[1] == 3) {
			System.out.println("Gan� el jugador 1");
			res = true;

		}
		mutex.release();

		return res; // borrar
	}
}