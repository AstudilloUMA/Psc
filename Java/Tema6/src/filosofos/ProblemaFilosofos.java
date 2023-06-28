
public class ProblemaFilosofos {

	public static void main(String[] args) {
		Tenedor tenedores[] = new Tenedor[5];
		Filosofo filosofos[] = new Filosofo[5];

		for(int i=0; i<5; i++)
		{
			tenedores[i]= new Tenedor(i);
			
		}
		// t0 <- f0 -> t1 <-f1 -> t2 <- f2 -> t3 <- f3 -> t4 <- f4 -> *t0 		
		
		//Si el filosofo 0 coge los tenedores en el mismo orden el sistema puede bloquear
		filosofos[0] = new Filosofo(0,tenedores[0], tenedores[1]);
		//Si el filosofo 0 coge los tenedores en otro orden se elimina el bloqueo
		//filosofos[0] = new Filosofo(0,tenedores[1], tenedores[0]);
		
		for(int i=1; i< 5; i++)
		{
			filosofos[i]= new Filosofo(i,tenedores[i], tenedores[(i+1)%5]);
		}		
		for(int i=0; i< 5; i++)
		{
			filosofos[i].start();
		}

	}

}
