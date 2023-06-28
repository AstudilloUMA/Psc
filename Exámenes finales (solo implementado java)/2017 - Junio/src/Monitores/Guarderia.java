package Monitores;


public class Guarderia {

	int nBebes = 0;
	int nPadres = 0;
	
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public synchronized void entraBebe(int id) throws InterruptedException{
		while (nBebes + 1 > 3 * nPadres) {
			wait();
		}
		nBebes++;
		System.out.format("Ha entrado el bebe %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public synchronized void saleBebe(int id) throws InterruptedException{
		nBebes--;
		System.out.format("Ha salido el bebe %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
		notifyAll();
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public synchronized void entraAdulto(int id) throws InterruptedException {
		nPadres++;
		System.out.format("Ha entrado el padre %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
		notifyAll();
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public synchronized void saleAdulto(int id) throws InterruptedException{
		while (nBebes > 3 * (nPadres - 1)) {
			wait();
		}
		nPadres--;
		System.out.format("Ha salido el padre %d. Hay %d bebés y %d padres\n", id, nBebes, nPadres);
	}

}
