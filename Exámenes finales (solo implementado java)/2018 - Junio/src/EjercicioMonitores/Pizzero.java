package EjercicioMonitores;

import java.util.Random;

public class Pizzero extends Thread{
	private MesaConPago mesaConPago;

	private static Random r = new Random();
	public Pizzero(MesaConPago mesaConPago){
		this.mesaConPago = mesaConPago;
	}
	
	public void run(){
		while (true){
			try {
				mesaConPago.nuevoCliente();
				Thread.sleep(r.nextInt(500));	
				mesaConPago.nuevaPizza();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
