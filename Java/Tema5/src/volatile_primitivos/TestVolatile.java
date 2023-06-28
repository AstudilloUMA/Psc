package volatile_primitivos;

public class TestVolatile {

	/* myVar es una variable visible dentro de todas las clases anidadas en TestVolatile 
	 * Probar la version sin y con volatile, ya sea usando tipos primitivos u objetos
	 */
	
	private static int myVar; 
	
	
	//volatile private static int myVar =0;
	//private static Integer myVar; 
	//volatile private static Integer myVar =0;
	
	public static void main(String[] args) 
	{
		myVar =0;
		//myVar = new Integer(0);
		Thread t1 = new ThreadListener(); //Esta hebra no termina hasta que myInt > 5
		Thread t2 = new ThreadModifyer(); //Esta hebra incrementa myInt 5 veces y termina
		t1.start();
		t2.start();	
		/*
		 * sin volatile - la hebra t1 no ve el cambio de valor en myInt y el programa no termina
		 * con volatile - la hebra t1 si ve el cambio de valor y termina
		 */
	}
		
	
	//Declaración anidada de clase que hereda de Thread 
	static class ThreadListener extends Thread
	{
		public void run() 
		{
			int local_value = myVar;
			while(local_value < 5)
			{/* sin volatile 
			  * local_value <5 && local_value = myInt 
			  * porque no ve el cambio de valor de myInt.*/
				if(local_value!=myVar)
				{
					System.out.println("Listener: myInt = "+myVar);
					local_value = myVar;
				}
			}
		}
	}
	
	//Declaración anidada de clase que hereda de Thread 
	static class ThreadModifyer extends Thread
	{
		public void run() 
		{
			int local_value = myVar;
			while(local_value < 5)
			{
				
				local_value++;
				myVar=local_value;
				System.out.println("Modifyer: myInt= "+myVar);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
