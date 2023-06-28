package EjercicioLock;

import java.util.Random;

public class Pizzero extends Thread{
	private MesaConPago mesa;

	private static Random r = new Random();
	public Pizzero(MesaConPago mesa){
		this.mesa = mesa;
	}
	
	public void run(){
		while (true){
			try {
				mesa.nuevoCliente();
				Thread.sleep(r.nextInt(500));	
				mesa.nuevaPizza();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
