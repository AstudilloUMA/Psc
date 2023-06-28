package junio2018;


import java.util.concurrent.*;

public class GestorAgua {
	private int aH, aO;

	Semaphore entraH = new Semaphore(1);
	Semaphore entraO = new Semaphore(1);
	Semaphore mutex = new Semaphore(1);
	Semaphore nuevaMolecula = new Semaphore(0);

	public void hListo(int id) throws InterruptedException{
		//Pre
		entraH.acquire();
		mutex.acquire();
		//Sc
		aH++;
		System.out.printf("La molecula de hidrogeno %d entra. HIDROGENOS: %d\n", id, aH);
		//Post
		if(aH < 2)
		{
			mutex.release();
			entraH.release();
		}
		else if(aH == 2 && aO != 1)
		{
			mutex.release();
			nuevaMolecula.acquire();
			entraH.release();
		} else if (aH == 2 && aO == 1) {
			System.out.println("NUEVA MOLECULA LISTA");
			aH = 0;
			aO = 0;
			nuevaMolecula.release();
			mutex.release();
			entraH.release();
		}
	}
	
	public void oListo(int id) throws InterruptedException{ 
		//Pre
		entraO.acquire();
		mutex.acquire();
		//Sc
		aO++;
		System.out.printf("\tLa molecula de oxiego %d entra\n", id);
		//Post

		if(aH == 2)
		{
			System.out.println("NUEVA MOLECULA LISTA");
			aO = 0;
			aH = 0;
			nuevaMolecula.release();
			mutex.release();
			entraO.release();
		}
		else
		{
			mutex.release();
			nuevaMolecula.acquire();
			entraO.release();
		}
	}
}