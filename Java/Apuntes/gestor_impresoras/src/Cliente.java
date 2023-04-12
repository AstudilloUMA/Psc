
import java.util.Random;

public class Cliente extends Thread {

	private Gestor g;
	private int id;
	private Random r;
	
	public Cliente(int id, Gestor g) {
		this.id = id;
		this.g = g;
		r = new Random();
	}
	
	public void run() {
		//El cliente va a imprimir 20 trabajos
		try {
			for(int i=0; i<20; i++) {
				Thread.sleep(r.nextInt(200));//simula eltiempo que no necesita imprimir nada
				g.quieroImpresora(id);
				Thread.sleep(r.nextInt(100)); //simula el tiempo que tarda en imprimir
				g.sueltoImpresora(id);
			}
		}catch(InterruptedException e)
		{
			
		}
	}
	
}
