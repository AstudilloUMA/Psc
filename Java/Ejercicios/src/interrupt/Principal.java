package interrupt;
public class Principal {

	public static void main(String[] args) {		
		
		MyThreadSleep t1[] = new MyThreadSleep[10];
		for(int i=0; i<10; i++)
		{
				t1[i]= new MyThreadSleep();//Creamos las hebras
		}
		
		for(int i=0; i<10; i++)
		{
				t1[i].start();//Comenzamos la ejecucion de las hebras
		}
		
		try {
			Thread.sleep(100); //Bloqueamos la hebra principal
			
			t1[0].interrupt();
			/*for(int i=0; i<10; i++)
			{
					t1[i].interrupt();//interrumpimos las hebras t[0] ... t[9]
			}*/
			//NO podemos volver a llamar a start si la hebra ha terminado de ejecutarse
			//t1[1].start();
			
			t1[1].join(); //si ya ha terminado la hebra t1[1] no llega a bloquearse
			//Podemos llamar al constructor que crea una nueva hebra
			// Asi aprovechamos el nombre de las variables - observa que se crea un nuevo thread-id
			t1[1] = new MyThreadSleep();
			t1[1].start();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
