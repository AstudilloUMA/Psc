package filosofos_sillas;
public class ProblemaFilosofos {

	public static void main(String[] args) {
		Tenedor tenedores[] = new Tenedor[5];
		Filosofo filosofos[] = new Filosofo[5];
		Silla sillasLibres = new Silla();
		for(int i=0; i<5; i++)
		{
			tenedores[i]= new Tenedor(i);
			
		}
		
		for(int i=0; i< 5; i++)
		{
			filosofos[i]= new Filosofo(i,tenedores[i], tenedores[(i+1)%5], sillasLibres);
		}		
		for(int i=0; i< 5; i++)
		{
			filosofos[i].start();
		}

	}

}
