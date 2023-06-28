package jardines;

public class Principal {
	public static void main(String args[])
	{
		Jardin jardin = new Jardin();
		Puerta p1 = new Puerta(0, jardin);
		Puerta p2 = new Puerta(1, jardin);
		
		p1.start();
		p2.start();
		
		try {
			p1.join();
			p2.join();
			System.out.println("Número total de visitantes "+jardin.publicarVisitantes());
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
		
	}
}
