package aseos;
import java.util.Random;

public class Limpieza extends Thread {
	private Aseo aseo;
	private Random r;
	
	public Limpieza(Aseo aseo) {
		this.aseo = aseo;
		this.r = new Random();
	}
	
	public void run() {
		try {
			while(true) {
				aseo.empezarLimpiar();
				Thread.sleep(100 + r.nextInt(100)); //limpia el aseo
				aseo.terminarLimpiar();
				Thread.sleep(100 + r.nextInt(100)); //espera al siguiente turno de limpieza
			}
		} catch (InterruptedException e) {
			e.getStackTrace();
		}
	}
}
