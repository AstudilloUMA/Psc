package deamon;
public class MyThread extends Thread {

	
	public void run()
	{
		if(Thread.currentThread().isDaemon()) {
			System.out.println(Thread.currentThread().getName()+" esta marcada como demonio");
		}else
		{
			System.out.println(Thread.currentThread().getName()+" es normal");
		}
		
		
		try {
			while(true) {				
				if(Thread.currentThread().isDaemon()) {
					Thread.sleep(200);
					System.out.println(Thread.currentThread().getName()+" demonio ha despertado");
				}else {					
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName()+" normal ha despertado");
					break;
				}
				
			}
		}catch(InterruptedException e){
			//La hebra demonio no ejecuta ni estar rama ni la finally pero al terminar las
			// hebras de usuario ella terminara
				System.out.println(Thread.currentThread().getName()+ " ha sido interrumpida");			
		}finally {
			System.out.println(Thread.currentThread().getName()+ " ha terminado");
		}
				
	} //si quitamos el while y depuramos con break point en linea 32 vemos com deamon no ha terminado realmente

}
