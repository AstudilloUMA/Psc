package yield_sleep;
public class Principal {

	public static void main(String[] args) {
		
		/*Creamos hebras que realizan llamadas a yield
		 * estas hebras pasaran de en-ejcucion a lista*/
		MyThreadYield t0[] = new MyThreadYield[10];
		for(int i=0; i<10; i++)
		{
			//Lo habitual es crear primero todos los objetos Thread
			//Y posteriormente comenzar la ejecucion de la hebra
				t0[i]= new MyThreadYield();
			//si usamos el mismo bucle para crear y comenzar la ejecucion
			// las hebras empezaron a ejecutarse antes de que se creen todas
		}
		
		/*for(int i=0; i<10; i++)
		{		
			//Aqui empiezan a ejecutarse las hebras
				t0[i].start();

			//Ojo: se llama al metodo start, NO llameis al metodo run!
		}*/
		System.out.println(Thread.currentThread().getName());
		
		/*Creamos hebras que realizan llamadas a sleep
		 * estas hebras pasaran de en-ejcucion a bloquada*/
		
		MyThreadSleep t1[] = new MyThreadSleep[10];
		for(int i=0; i<10; i++)
		{
				t1[i]= new MyThreadSleep();
		}
		
		for(int i=0; i<10; i++)
		{
				t1[i].start();
		}
		

		
		try {
			t1[0].join();
			t1[1].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Termina main");
	}

}
