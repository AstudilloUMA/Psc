package EjercicioMonitores;
import java.util.*;
public class Estudiante extends Thread{
	private MesaConPago mesaConPago;
	private int id;
	private static Random r = new Random();
	public Estudiante(int id, MesaConPago mesaConPago){
		this.id = id;
		this.mesaConPago = mesaConPago;
	}
	
	public void run(){
		while (true){
			try {
				mesaConPago.nuevaRacion(id);
				Thread.sleep(r.nextInt(500));	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
