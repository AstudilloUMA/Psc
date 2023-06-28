package jardinPetMutex;
public class Jardin {
	int visitantes;
	volatile int turno = 0;
	volatile boolean f0, f1;
	// recurso compartido
	public void entrar(int id) {
		if(id == 0)
			pre0();
		else
			pre1();
		
		visitantes++;
		System.out.println("Entra por puerta "+id+ ". Total "+visitantes);
		
		if(id == 0)
			post0();
		else
			post1();
	}
	
	public int publicarVisitantes() {
		return visitantes;		
	}
	
	// peterson
	void pre0() {
		f0 = true;
		turno = 1;
		while(f1 && turno ==1) Thread.yield();
	}
	void pre1(){
		f1 = true;
		turno = 0;
		while(f0 && turno == 0)  Thread.yield();
	}
	void post0() {
		f0 = false;
	}
	void post1() {
		f1 = false;
	}
}
