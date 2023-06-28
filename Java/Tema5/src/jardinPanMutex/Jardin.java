package jardinPanMutex;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Jardin {
	int visitantes;
	volatile private AtomicIntegerArray turnos;
	volatile private AtomicIntegerArray pidiendo;
	
	public Jardin(int n)
	{
		turnos =new AtomicIntegerArray(n);
		pidiendo = new AtomicIntegerArray(n);
	}
	// recurso compartido
	public void entrar(int id) {
		
		//PRE
		cogerTurno(id);
		esperarTurno(id);
		//SC
		visitantes++;
		//System.out.println("Entra por puerta "+id+ ". Total "+visitantes);
		
		//Post
		salir(id);
		
		
	}
	
	public int publicarVisitantes() {
		return visitantes;		
	}
	//metodos de la panaderia
	public void cogerTurno(int id) {
		pidiendo.set(id, 1);
		
		int max = 0;
		for(int i=0; i < pidiendo.length(); i++) {			
			if(max < turnos.get(i))
				max = turnos.get(i);
		}
		turnos.set(id, max+1); 
		
		pidiendo.set(id, 0);
	}
	public void esperarTurno(int id) {
		for(int i=0; i < turnos.length(); i++) {
			while(pidiendo.get(i)==1) 
				Thread.yield();
			while(!meToca(id, i)) 
				Thread.yield();
		}
	}
	public boolean meToca(int miId, int otra) {
		if(turnos.get(otra) >0 && turnos.get(otra)< turnos.get(miId))
			return false;
		else if (turnos.get(otra) == turnos.get(miId) && otra < miId)
			return false;
		else 
			return true;
		
	}
	public void salir(int id) {
		turnos.set(id, 0); 
	}
	
	
}
