package EjercicioLock;

import java.util.Random;

public class Estudiante extends Thread{
	private MesaConPago mesa;
	private int id;
	private static Random r = new Random();
	public Estudiante(int id, MesaConPago mesa){
		this.id = id;
		this.mesa = mesa;
	}
	
	public void run(){
		while (true){
			try {
				mesa.nuevaRacion(id);
				Thread.sleep(r.nextInt(500));	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
