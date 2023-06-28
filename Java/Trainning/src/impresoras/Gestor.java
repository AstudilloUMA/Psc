package impresoras;

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
	private Semaphore mutex;
	
	
	public Gestor(int N) {
		impresorasLibres = N;
		mutex = new Semaphore(N);
	}
	
	public void quieroImpresora(int id) throws InterruptedException {		
		mutex.acquire();
		//SC
		impresorasLibres--;
		System.out.println("Cliente "+id+" consigue impresora. Quedan "+impresorasLibres);
	}
	
	public void sueltoImpresora(int id) throws InterruptedException {
		//SC
		impresorasLibres++;
		System.out.println("\tCliente "+id+" suelta impresora. Quedan "+impresorasLibres);
		mutex.release();
	}
}
