package prod_cons_buffer;

public class Consumidor extends Thread {
	private Buffer myBuffer;
	private int iter;
	public Consumidor(Buffer b, int it) {
		myBuffer= b;
		iter = it;
	}
	
	public void run() {
		int dato;
		for(int i =0; i < iter; i++ ){
			try {
				dato = myBuffer.consumir();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
