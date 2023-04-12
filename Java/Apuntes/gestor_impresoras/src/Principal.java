

public class Principal {
	final static int N_IMPRESORAS = 3;
	final static int N_CLIENTES = 7;
	public static void main(String[] args) {
		//Creamos el recurso compartido y los objetos que representan a las hebras
		Gestor g = new Gestor(N_IMPRESORAS);
		Cliente clientes[] = new Cliente[N_CLIENTES];
		
		for(int i = 0; i< N_CLIENTES; i++)
		{
			clientes[i] = new Cliente(i, g);
		}
		
		//inicializamos las hebras
		for(int i = 0; i< N_CLIENTES; i++)
		{
			clientes[i].start();
		}
	}
}
