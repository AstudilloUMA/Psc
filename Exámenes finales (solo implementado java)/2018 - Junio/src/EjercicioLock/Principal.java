package EjercicioLock;

public class Principal {
	
	public static void main(String[] args){
		final int NUM_ESTUDIANTES = 10;
		MesaConPago mesa = new MesaConPago();
		Pizzero p = new Pizzero(mesa);
		Estudiante[] est = new Estudiante[NUM_ESTUDIANTES];
		for (int i = 0; i<est.length; i++)
			est[i] = new Estudiante(i,mesa);
		p.start();
		for (int i = 0; i<est.length; i++)
			est[i].start();
	}
}
