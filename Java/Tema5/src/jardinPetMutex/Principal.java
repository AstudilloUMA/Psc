package jardinPetMutex;
public class Principal {

	public static void main(String[] args) {
		Jardin j = new Jardin();
		Puerta p1 = new Puerta(0, j);
		Puerta p2 = new Puerta(1, j);
		
		p1.start();
		p2.start();
		
		
		try {
			p1.join();
			p2.join();
			
			System.out.println("Al final del dia han entrado " + j.publicarVisitantes());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
