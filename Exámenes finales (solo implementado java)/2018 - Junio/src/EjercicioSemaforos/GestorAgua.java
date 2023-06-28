package EjercicioSemaforos;


import java.util.Arrays;
import java.util.concurrent.*;


public class GestorAgua {

	Semaphore mutex = new Semaphore(1);
	Semaphore maxHidrogeno = new Semaphore(1);
	Semaphore maxOxigeno = new Semaphore(1);

	int hidrogeno = 0, oxigeno = 0;

	public void hListo(int id) throws InterruptedException{
		maxHidrogeno.acquire();
		mutex.acquire();

		hidrogeno++;
		System.out.println("Hay " + oxigeno + " moléculas de oxigenos y " + hidrogeno + " de hidrógeno");

		if (hidrogeno < 2) {
			maxHidrogeno.release();
		}

		if (completo()) {
			System.out.println("Hemos creado una molecula de agua.");
			System.out.println("\nEsperando para hacer una nueva molecula");
			maxOxigeno.release();
			maxHidrogeno.release();
			hidrogeno = 0;
			oxigeno = 0;
		}

		mutex.release();
	}

	public void oListo(int id) throws InterruptedException{
		maxOxigeno.acquire();
		mutex.acquire();

		oxigeno++;
		System.out.println("Hay " + oxigeno + " moléculas de oxigenos y " + hidrogeno + " de hidrógeno");

		if (oxigeno < 1) {
			maxOxigeno.release();
		}

		if (completo()) {
			System.out.println("Hemos creado una molecula de agua.");
			System.out.println("\nEsperando para hacer una nueva molecula");
			hidrogeno = 0;
			oxigeno = 0;
			maxHidrogeno.release();
			maxOxigeno.release();
		}

		mutex.release();
	}

	private boolean completo() {
		return hidrogeno == 2 && oxigeno == 1;
	}
}

