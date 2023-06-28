package lago;

public class Presa extends Thread {
	private int id;
	private Lago lago;
	
	public Presa(int id, Lago lago) {
		this.id =id;
		this.lago = lago;
	}
	
	public void run() {
		for(int i=0; i < 1000; i++) {
			if(id == 0)
				lago.decPresa0();
			else
				lago.decPresa1();
		}
	}
}
