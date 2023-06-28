package prodCons_esperaActiva;
public class Principal {
	public static void main(String[] args) {
		Buffer<Integer> v = new Buffer<Integer>();
		Productor p = new Productor(v,100);
		Consumidor c = new Consumidor(v,100);
		
		p.start();
		c.start();
	}
}
