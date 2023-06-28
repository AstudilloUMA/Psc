import java.util.Random;

public class Filosofo extends Thread {
	Random r;
	private int id;
	private Tenedor  t1,t2;
	public Filosofo(int id, Tenedor t1, Tenedor t2)
	{
		this.id = id;
		this.t1 = t1;
		this.t2 = t2;
		r = new Random();
	}
	
	public void run()
	{	
		try {
			while(true)
			{

				Thread.sleep(r.nextInt(100));//pensando
				t1.cogeTenedor(id);
				t2.cogeTenedor(id);
				
				Thread.sleep(r.nextInt(40));//come
				
				t1.sueltaTenedor(id);
				t2.sueltaTenedor(id);
		
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Filosofo pensando un tiempo
	}

}
