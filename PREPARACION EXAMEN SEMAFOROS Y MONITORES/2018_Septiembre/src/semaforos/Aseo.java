package semaforos;

import java.util.concurrent.Semaphore;

public class Aseo {
	private int numHombres=0;
	private int numMujeres=0;
	private Semaphore mutex= new Semaphore(1);
	
	private Semaphore esperaHombre= new Semaphore(1);//Semáforo inicializado a 1 porque pueden entrar cualquiera de los dos
	private Semaphore esperaMujer= new Semaphore(1);//Semáforo inicializado a 1 porque pueden entrar cualquiera de los dos
	private Semaphore esperaHombreNoMujer = new Semaphore(1);
	private Semaphore esperaMujerNoHombre = new Semaphore(1);
	/**
	 * El hombre id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay alguna mujer en ese
	 * momento en el aseo
	 */
	public void llegaHombre(int id) throws InterruptedException{
		
		esperaHombre.acquire();
		mutex.acquire();
		System.out.println("LLega un hombre");
		if(numHombres == 0) {
			esperaMujer.acquire();
		}
		numHombres++;
		System.out.println("Ha llegado un hombre");
		System.out.println("Hay "+ numHombres+" hombres y "+ numMujeres+" mujeres");
		esperaHombre.release();
		mutex.release();
	}
	/**
	 * La mujer id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay algun hombre en ese
	 * momento en el aseo
	 */
	public void llegaMujer(int id) throws InterruptedException{
		esperaMujer.acquire();
		mutex.acquire();
		System.out.println("Llega una mujer");
		if(numMujeres==0) {
			esperaHombre.acquire();
		}
		numMujeres++;
		System.out.println("Ha llegado una mujer");
		System.out.println("Hay "+ numHombres+" hombres y "+ numMujeres+" mujeres");
		esperaMujer.release();
		mutex.release();
	}
	/**
	 * El hombre id, que estaba en el aseo, sale
	 */
	public void saleHombre(int id)throws InterruptedException{
		esperaHombre.acquire();
		mutex.acquire();
		numHombres--;
		System.out.println("Ha salido un hombre");
		System.out.println("Hay "+ numHombres+" hombres y "+ numMujeres+" mujeres");
		if(numHombres == 0) { //Compruebo si el baño está vacío, entonces podrá entrar una mujer
			esperaMujer.release();
		}
		esperaHombre.release();
		mutex.release();
		
		
	}
	
	public void saleMujer(int id)throws InterruptedException{
		esperaMujer.acquire();
		mutex.acquire();
		numMujeres--;
		System.out.println("Ha salido una mujer");
		System.out.println("Hay "+ numHombres+" hombres y "+ numMujeres+" mujeres");
		if(numMujeres == 0) {//Si el numero de mujeres es 0, podrán entrar hombres
			esperaHombre.release();
			
		}
		esperaMujer.release();
		mutex.release();
	}
}
