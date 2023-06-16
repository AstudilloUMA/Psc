package yield_sleep;

public class MyThreadYield extends Thread {

	public MyThreadYield()
	{
	}
	
	public void run() {
		System.out.println("Hello before yield from thread "+ this.getName());
		//System.out.println("Hello from thread "+ Thread.currentThread().getName());		
		//La llamada a yield le dice al planificador que la hebra puede ceder el procesador
		Thread.yield();		
		//Si el planificador atiende esta sugerencia la hebra pasara de 
		// en-ejecucion --> lista
		System.out.println("Hello 2 after yield from thread "+ this.getName());
		//Cuando se alcanza la llave de fin del metodo run la hebra termina
	}
}
