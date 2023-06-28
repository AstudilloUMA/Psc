package junio2017;


import java.util.concurrent.Semaphore;

public class Guarderia {

	private int bebes; //numero de bebes
	private int adultos; //numero de adultos

	Semaphore mutex = new Semaphore(1);
	Semaphore bebeEntra = new Semaphore(0);
	Semaphore adultoSale = new Semaphore(0);



	//CS-1- bebe no puede entrar si al entrar no se cumple bebes <= 3xadultos
	//CS-2- un adulto no puede salir a noo ser que al salir se cumpla bebes <= 3xadultos

	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		//Pre
		bebeEntra.acquire();
		mutex.acquire();
		while(bebes+1 > 3*adultos)
		{
			System.out.println("El bebe " + id + " ESPERA a entrar");
			mutex.release();
			bebeEntra.acquire();
			mutex.acquire();
		}
		//Sc
		bebes++;
		System.out.println("El bebe " + id + " ENTRA a la guarderia. \tBEBES: " + bebes + " \tADULTOS: " + adultos);
		//Post
		mutex.release();
		bebeEntra.release();
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
			//Pre
			mutex.acquire();
			//Sc
			bebes--;
			System.out.println("El bebe " + id + " SALE de la guarderia. \tBEBES: " + bebes + " \tADULTOS: " + adultos);
			//Post
			if(bebes < 3*(adultos-1)) adultoSale.release();
			mutex.release();
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		//Pre
		mutex.acquire();
		//Sc
		adultos++;
		System.out.println("\tEl adulto " + id + " ENTRA a la guarderia. \tBEBES: " + bebes + " \tADULTOS: " + adultos);
		//Post
		if(bebes < 3*adultos) bebeEntra.release();
		mutex.release();
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		//Pre
		adultoSale.acquire();
		mutex.acquire();
		while (bebes > 3*(adultos-1))
		{
			System.out.println("\tEl adulto " + id + " ESPERA a salir");
			mutex.release();
			adultoSale.acquire();
			mutex.acquire();
		}
		//Sc
		adultos--;
		System.out.println("\tEl adulto " + id + " SALE de la guarderia. \tBEBES: " + bebes + " \tADULTOS: " + adultos);
		//Post
		mutex.release();
		adultoSale.acquire();

	}

}
