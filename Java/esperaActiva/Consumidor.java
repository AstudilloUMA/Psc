

public class Consumidor extends Thread {

	private Buffer<Integer> v;
	private int iter;
	public Consumidor(Buffer<Integer> v, int iter)
	{
		this.v = v;
		this.iter = iter;
	}
	
	public void run(){
		Integer dato;
		for(int i=0; i<iter; i++){
			
			dato = v.consumir(i);
						
		}
	}
}
