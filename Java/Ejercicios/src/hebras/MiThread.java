package hebras;
public class MiThread extends Thread {
	int contador;
	
	
	public MiThread(int val) {
		contador = val;
	
	}
	
	public void run() {
		for(int i=0; i < contador; i++)
			System.out.println("MiThread Contador "+i);
	}

}
