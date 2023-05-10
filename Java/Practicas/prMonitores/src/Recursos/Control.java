package Recursos;
import java.util.*;

public class Control {

	private int recursos; // entero que almacena los recursos disponibles
	//TODO
	List<Integer> cola;// cola FIFO donde se iran anyadiendo los procesos en cola

	public Control(int recursos) {
		this.recursos = recursos;
		cola = new LinkedList<Integer>();
	}

	public synchronized void quieroRecursos(int id, int num) throws InterruptedException {
		cola.add(id);
		//CS: si no hay recursos disponibles entrar en cola y wait
		System.out.println("El proceso " + id + " quiere " + num + " recursos en el turno " + cola.size());
		while(cola.get(0) != id || recursos < num) wait();
		//SC
		recursos -= num;
		cola.remove(0);
		System.out.println("El proceso " + id + " esta consumiendo " + num + " recursos. Total " + recursos + " recursos");
		//POST
		if(recursos > 0) notifyAll();
	}

	public synchronized void liberoRecursos(int id, int num) {
		recursos += num;
		System.out.println("\t;El proceso " + id + " ha liberado " + num + " recursos. Total " + recursos + "recursos");
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
