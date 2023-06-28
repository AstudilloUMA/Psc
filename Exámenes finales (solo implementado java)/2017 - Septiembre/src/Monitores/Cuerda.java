package Monitores;


public class Cuerda {

	int NSTam = 0;
	int SNTam = 0;
	int monosTotales = 50;


	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public synchronized void entraDireccionNS(int id) throws InterruptedException{
		while (SNTam > 0 || NSTam == 3) {
			wait();
		}
		System.out.format("Mono %d se monta. Hay %d monos en -> y %d monos en <- dirección.", id, NSTam, SNTam);
		NSTam++;
	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public synchronized void entraDireccionSN(int id) throws InterruptedException{
		while (NSTam > 0 || SNTam == 3) {
			wait();
		}
		SNTam++;
		System.out.format("\tMono %d se monta. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);

	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public synchronized void saleDireccionNS(int id) throws InterruptedException {
		NSTam--;
		monosTotales--;
		System.out.format("Mono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);
		System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);
		notifyAll();
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public synchronized void saleDireccionSN(int id) throws InterruptedException{
		SNTam--;
		monosTotales--;
		System.out.format("\tMono %d se baja. Hay %d monos en -> y %d monos en <- dirección.\n", id, NSTam, SNTam);
		System.out.printf("Faltan %d monos por viajar.\n\n", monosTotales);
		notifyAll();
	}	
		
}
