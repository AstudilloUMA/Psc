package jardin_monitores;

public class Jardin {
	int visitantes;

	
	public Jardin()
	{
		visitantes = 0;
		
	}
	// recurso compartido
	public synchronized void entrar(int id) throws InterruptedException {
		
		//PRE		
		//SC
		visitantes++;
		System.out.println("Entra por puerta "+id+ ". Total "+visitantes);
		//Post

	}
	
	public synchronized int publicarVisitantes() throws InterruptedException {

		return visitantes;		
	}
	
	
}
