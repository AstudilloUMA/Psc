package cita_n;

import java.util.concurrent.Semaphore;

public class CitaN {

	int enCita;
	int totalCita;
	private Semaphore hanLlegadoTodos; //cerrado hasta que lleguen totalCita
	private Semaphore mutex;
	
	public CitaN(int n) {
		enCita = 0;
		totalCita = n;
		hanLlegadoTodos = new Semaphore(0);
		mutex = new Semaphore(1);
		
	}
	
	public void esperarATodos(int id) throws InterruptedException {
		mutex.acquire();
		
		enCita++;
		
		if(enCita == totalCita) {
			System.out.println(id+ " Soy el último en llegar. La cita puede terminar");
			enCita=0;
			/* En este punto hay totalCita-1 hebras bloqueadas. 
			 * Si hago justo esos release se van despertando y el semáforo nunca vale 1 o más de 1
			 */ 
			for(int i=0; i< totalCita-1; i++) {
				hanLlegadoTodos.release();
			}
			mutex.release();
		}else {
			System.out.println(id+" llego a la cita. Hay esperando "+enCita);
			mutex.release();
			hanLlegadoTodos.acquire();
		}		
	}

	

	public static void main(String[] args) {
		final int N = 5;
		CitaN cita = new CitaN(N);
		Hebra citados[] = new Hebra[5];
		
		for (int i = 0; i < N; i++) {
			citados[i] = new Hebra(i, cita);
		}
		for (int i = 0; i < N; i++) {
			citados[i].start();
		}
	}
}
