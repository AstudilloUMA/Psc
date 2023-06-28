package canibales;
import java.util.concurrent.Semaphore;

public class Caldero {
	private int raciones, maxRaciones;
	private Semaphore esperaCocinero = new Semaphore(0);
	private Semaphore puedeCocinar = new Semaphore(0);
	private Semaphore puedeComer = new Semaphore(1);
	//TODO 
	public Caldero(int max) {
		raciones = 0;
		maxRaciones = max;
		//TODO
	}
	public void comer(int id) throws InterruptedException {
		//Pre
		puedeComer.acquire();
		if(raciones == 0)
		{
			System.out.println("\t\tCanibal "+id+" avisa al cocinero");
			puedeCocinar.release();
			esperaCocinero.acquire();
		}
		//SC
		raciones--;
		System.out.println("Canibal "+id+ " come");
		//Post
		puedeComer.release();
	}

	public void cocinar() throws InterruptedException {
		//Pre
		puedeCocinar.acquire();
		//SC
		System.out.println("\tCocinero prepara 10 raciones");
		raciones = maxRaciones;
		//Post
		esperaCocinero.release();
	}

	public static void main(String[] args) {
		Caldero caldero = new Caldero(10);
		Cocinero co = new Cocinero(caldero);
		Canibal can[] = new Canibal[6];
		for(int i=0; i < 6; i++)
			can[i] = new Canibal(i, caldero);
		
		co.start();
		for(int i=0; i < 6; i++)
			can[i].start();
		
		try {
			Thread.sleep(2500);
			co.interrupt();
			for(int i=0; i < 6; i++)
				can[i].interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	

}
/**/