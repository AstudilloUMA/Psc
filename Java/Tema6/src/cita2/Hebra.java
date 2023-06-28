package cita2;

public class Hebra extends Thread {
	private int id;
	private Cita2 cita;
	
	public Hebra(int id, Cita2 cita) {
		this.id = id;
		this.cita = cita;
	}
	
	public void run() {	
		try {
		if(id==0)
			cita.esperar1();
		else
			cita.esperar0();
		System.out.println(id+ " Ya nos hemos citado");
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
