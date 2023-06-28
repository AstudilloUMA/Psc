package barbero;

import java.util.Random;

public class Cliente extends Thread {
	private Barberia b;//a la que van los clientes
	private Random r;
	private int id;
	public Cliente(int id, Barberia b) {
		this.b = b;
		r = new Random();
		this.id = id;
	}
	
	public void run() {
		try {
			while(true) {
				b.voyAPelarme(id);
				Thread.sleep(r.nextInt(300));
			}
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
}
