package filosofos_sillas;
import java.util.Random;

public class Filosofo extends Thread {
	Random r;
	private int id;
	private Tenedor  t1,t2;
	private Silla s;
	public Filosofo(int id, Tenedor t1, Tenedor t2, Silla s)
	{
		this.id = id;
		this.t1 = t1;
		this.t2 = t2;
		this.s = s;
		r = new Random();
	}
	
	public void run()
	{	
		try {
			while(true)
			{

				Thread.sleep(r.nextInt(100));//pensando
				s.quieroSilla();
				t1.cogeTenedor(id);
				t2.cogeTenedor(id);
				
				Thread.sleep(r.nextInt(40));//come
				
				t1.sueltaTenedor(id);
				t2.sueltaTenedor(id);
				s.sueltoSilla();

						
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //Filosofo pensando un tiempo
	}

}
