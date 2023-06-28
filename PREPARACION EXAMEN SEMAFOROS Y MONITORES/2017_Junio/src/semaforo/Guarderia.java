package semaforo;

import java.util.concurrent.Semaphore;

public class Guarderia {
	private int numBebes = 0 ;
	private int numAdultos = 0 ;
	
	private Semaphore mutex = new Semaphore(1);
	
	private Semaphore entraBebe = new Semaphore(0);
	
	private Semaphore saleAdulto= new Semaphore(1);
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 */
	public void entraBebe(int id) throws InterruptedException{
		entraBebe.acquire();
		mutex.acquire();
		if(numBebes+1<=numAdultos*3) {
			numBebes++;
			System.out.println("Ha entrado una bbsita");
			System.out.println("ADULTOS: "+numAdultos+", BEBES: "+numBebes);
			entraBebe.release();
		}
		
		mutex.release();
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
			mutex.acquire();
			if(numBebes>=1) {
				numBebes--;
				System.out.println("Sale un bebe");
			}
			System.out.println("ADULTOS: "+numAdultos+", BEBES: "+numBebes);
//			if(numBebes % 3 ==0) {
				saleAdulto.release();
//			}
			mutex.release();
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		mutex.acquire();
		System.out.println("Entra un adulto");
		numAdultos++;
		System.out.println("ADULTOS: "+numAdultos+", BEBES: "+numBebes);
//		if(numBebes<=numAdultos*3)
		entraBebe.release();
		mutex.release();
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		saleAdulto.acquire();
		
		mutex.acquire();
		if(numBebes<=3*(numAdultos-1)) {
			numAdultos--;
			System.out.println("Ha salido un adulto");
			System.out.println("ADULTOS: "+numAdultos+", BEBES: "+numBebes);
			saleAdulto.release();
		}
		
		mutex.release();
	}

}
