package jardines;

public class Puerta extends Thread {
	private int id;
	private Jardin j;
	
	public Puerta(int id, Jardin j) {
		this.id = id;
		this.j = j;
	}
	
	public void run() {
		for(int i = 0; i < 1000; i++) {
			try {
				j.nuevoVisitante(id);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
