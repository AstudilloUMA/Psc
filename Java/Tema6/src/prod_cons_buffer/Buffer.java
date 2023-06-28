package prod_cons_buffer;

import java.util.concurrent.Semaphore;

public class Buffer {
	private int elem[];
	private int p,c,nelem; //numero de datos por consumir
	//Semaforos para sincronizacion y exclusion mutua
	Semaphore mutex, hayHueco, hayDato;
	
	public Buffer(int n) {
		elem = new int[n];
		p = 0; c = 0; nelem = 0;
		// Incializar los semaforos
		mutex = new Semaphore(1);
		hayHueco = new Semaphore(1);
		hayDato = new Semaphore(0);
	}
	
	public void producir(int dato) throws InterruptedException {
		//pre-protocolo - sincronizacion y exclusion mutua
		hayHueco.acquire();
		mutex.acquire();
		//SC
		elem[p]=dato;
		System.out.println("Dato producido "+ elem[p]);
		p = (p+1)%elem.length;	
		nelem++;
		
		//post-protocolo
		//if(nelem == elem.length) // no hay huecos
		if(nelem < elem.length)
			hayHueco.release();
		if(nelem > 0) {// antes del ++ hayDatos estaba cerado
			hayDato.release();
		}
		
		mutex.release();
	}
	
	
	public int consumir() throws InterruptedException {
		int d;
		//pre-protocolo + sincronizacion 
		hayDato.acquire();
		
		mutex.acquire();
		//SC
		d = elem[c];
		System.out.println("\tDato consumido "+ d);
		c = (c+1)%elem.length;		
		nelem--;
		//post-protocolo
		
		if(nelem > 0 )
			hayDato.release();
		
		if(nelem < elem.length)
			hayHueco.release();
		mutex.release();
		return d; 
	}
	public static void main(String[] args) {
		Buffer myBuffer = new Buffer(5);
		Productor prod= new Productor(myBuffer, 30);
		Consumidor cons = new Consumidor(myBuffer, 30);
		
		prod.start();
		cons.start();

	}

}
