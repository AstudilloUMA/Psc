package filosofos_sillas;
import java.util.concurrent.Semaphore;

public class Silla {
	Semaphore s = new Semaphore(4, true);
	
	public void quieroSilla() throws InterruptedException {
		s.acquire();
	}
	
	public void sueltoSilla()
	{
		s.release();
	}
}
