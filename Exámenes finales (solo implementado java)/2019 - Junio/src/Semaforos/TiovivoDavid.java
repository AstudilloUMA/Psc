package Semaforos;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TiovivoDavid {
		
	private int pasajeros;
	
	//Lock lock = new ReentrantLock();
	
	//private boolean atraccionEnFuncionamiento = false;
	//private Condition condAtraccionFuncionando = lock.newCondition();
	
	//private boolean despiertaOperario = false;
	//private Condition condEsperaOperario = lock.newCondition();
	
	//private boolean esperaBajar = true;
	//private Condition condEsperaBajar = lock.newCondition();
	
	private Semaphore mutex = new Semaphore(1);
	
	private Semaphore montarAtraccion = new Semaphore(1);
	
	private Semaphore esperaOperario = new Semaphore(0);
	
	private Semaphore esperaBajar = new Semaphore(0);
	
	
	public TiovivoDavid(int i) {
		
		pasajeros = i;
	}

	public void subir(int id) throws InterruptedException 
	{	
		
			
			montarAtraccion.acquire();
			
			mutex.acquire();
			
			pasajeros--;
			System.out.println("Pasajero " + id + " sube al tiovivo. N�Pasajeros restantes = " + pasajeros);
			
			if (pasajeros > 0) montarAtraccion.release();
			
			if (pasajeros == 0)
			{
				
				esperaOperario.release();
			}
			
			
		mutex.release();
		
		
	
		
	}
	
	public void bajar(int id) throws InterruptedException 
	{
	
		esperaBajar.acquire();
		
		mutex.acquire();
		
		pasajeros++;
		System.out.println("Pasajero " + id + " baja del tiovivo. N�Pasajeros = " + pasajeros);
		
		if (pasajeros < 5) esperaBajar.release();
		
		if (pasajeros == 5) 
		{
		System.out.println("Tiovivo vacio. Pueden comenzar a subir pasajeros");
		montarAtraccion.release();
		}
		
		mutex.release();
		
		
	}
	
	public void esperaLleno () throws InterruptedException 
	{
		
			esperaOperario.acquire();
			
			mutex.acquire();
			
			System.out.println("Operario pone en marcha la atracci�n");
		
		
			
	}
	
	public void finViaje () 
	{
		
		
			System.out.println("Fin de la vuelta");
			
		esperaBajar.release();
		
		mutex.release();
		
	
	}
}
