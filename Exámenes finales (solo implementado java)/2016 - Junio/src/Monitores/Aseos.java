package Monitores;


public class Aseos {

	int personasDentro = 0;
	boolean quierenLimpiar = true;
	boolean hanDejadoPasarAAlguien = true;  //Si no pongo esto los limpiadores van a parecer los de la uma antes de la pandemia
	
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 * 
	 */
	public synchronized void entroAseo(int id) throws InterruptedException {
		while (quierenLimpiar) {
			wait();
		}
		personasDentro++;
		hanDejadoPasarAAlguien = true;
		System.out.format("Entra la persona %d. Hay %d personas dentro.\n", id, personasDentro);
		notifyAll();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 */
	public synchronized void salgoAseo(int id){
		personasDentro--;
		System.out.format("Sale la persona %d. Hay %d personas dentro.\n", id, personasDentro);
		if (personasDentro == 0) {
			notifyAll();
		}
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 * 
	 */
    public synchronized void entraEquipoLimpieza() throws InterruptedException {

    	while (!hanDejadoPasarAAlguien) {
    		wait();
		}
		System.out.println("\tLos limpiadores quieren entrar.");
    	quierenLimpiar = true;
		while (personasDentro != 0 ) {
			wait();
		}
		System.out.println("\tLos limpiadores entran.");
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
	 * 
	 * 
	 */
    public synchronized void saleEquipoLimpieza(){
    	quierenLimpiar = false;
		System.out.println("\tLos limpiadores salen.\n");
		hanDejadoPasarAAlguien = false;
		notifyAll();
	}
}
