

public class Buffer<T> {

	private T var;
	//hayDato == true si se acaba de producir un dato y no se ha consumido
	private volatile boolean hayDato = false; //inicialmente no hay dato
	
	public void producir(T v){
		while(hayDato){
			Thread.yield();
		}
		var = v;
		System.out.println("Dato producido "+var);
		hayDato= true;			
	}
	
	public T consumir(int i) {
		while(!hayDato){
			Thread.yield();
		}
		System.out.println("\t\tDato consumido "+ var);
		hayDato = false;		
		return var;
	}	
}
