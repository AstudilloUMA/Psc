package interrupt;
import java.util.Random;

public class MyThreadSleep extends Thread 
{

	Random r;
	public MyThreadSleep()
	{
		r= new Random();
	}

	public void run() {
		
		int num = r.nextInt(100); //Obtengo un numero aleatorio

		try {
			//Si interrumpen la hebra mientras no está ejecutable tiene que comprobarlo y terminar manualmente
			while(num>50 && !Thread.currentThread().isInterrupted()) 
			{	//Mientras el numero aleatorio sea mayor de 50 la hebra se duerme
				System.out.println("Thread "+ Thread.currentThread().getName() + " go to sleep " + num);	
				Thread.sleep(5000);
				
				num = r.nextInt(100);//Obtengo un numero aleatorio
			}
		} catch (InterruptedException e) {
			//Si interrumpen a la hebra mientras está bloqueada se ejecuta el catch
			System.out.println("Thread "+ Thread.currentThread().getName() + " interruptedException");
		}finally
		{	// Todas las hebras ejecutan el bloque finally
			System.out.println("Thread "+ Thread.currentThread().getName() + " ms "+num+" cleaning up");
		}
		
	}
}
