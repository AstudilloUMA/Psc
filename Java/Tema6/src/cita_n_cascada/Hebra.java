package n_hebras_cascada;

public class Hebra extends Thread {
	private int id;
	private CitaN cita;
	
	public Hebra(int id, CitaN cita) {
		this.id = id;
		this.cita = cita;
	}
	
	public void run() {	
		try {
		cita.esperarATodos(id);
		System.out.println(id+ " Ya nos hemos citado");
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
