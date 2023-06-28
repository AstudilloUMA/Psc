package locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tiovivo {
	private int capacidad;	
	private int pasajeros;
	private Lock l = new ReentrantLock(); 
	
	private Condition csubir = l.newCondition();
	private boolean subir =true;
	
	private Condition cbajar= l.newCondition();
	private boolean bajar = false;
	
	private Condition carrancar = l.newCondition();		
	private boolean arrancar=false;
	
	public Tiovivo(int cap) {
		this.capacidad=cap;
		this.pasajeros=0;
	}
	
	
	public  void subir(int id) throws InterruptedException 
	{	
	//TODO
		try {
			l.lock();
			while(!subir) {
				csubir.await();
			}
			pasajeros++;
			System.out.println("Ha subido el pasajero "+id);
			System.out.println("Hay "+this.pasajeros+" pasajeros");
			if(pasajeros==capacidad) {
				subir=false;
				arrancar=true;
				carrancar.signalAll();
			}
		}finally {
			l.unlock();
		}
		
	}
	
	public void bajar(int id) throws InterruptedException 
	{
		//TODO
		try {
			l.lock();
			while(!bajar) {
				cbajar.await();
			}
			pasajeros--;
			System.out.println("Ha bajado el pasajero "+ id);
			System.out.println("Quedan "+ this.pasajeros+" pasajeros");
			if(pasajeros == 0) {
				bajar = false;
				subir=true;
				csubir.signalAll();
			}
		}finally {
			l.unlock();
		}
		
		
	}
	
	public void esperaLleno () throws InterruptedException 
	{
		//TODO
		try {
			l.lock();
			while(!arrancar) {
				carrancar.await();
			}
			System.out.println("Arranc� el caballo");
			arrancar = false;
		}finally {
			l.unlock();
		}
		
		
		
		
	}
	
	public void finViaje () throws InterruptedException 
	{
		//TODO
		try {
			l.lock();
			System.out.println("Acab� el viaje chiquis");	
			bajar=true;
			cbajar.signalAll();
		}finally {
			l.unlock();
		}
		
	}
}