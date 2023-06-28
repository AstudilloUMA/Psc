package aseos_justos_semBin;

import java.util.concurrent.Semaphore;

public class Aseo {
	
	private int clientesEnAseo;
	private Semaphore mutexC, limpiando, quieroLimpiar, mutexC2;
	
	//segun la tecnica que uses para resolverlo tendras 
	//que definir semaforos,variables bool, locks + condiciones
	
	public Aseo() {
		clientesEnAseo = 0;
		mutexC = new Semaphore(1);	
		limpiando = new Semaphore(1);// no esta limpiando el equipo ni clientes dentro
		quieroLimpiar = new Semaphore(1); //no quiere limpiar el equipo
		mutexC2 = new Semaphore(1); //para que los clientes esperen en un sitio diferente que el equipo que quiere entrar 
	}
	
	public  void entrarAseo(int id) throws InterruptedException {
		mutexC2.acquire();
		quieroLimpiar.acquire();
		mutexC.acquire();
		
		if(clientesEnAseo==0) {			
			limpiando.acquire();
		}
		clientesEnAseo++;
		System.out.println("\t\tCliente "+id+" dentro. total:"+clientesEnAseo);
		
		mutexC.release();
		quieroLimpiar.release();
		mutexC2.release();
	}

	public void salirAseo(int id) throws InterruptedException {	
		mutexC.acquire();
		clientesEnAseo--;
		System.out.println("\t\tCliente "+id+" sale. Total "+clientesEnAseo);
		
		if(clientesEnAseo == 0)
			limpiando.release();
		
		mutexC.release();
	}

	public void empezarLimpiar() throws InterruptedException {
		System.out.println("Servicio de limpiea QUIERE ENTRAR. Total "+clientesEnAseo);
		quieroLimpiar.acquire();
		limpiando.acquire();
		System.out.println("Sevicio de limpieza dentro. Total "+clientesEnAseo);
		
	}

	public  void terminarLimpiar() {
		
		System.out.println("Sevicio de limpieza fuera. Total "+clientesEnAseo);
		quieroLimpiar.release();
		limpiando.release();
	}

}
