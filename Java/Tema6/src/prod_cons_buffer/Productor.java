package prod_cons_buffer;

public class Productor extends Thread{
	private Buffer myBuffer;
	private int iter;
	public Productor(Buffer b, int it){
		myBuffer= b;
		iter = it;
	}
	
	public void run() {		
		for(int i = 0; i < iter; i++) {

			try {
				myBuffer.producir(i);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
