package prod_cons;

public class Consumidor extends Thread {
	private Buffer b;
	private int id;
	public Consumidor(Buffer b, int id) {
		this.b = b;
		this.id = id;
	}
	
	public void run() {
		for(int i = 0; i < 100; i++) {
			try {
				int dato = b.consumir();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
