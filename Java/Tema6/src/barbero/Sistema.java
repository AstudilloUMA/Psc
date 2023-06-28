package barbero;


public class Sistema {

	public static void main(String[] args) {
		final int NCLIENTES = 10;
		Barberia barberia = new Barberia();
		Cliente clientes[] = new Cliente[NCLIENTES];
		for(int i=0; i<NCLIENTES; i++)
			clientes[i] = new Cliente(i,barberia);
		
		Barbero barbero = new Barbero(barberia);		
		barbero.start();
		
		for(int i=0; i<NCLIENTES; i++)
			clientes[i].start();

	}

}
