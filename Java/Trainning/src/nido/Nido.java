package nido;
import java.util.concurrent.Semaphore;

public class Nido {
	private final int maxBichos;
	private int plato;
	private Semaphore hayBichos;
	private Semaphore hayHuecos;
	private Semaphore mutex;
	
	
	public Nido(int max) {
		maxBichos = max;
		hayBichos = new Semaphore(0);
		hayHuecos = new Semaphore(1);
		mutex = new Semaphore(1);
	}
	
	public void depositarBicho(int id) throws InterruptedException {
		//Pre
		hayHuecos.acquire();
		mutex.acquire();
		//SC
		plato++;
		System.out.printf("El pajaro %d deposita un bicho\tNumero de bichos %d\n", id, plato);
		//Post
		if(plato < maxBichos) hayHuecos.release();
		if(plato == 1) hayBichos.release();
		mutex.release();
	}
	public void comerBicho(int id) throws InterruptedException {
		//Pre
		hayBichos.acquire();
		mutex.acquire();
		//SC
		plato--;
		System.out.printf("\tEl polluelo %d come un bicho\tNumero de bichos %d\n", id, plato);
		//Post
		if(plato > 0) hayBichos.release();
		if(plato < maxBichos) hayHuecos.release();
		mutex.release();
	}
	
	public static void main(String[] args) {
		Nido nido = new Nido(7);
		Pajaro p0 = new Pajaro(0,nido);
		Pajaro p1 = new Pajaro(1, nido);
		Polluelo polluelos[] = new Polluelo[5];
		for(int i = 0; i < 5; i++)
			polluelos[i] = new Polluelo(i, nido);
		p0.start();
		p1.start();
		for(int i = 0; i < 5; i++)
			polluelos[i] .start();
		
		
		try {
			Thread.sleep(2000);
			p0.interrupt();
			p1.interrupt();
			for(int i = 0; i < 5; i++)
				polluelos[i].interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}

