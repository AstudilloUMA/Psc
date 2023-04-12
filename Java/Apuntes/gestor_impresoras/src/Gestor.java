
import java.util.concurrent.Semaphore;

/* Un centro de cálculo dispone de N impresoras, a las que se accede mediante un Gestor.
 * Si un Cliente quiere imprimir un fichero llama a quiroImpresora.
 * En este método espera hasta que esté libre una impresora.
 * Cuando un cliente (que tiene asignada una impresora) ha terminado de usarla
 * se lo indica al Gestor mediante el método suelto impresora.
 * Diseña una solución basada en semáforos binarios que cumpla las reglas 
 * anteriores y asegure el uso en exclusión mutua de las impresoras.*/

public class Gestor {
	private int impresorasLibres;
	//TODO definir los semáforos de sincronización y exclusión mutua
	private Semaphore mutex;
	private Semaphore hayImpresora; //esta cerrado si impresoresLibres == 0
	
	
	public Gestor(int N) {
		impresorasLibres = N;
		//TODO: inicializar semáforos
		mutex = new Semaphore(1);
		hayImpresora = new Semaphore(1);
	}
	
	public void quieroImpresora(int id) throws InterruptedException {		
		
		
		//SC  
		
		System.out.println("Cliente "+id+" consigue impresora. Quedan "+impresorasLibres);
		
		
		
	}
	
	public void sueltoImpresora(int id) throws InterruptedException {
		//TODO cond sinc - si impresorasLibres == 0 --> bloquear
		hayImpresora.acquire();
		mutex.acquire();

		//SC
		impresorasLibres--;
		System.out.println("Cliente "+id+" suelta impresora. Quedan "+impresorasLibres);

		//TODO post
		if (impresorasLibres > 0) hayImpresora.release();
		mutex.release();
	}
}
