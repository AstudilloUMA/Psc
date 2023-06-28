package lago;

public class Principal {

	public static void main(String[] args) {
		Lago lago = new Lago();
		Rio r0 = new Rio(0,lago);
		Rio r1 = new Rio(1,lago);
		Presa p0 = new Presa(0, lago);
		Presa p1 = new Presa(1, lago);
		r0.start();
		r1.start();
		p0.start();
		p1.start();
		
		try {
			r0.join();
			r1.join();
			p0.join();
			p1.join();
			
			System.out.println("Nivel del lago al final: "+lago.nivel());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
