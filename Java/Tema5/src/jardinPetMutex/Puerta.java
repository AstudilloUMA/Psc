package jardinPetMutex;
public class Puerta extends Thread {
	int id;
	Jardin j;
	
	public Puerta(int id, Jardin j) {
		this.j = j;
		this.id = id;
	}
	
	public void run() {
		for(int i =0; i < 10000; i++) {
			try {
				Thread.sleep(10);
				j.entrar(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
