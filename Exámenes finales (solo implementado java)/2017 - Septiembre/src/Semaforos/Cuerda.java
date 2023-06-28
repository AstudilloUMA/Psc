package Semaforos;


import java.util.concurrent.Semaphore;

public class Cuerda {

	int NSTam = 0;
	int SNTam = 0;
	int monosTotales = 50;

	Semaphore mutex = new Semaphore(1); //NS
	Semaphore mutex2 = new Semaphore(1); //SN
	Semaphore NS = new Semaphore(1);
	Semaphore SN = new Semaphore(1);
	Semaphore cuerda = new Semaphore(1);


	public void entraDireccionNS(int id) throws InterruptedException {
		NS.acquire();
		if (NSTam == 0) {
			cuerda.acquire();
		}
		mutex.acquire();
		NSTam++;
		System.out.format("Mono %d se monta. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

		if (NSTam < 3) {
			NS.release();
		}
		mutex.release();
	}

	public void entraDireccionSN(int id) throws InterruptedException {
		SN.acquire();
		if (SNTam == 0) {
			cuerda.acquire();
		}
		mutex2.acquire();


		SNTam++;
		System.out.format("Mono %d se monta. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

		if (SNTam < 3) {
			SN.release();
		}
		mutex2.release();
	}

	public void saleDireccionNS(int id) throws InterruptedException {
		mutex.acquire();
		NSTam--;
		monosTotales--;
		System.out.format("Mono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);
		if (NSTam == 0) {
			System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);
			cuerda.release();
			NS.release();
		}
		mutex.release();
	}

	public void saleDireccionSN(int id) throws InterruptedException{
		mutex2.acquire();
		SNTam--;
		monosTotales--;
		System.out.format("Mono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

		if (SNTam == 0) {
			System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);
			cuerda.release();
			SN.release();
		}
		mutex2.release();
	}


	/*public void entraDireccionNS(int id) throws InterruptedException {
		NS.acquire();
		if (NSTam == 0) {
			cuerda.acquire();
		}
		mutex.acquire();

		NSTam++;
		System.out.format("Mono %d se monta. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

		if (NSTam < 3) {
			NS.release();
		}
		mutex.release();
	}

	public void entraDireccionSN(int id) throws InterruptedException {
		SN.acquire();
		if (SNTam == 0) {
			cuerda.acquire();
		}
		mutex2.acquire();

		SNTam++;
		System.out.format("Mono %d se monta. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

		if (SNTam < 3) {
			SN.release();
		}
		mutex2.release();
	}

	public void saleDireccionNS(int id) throws InterruptedException {
		mutex.acquire();

		NSTam--;
		monosTotales--;
		System.out.format("Mono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

		if (NSTam == 0) {
			System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);
			cuerda.release();
			NS.release();
		}
		mutex.release();
	}

	public void saleDireccionSN(int id) throws InterruptedException{
		mutex2.acquire();

		SNTam--;
		monosTotales--;
		System.out.format("Mono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

		if (SNTam == 0) {
			System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);
			cuerda.release();
			SN.release();
		}

		mutex2.release();
	}*/
}



/*
public class Cuerda {

	int NSTam = 0;
	int SNTam = 0;
	int monosTotales = 50;

	Semaphore mutex = new Semaphore(1); //NS
	Semaphore mutex2 = new Semaphore(1); //SN
	Semaphore NS = new Semaphore(1);
	Semaphore SN = new Semaphore(1);
	Semaphore cuerda = new Semaphore(1);


	*/
/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 *//*

	public void entraDireccionNS(int id) throws InterruptedException {
		NS.acquire();
		mutex.acquire();

		NSTam++;
		if (NSTam == 1) cuerda.acquire();

		if (NSTam < 3){
			NS.release();
		}

		System.out.format("Mono %d se monta. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);
		mutex.release();
	}
	*/
/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 *//*

	public void entraDireccionSN(int id) throws InterruptedException {
		SN.acquire();
		mutex2.acquire();

		if (SNTam == 1) cuerda.acquire();
		if (SNTam < 3) {
			SN.release();
		}
		SNTam++;
		System.out.format("Mono %d se monta. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);
		mutex2.release();
		SN.release();
	}
	*/
/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 *//*

	public void saleDireccionNS(int id) throws InterruptedException {
		mutex.acquire();
		NSTam--;
		monosTotales--;
		System.out.format("Mono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);
		System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);

		if (NSTam == 0) {
			cuerda.release();
			NS.release();
		}
		mutex.release();
	}

	*/
/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 *//*

	public void saleDireccionSN(int id) throws InterruptedException{
		mutex2.acquire();
		SNTam--;
		monosTotales--;
		System.out.format("Mono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);
		System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);

		if (SNTam == 0) {
			cuerda.release();
			SN.release();
		}
		mutex2.release();
	}
}

*/

