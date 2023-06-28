package SemaforosNoJusto;

import java.util.concurrent.Semaphore;

public class AseosSemaphore {
	
	private int num=0;
	private Semaphore mutex= new Semaphore(1);
	private Semaphore esperaVacio = new Semaphore(1);
	private Semaphore esperaLimpio = new Semaphore(1);
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 * @throws InterruptedException 
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException{
		
		
		mutex.acquire();
		if(num==0) esperaLimpio.acquire();
		num++;
		System.out.println("Entra el cliente "+id+" hay "+num+" personas en el ba�o.");
		mutex.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * @throws InterruptedException 
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException{
		mutex.acquire();
		num--;
		System.out.println("El cliente "+ id +" sale del ba�o quedan "+num+" clientes.");
		if(num==0) esperaVacio.release();
		mutex.release();
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 * @throws InterruptedException 
	 * 
	 */
    public void entraEquipoLimpieza() throws InterruptedException{
		esperaVacio.acquire();
		System.out.println("Entra el equipo de limpieza con "+num+" clientes en el ba�o.");
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
     * @throws InterruptedException 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza() throws InterruptedException{
    	
    	System.out.println("Sale el equipo de limpieza.");
    	esperaLimpio.release();
	}
}
