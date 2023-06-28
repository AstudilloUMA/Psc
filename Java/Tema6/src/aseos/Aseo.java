package aseos;

import java.util.concurrent.Semaphore;

public class Aseo {
	
	private int clientesEnAseo;
	
	
	//segun la tecnica que uses para resolverlo tendras 
	//que definir semaforos,variables bool, locks + condiciones
	
	public Aseo() {
		clientesEnAseo = 0;
	
	}
	
	public  void entrarAseo(int id) throws InterruptedException {
		
		System.out.println("\t\tCliente "+id+" dentro. total:"+clientesEnAseo);
		
	}

	public void salirAseo(int id) throws InterruptedException {	
		
		System.out.println("\t\tCliente "+id+" sale. Total "+clientesEnAseo);
		
	}

	public void empezarLimpiar() throws InterruptedException {
		//System.out.println("Servicio de limpiea QUIERE ENTRAR. Total "+clientesEnAseo);
		
		System.out.println("Sevicio de limpieza dentro. Total "+clientesEnAseo);
		
	}

	public  void terminarLimpiar() {
		
		System.out.println("Sevicio de limpieza fuera. Total "+clientesEnAseo);
		
	}

}
