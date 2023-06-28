package jardines;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Jardin {
	int visitantes;
	Semaphore mutex;
	
	public Jardin()
	{
		visitantes = 0;
		mutex = new Semaphore(1);
	}
	// recurso compartido
	public void nuevoVisitante(int id) throws InterruptedException {
		
		//PRE
		mutex.acquire();
		//SC
		visitantes++;
		System.out.println("Entra por puerta "+id+ ". Total "+visitantes);
		
		//Post
		mutex.release();
		
	}
	
	public int publicarVisitantes() throws InterruptedException {
		mutex.acquire();
		int tem = visitantes;
		mutex.release();
		return tem;		
	}
	
	
	
}
