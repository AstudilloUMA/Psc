package jardinPanMutex;
public class Principal {

	public static void main(String[] args) {
		int N = 4;
		Jardin j = new Jardin(N);
		
		Puerta puertas[] = new Puerta[N];
		
		for(int i = 0; i < N; i++) {
			puertas[i] = new Puerta(i, j);
		}
		
		for(int i = 0; i < N; i++) {
			puertas[i].start();
		}
		try {
			for(int i = 0; i < N; i++) {
				puertas[i].join();
			}
			
			System.out.println("Al final del dia han entrado " + j.publicarVisitantes());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
