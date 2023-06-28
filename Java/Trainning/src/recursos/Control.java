package recursos;
import java.util.*;

public class Control {

	private int recursos; //num de recursos disponibles

	public class Turno
	{
		private int proceso;
		private int recursos;
		public Turno(int proceso, int recursos)
		{
			this.proceso = proceso;
			this.recursos = recursos;
		}
	}

	private List<Turno> turnos = new ArrayList<>();

	public Control(int recursos) {
		this.recursos = recursos;
	}
	
	public synchronized void quieroRecursos(int id, int num) throws InterruptedException {
		System.out.println("El proceso " + id + " quiere " + num + " recursos");
		turnos.add(new Turno(id,num));
		//Pre
		while(recursos < num || turnos.get(0).proceso != id) wait();
		//Sc
		turnos.remove(0);
		recursos -= num;
		System.out.println("\tEl proceso " + id + " entra al procesador. RECURSOS: " + recursos);
		//Post
		if(recursos > 0) notifyAll();
	}
	
	public synchronized void liberoRecursos(int id, int num) {
		recursos += num;
		System.out.println("\t\tEl proceso " + id + " sale del procesador. RECURSOS: " + recursos);
		notifyAll();
	}
	
	public static void main(String[] args) {
		Control c = new Control(10);
		Proceso procesos[] = new Proceso[6];
		for(int i = 0; i < 6 ; i++)
			procesos[i] = new Proceso(i, c, 10);
		for(int i = 0; i < 6 ; i++)
			procesos[i].start();

	}

}
