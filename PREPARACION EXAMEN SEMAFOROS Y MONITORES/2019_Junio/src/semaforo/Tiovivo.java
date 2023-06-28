package semaforo;

import java.util.concurrent.Semaphore;

public class Tiovivo {
	private int capacidad;
	private int pasajeros;	
	private Semaphore mutex= new Semaphore(1);
	private Semaphore subir= new Semaphore(1);
	private Semaphore bajar= new Semaphore(0);
	private Semaphore arrancar = new Semaphore(0);
	public Tiovivo(int cap) {
		this.capacidad=cap;
		this.pasajeros=0;
	}
	
	
	public void subir(int id) throws InterruptedException 
	{	
	//TODO	
		subir.acquire();
		mutex.acquire();
		pasajeros++;
		System.out.println("Ha subido el pasajero "+id);
		System.out.println("Hay "+ this.pasajeros +" pasajeros");
		if(pasajeros<capacidad) {
			subir.release();
		}else {
			
			arrancar.release();
		}
		mutex.release();
		
	}
	
	public void bajar(int id) throws InterruptedException 
	{
		//TODO
		bajar.acquire();
		mutex.acquire();
		pasajeros--;
		System.out.println("Ha bajado el pasajero "+ id);
		System.out.println("Quedan "+ this.pasajeros+" pasajeros");
		if(pasajeros==0) {
			subir.release();
		}else {
			bajar.release();
		}
		
		mutex.release();
	}
	
	public void esperaLleno () throws InterruptedException 
	{
		//TODO
		arrancar.acquire();
		mutex.acquire();
		System.out.println("Arranc� el caballo");
		
		mutex.release();
		
		
	}
	
	public void finViaje () throws InterruptedException 
	{
		//TODO
		mutex.acquire();
		System.out.println("Acab� el viaje chiquis");
		bajar.release();
		mutex.release();
		
	}
}
