package n_cascada_varias_rondas;

public class Hebra extends Thread {
	private int id;
	private CitaN cita;
	
	public Hebra(int id, CitaN cita) {
		this.id = id;
		this.cita = cita;
	}
	
	public void run() {	
		try {
			while(true) {
				cita.esperarATodos(id);				
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
