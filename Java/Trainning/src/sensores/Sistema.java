package sensores;


import java.util.concurrent.Semaphore;

public class Sistema {
	private int medidas[];
	private int nMedidas;
	private int yaMedidas[];

	private Semaphore mutex = new Semaphore(1);
	private Semaphore[] puedeMedir;
	private Semaphore puedeTrabajar = new Semaphore(0);
	//TODO
	
	public Sistema() {
		medidas = new int[3];
		nMedidas = 0;
		yaMedidas = new int[3];
		puedeMedir = new Semaphore[3];
		for(int i = 0; i < 3; i++) puedeMedir[i] = new Semaphore(1);
		//TODO
	}
	
	public void ponerMedida(int id, int dato) throws InterruptedException {
		//Pre
		puedeMedir[id].acquire();
		mutex.acquire();
		//SC
		System.out.printf("El detector %d ha medido %d\n", id, dato);
		medidas[id] = dato;
		nMedidas++;
		//Post
		if(nMedidas == 3) puedeTrabajar.release();
		mutex.release();
	}
	
	public void procesarMedidas() throws InterruptedException {
		//Pre
		puedeTrabajar.acquire();
		mutex.acquire();
		//SC
		nMedidas = 0;
		System.out.println( "\tLuz "+ medidas[0]  +" Hum "+medidas[1] +" Temp "+medidas[2]);
		//Post
		for(int i = 0; i < 3; i++) puedeMedir[i].release();
		mutex.release();
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

 