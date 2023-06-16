package yield_sleep;
import java.util.Random;

public class MyThreadSleep extends Thread {

	Random r;
	public MyThreadSleep()
	{
		r= new Random();
	}
	
	public void run() {
		int ms = r.nextInt(5);
		System.out.println("Hello from thread "+ this.getName());
		try {
			//La hebra decide pasar bloqueada durante un tiempo
			Thread.sleep(ms);
			//Cuando pasa ese tiempo la hebra vuelve al estado lista y 
			//pasara a en-ejecucion cuando le de paso el planificador
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Hello after "+ms +" ms- from thread "+ this.getName());
	}
}
