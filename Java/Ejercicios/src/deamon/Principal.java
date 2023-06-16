package deamon;
public class Principal {

	public static void main(String[] args) {		
		
		/* Marcamos la hebra como tipo demonio, 
		 * hay que hacerlo antes de que comience a ejecutarse la hebra */
		
		MyThread daemon = new MyThread(); 
		daemon.setDaemon(true);  // hay que decir que es demonio antes del start
		daemon.start();
				
		MyThread t = new MyThread();
		t.start(); //esta hebra es normal
		//No hacer esto -- el programa no terminara
		/*try {

			daemon.join();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}finally{
			System.out.println("main ha terminado");
		}*/
		

	}

}
