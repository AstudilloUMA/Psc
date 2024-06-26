package n_hebras_cascada;

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
			System.out.println(id+ " Soy el último en llegar y el primero en salir. Vamos saliendo de 1 en 1");
			
			enCita--;				
			hanLlegadoTodos.release();						
		}else {
			System.out.println(id+" llego a la cita. Hay esperando "+enCita);
			mutex.release();
			hanLlegadoTodos.acquire();
			mutex.acquire();
			enCita--;			
			if(enCita>0) {
				System.out.println(id+" salgo de la cita. Quedan por salir "+enCita);
				hanLlegadoTodos.release();
			}else {
				System.out.println(id+" soy el último en salir, cierro la puerta");
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
