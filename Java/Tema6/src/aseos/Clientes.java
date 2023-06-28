package aseos;
import java.util.Random;

public class Clientes extends Thread {
	private Aseo aseo;
	private int id;
	private Random r;
	
	public Clientes(Aseo aseo, int id) {
		this.aseo = aseo;
		this.id = id;
		this.r = new Random();
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(r.nextInt(50)); //Da un paseo por el centro comercial
				aseo.entrarAseo(id);
				Thread.sleep(r.nextInt(50)); //Pasa un tiempo en el aseo
				aseo.salirAseo(id);
			}
		} catch(InterruptedException e) {
			e.getStackTrace();
		}
	}
}
