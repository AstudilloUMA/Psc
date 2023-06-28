package barbero;

import java.util.Random;

public class Barbero extends Thread {
	private Barberia b; //donde trabaja
	private Random r;
	public Barbero(Barberia b) {
		this.b = b;
		r= new Random();
	}
	
	public void run() {
		try {
			while(true) {
				b.siguiente();//Esta disponible y se bloqueado hasta que el cliente se sienta				
				Thread.sleep(r.nextInt(100));				
				b.finPelar();//Ha abierto la puerta y no vuelve a estar disponible hasta que el cliente cierra la puerta
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
