package mrusa;
import java.util.concurrent.Semaphore;

public class Coche extends Thread {
	private int capacidad;
	private Semaphore puedenSubir;
	private Semaphore puedenBajar;
	private Semaphore darVuelta;
	private int hanSubido;
	private int hanBajado;
	
	public Coche(int capacidad) {
		puedenSubir = new Semaphore(1);
		puedenBajar = new Semaphore(0);
		darVuelta = new Semaphore(0);

		this.capacidad = capacidad;
	}
	public void quieroSubir(int id) throws InterruptedException {
		//Pre
		puedenSubir.acquire();
		//SC
		hanSubido++;
		System.out.println("El pasajero " + id + " ha subido al vagon. Total: " + hanSubido);
		//Post
		if(hanSubido < capacidad) puedenSubir.release();
		if(hanSubido == capacidad)
		{
			hanBajado = 0;
			darVuelta.release();
		}
	}

	public void quieroBajar(int id) throws InterruptedException {
		//Pre
		puedenBajar.acquire();
		//SC
		hanBajado++;
		System.out.println("\tEl pasajero " + id + " ha bajado del vagon. Total: " + (hanSubido-hanBajado));
		//Post
		if(hanBajado < capacidad) puedenBajar.release();
		if(hanBajado == capacidad)
		{
			hanSubido = 0;
			puedenSubir.release();
		}
	}
	
	public void inicioViaje() throws InterruptedException {		
		//Pre
		darVuelta.acquire();
		//SC
		System.out.println("\t\tEL VAGON EMPIEZA SU VIAJE");
		//Post
	}
	
	public void finViaje() throws InterruptedException {							
		//Pre

		//SC
		System.out.println("\t\tEL VAGON HA FINALIZADO SU VIAJE");
		//Post
		puedenBajar.release();
	}
	
	
	public void run() {
		try {
			while(!isInterrupted()) {
				inicioViaje();
				Thread.sleep(100);
				finViaje();
			}
		}catch(InterruptedException e) {
			System.out.println("Coche interrumpido");
		}
	}
	
	public static void main(String[] args) {
		Coche c = new Coche(4);
		Pasajero pasajeros[] = new Pasajero[10];
		for(int i = 0; i <pasajeros.length ; i++)
			pasajeros[i] = new Pasajero(i, c);
		c.start();
		for(int i = 0; i <pasajeros.length ; i++)
			pasajeros[i].start();

	}

}
