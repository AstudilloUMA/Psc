package ejercicio2;


import java.util.concurrent.Semaphore;

public class Sistema {
	private int medidas[];
	private int nMedidas;
	//TODO
	
	public Sistema() {
		medidas = new int[3];
		nMedidas = 0;
		//TODO
	}
	
	public void ponerMedida(int id, int dato) throws InterruptedException {
		//TODO
	}
	
	public void procesarMedidas() throws InterruptedException {
		//TODO
			
				
		System.out.println( "Luz "+ medidas[0]  +" Hum "+medidas[1] +" Temp "+medidas[2]);
		
		
	}




	 public static void main(String[] args) {
		 Sistema s = new Sistema();
			Trabajador t1 = new Trabajador(s);
			Sensor sensors[] = new Sensor[3];
			for(int i = 0; i < 3; i++)
				sensors[i] = new Sensor(i, s);
			
			t1.start();
			for(int i = 0; i < 3; i++)
				sensors[i].start();
			
			
			//Vamos a dormir un rato y luego interrumpimos todas las hebras
			try {
				Thread.sleep(2000);
				for(int i = 0; i < 3; i++)
					sensors[i].interrupt();
				t1.interrupt();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	
}

 