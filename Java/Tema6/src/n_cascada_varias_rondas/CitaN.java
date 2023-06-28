package n_cascada_varias_rondas;

import java.util.concurrent.Semaphore;

public class  CitaN {

	int enCita;
	int totalCita;
	private Semaphore hanLlegadoTodos; //cerrado hasta que lleguen totalCita
	private Semaphore mutex;
	private Semaphore hanSalidoTodos;
	
	public CitaN(int n) {
		enCita = 0;
		totalCita = n;
		hanLlegadoTodos = new Semaphore(0);
		hanSalidoTodos = new Semaphore(1);
		mutex = new Semaphore(1);
		
	}
	
	public void esperarATodos(int id) throws InterruptedException {
		hanSalidoTodos.acquire();
		mutex.acquire();
		
		enCita++;
		
		if(enCita == totalCita) {
			System.out.println(id+ " Soy el último en llegar y el primero en salir. Vamos saliendo de 1 en 1");
			
			enCita--;			
			hanLlegadoTodos.release();			
		}else {
			System.out.println(id+" llego a la cita. Hay esperando "+enCita);
			mutex.release();
			hanSalidoTodos.release();
			hanLlegadoTodos.acquire();
			mutex.acquire();
			enCita--;			
			if(enCita>0) {
				System.out.println(id+" salgo de la cita. Quedan por salir "+enCita);
				hanLlegadoTodos.release();
			}else {
				System.out.println(id+" soy el último en salir, puede empezar otra ronda");
				hanSalidoTodos.release();
			}
				
		}
		
		mutex.release();
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
