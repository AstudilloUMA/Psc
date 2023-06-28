package jardin_monitores;

public class Puerta extends Thread {
	private int id;
	private Jardin j;
	
	public Puerta(int id, Jardin j) {
		this.id = id;
		this.j = j;
	}
	
	public void run() {
		try {
			for(int i = 0; i < 1000; i++) {
				j.entrar(id);
			}
		}catch(InterruptedException e) {e.printStackTrace();}
	}
}
