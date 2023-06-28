package cita2;

import java.util.concurrent.Semaphore;

public class Cita2 {

	private Semaphore haLlegado0; //cerrado si 0 no ha llegado
	private Semaphore haLlegado1; //cerrado si 1 no ha llegado
	
	public Cita2() {
		haLlegado0 = new Semaphore(0);
		haLlegado1 = new Semaphore(0);
	}
	
	public void esperar1() throws InterruptedException {
		System.out.println("0 Llega a la cita y espera a 1");
		haLlegado0.release();
		haLlegado1.acquire();
		
		
	}

	public void esperar0() throws InterruptedException {
		System.out.println("1 Llega a la cita y espera a 0");
		haLlegado1.release();
		haLlegado0.acquire();
		
	}

	public static void main(String[] args) {
		Cita2 cita = new Cita2();
		Hebra h0 = new Hebra(0, cita);
		Hebra h1 = new Hebra(1, cita);
		
		h0.start();
		h1.start();
		

	}

}
