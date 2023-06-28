package prodCons_esperaActiva;

import java.util.Random;

public class Productor extends Thread {
	private static Random r = new Random();
	Buffer<Integer>  v;
	int iter;
	public Productor(Buffer<Integer> v, int iter)
	{
		this.v = v;
		this.iter =iter;
	}
	
	public void run() {
		int dato = 0;
		for(int i = 0; i<iter; i++){
			dato = i;							
			v.producir(dato);
		}
	}
	
}
