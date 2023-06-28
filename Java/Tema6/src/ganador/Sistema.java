package aseos;
public class Sistema {

	public static void main(String[] args) {
		final int TAM = 5;
		Aseo aseo = new Aseo();
		Limpieza limpieza = new Limpieza(aseo);
		Clientes[] clientes = new Clientes[TAM];
		
		for(int i = 0; i < TAM; i++) {
			clientes[i] = new Clientes(aseo, i);
		}
		limpieza.start();
		for(int i = 0; i < TAM; i++) {
			clientes[i].start();
		}
		

	}

}
