package EjercicioMonitores;

public class Principal {
	
	public static void main(String[] args){
		final int NUM_ESTUDIANTES = 10;
		MesaConPago mesaConPago = new MesaConPago();
		Pizzero p = new Pizzero(mesaConPago);
		Estudiante[] est = new Estudiante[NUM_ESTUDIANTES];
		for (int i = 0; i<est.length; i++)
			est[i] = new Estudiante(i, mesaConPago);
		p.start();
		for (int i = 0; i<est.length; i++)
			est[i].start();
	}
}
