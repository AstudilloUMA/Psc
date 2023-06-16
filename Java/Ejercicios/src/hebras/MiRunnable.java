package hebras;
public class MiRunnable implements Runnable {
	int contador;
	
	public MiRunnable(int val) {
		contador = val;
	}
	@Override
	public void run() {
		for(int i=0; i < contador; i++)
			System.out.println("MiRunnable "+i);

	}

}
