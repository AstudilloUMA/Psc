package barbero;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barberia {
	

	
	/* Lo utiliza los clientes para entrar a la barbería. 
	 * Cuando termina la ejecución del método, el cliente sale con un nuevo look.
	 * */
	public void voyAPelarme(int id) {
		
		//CS1- El cliente espera a que el barbero esté disponible
		
		
		//SC - el cliente se sienta
		
		System.out.println("C"+id+" ya me van a pelar");
		
		//CS3 - el cliente espera a que el barbero abra la puerta
		
		//SC - el cliente sale de la barbería
						
	}

	/* Lo utiliza el barbero para atender un nuevo cliente. 
	 * Si no hay clientes se duerme
	 */
	public void siguiente() {
		
			//SC - Barbero disponible
			
			//CS2 - Espera/duerme hasta que se sienta un cliente
			
			//Empieza a pelar al cliente
			System.out.println("Barbero empieza a pelar");		
	}


	public void finPelar() throws InterruptedException {		
			//SC - abre la puerta para que salga el cliente
			System.out.println("Barbero termina y abre la puerta");

			//CS4- espera que el cliente cierre la puerta			
		
	}
}
