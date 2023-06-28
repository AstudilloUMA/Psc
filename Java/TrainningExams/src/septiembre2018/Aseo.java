package septiembre2018;


import java.util.concurrent.Semaphore;

public class Aseo {

	private int mujeres = 0;
	private int hombres = 0;

	private Semaphore okHombre = new Semaphore(1);
	private Semaphore mutex = new Semaphore(1);
	private Semaphore okMujer = new Semaphore(1);
	/**
	 * El hombre id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay alguna mujer en ese
	 * momento en el aseo
	 */
	public void llegaHombre(int id) throws InterruptedException{
		//Pre
		okHombre.acquire();
		mutex.acquire();
		if(mujeres > 0)
		{
			mutex.release();
			okHombre.acquire();
			mutex.acquire();
		}
		//Sc
		hombres++;
		System.out.println("El hombre " + id + " entra al baño. HOMBRES: " + hombres);
		//Post
		mutex.release();
		okHombre.release();
	}
	/**
	 * La mujer id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay algun hombre en ese
	 * momento en el aseo
	 */
	public void llegaMujer(int id) throws InterruptedException{
		//Pre
		okMujer.acquire();
		mutex.acquire();
		if(hombres > 0)
		{
			mutex.release();
			okMujer.acquire();
			mutex.acquire();
		}
		//Sc
		mujeres++;
		System.out.println("La mujer " + id + " entra al baño. MUJERES: " + mujeres);
		//Post
		mutex.release();
		okMujer.release();
	}
	/**
	 * El hombre id, que estaba en el aseo, sale
	 */
	public void saleHombre(int id)throws InterruptedException{
		//Pre
		mutex.acquire();
		//Sc
		hombres--;
		System.out.println("\tEl hombre " + id + " sale del baño. HOMBRES: " + hombres);
		//Post
		if(hombres == 0) okMujer.release();
		mutex.release();
	}
	
	public void saleMujer(int id)throws InterruptedException{
		//Pre
		mutex.acquire();
		//Sc
		mujeres--;
		System.out.println("\tLa mujer " + id + " sale del baño. MUJERES: " + mujeres);
		//Post
		if(mujeres == 0) okHombre.release();
		mutex.release();
	}
}
