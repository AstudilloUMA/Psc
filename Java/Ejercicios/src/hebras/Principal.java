package hebras;
public class Principal {

	public static void main(String[] args) {
		
		MiThread th1 = new MiThread(3);
		
		th1.start();

		MiRunnable r1 = new MiRunnable(3);
		Thread th2 = new Thread(r1);
		 th2.start();
		 
		 Thread th3 = new Thread(r1);
		 th3.start();
		
		System.out.println("Hola desde el main");

	}
	

}
