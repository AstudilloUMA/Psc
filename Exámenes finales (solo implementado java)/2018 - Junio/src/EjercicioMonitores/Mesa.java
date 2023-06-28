package EjercicioMonitores;

public class Mesa {

	int racion = 8; //Los 8 trozos de una pizza
	boolean hayRacion = true;
	boolean hanLlamado = false;


	/**
	 * 
	 * @param id
	 * El estudiante id quiere una ración de pizza. 
	 * Si hay una ración la coge y sigue estudiante.
	 * Si no hay y es el primero que se da cuenta de que la mesa está vacía
	 * llama al pizzero y
	 * espera hasta que traiga una nueva pizza. Cuando el pizzero trae la pizza
	 * espera hasta que el estudiante que le ha llamado le pague.
	 * Si no hay pizza y no es el primer que se da cuenta de que la mesa está vacía
	 * espera hasta que haya un trozo para él.
	 * @throws InterruptedException 
	 * 
	 */
	public synchronized void nuevaRacion(int id) throws InterruptedException {

		System.out.println("Persona " + id + " quiere comer");

		while (!hayRacion) {
			wait();
		}

		if (racion == 0) {
			System.out.format("Estudiante %d ha llamado al pizzero\n", id);
			hayRacion = false;
			hanLlamado = true;
			notifyAll();
		}

		racion--;
		System.out.println("Persona " + id + " ha comido. Quedan " + racion);

	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public synchronized void nuevoCliente() throws InterruptedException {
		while (!hanLlamado) {
			wait();
		}
		System.out.println("Cliente nuevo");
	}
	

	/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public synchronized void nuevaPizza() throws InterruptedException{
		racion = 8;
		hanLlamado = false;
		hayRacion = true;
		System.out.println("Pizza entragada\n");
		notifyAll();
	}
}
