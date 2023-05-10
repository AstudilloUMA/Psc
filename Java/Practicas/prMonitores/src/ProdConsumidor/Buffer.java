package ProdConsumidor;
public class Buffer {
	private int p; /*posicion por la que se va produciendo*/
	private int c[]; /*posicion por la que se va consumiendo cada consumidor*/
	private int[] elem;
	//TODO
	private int numHuecos;
	private int[] lecturas;
	private int[] datosPorConsumir;

	//TODO
	
	public Buffer(int N, int nCon)
	{
		p = 0;
		c = new int[nCon];
		datosPorConsumir = new int[nCon];
		//
		elem = new int[N];
		lecturas = new int[N];
		//
		numHuecos = N;
	}
	
	//Productor
	public synchronized void producir(int id, int e) throws InterruptedException {
		//CS: si no hay huecos esperamos
		while(numHuecos == 0) wait();
		//SC
		elem[p] = e;
		lecturas[p] = 0;
		System.out.println("El productor " + id + " produce el dato de la posicion " + p);
		//POST
		p = (p+1)%elem.length;
		numHuecos--;
		for (int i = 0; i < datosPorConsumir.length; i++) datosPorConsumir[i]++;

		notifyAll();
	}
	
	public synchronized int consumir(int id) throws InterruptedException {
		//CS: si ya no quedan datos esperamos
		while(datosPorConsumir[id] == 0) wait();
		//SC
		int posicion = c[id];
		int dato = elem[posicion];
		System.out.println("\tEl consumidor " + id + " ha consumido la posicion " + posicion);
		//POST
		datosPorConsumir[posicion]--;
		lecturas[posicion]++;
		if (lecturas[posicion] == c.length) numHuecos++;
		c[id] = (c[id]+1)%c.length;
		notifyAll();

		return dato;
	}
	
	public static void main(String[] args) {
		final int NP = 2;
		final int NC = 3;
		Buffer b = new Buffer(10, NC);
		Productor productores[] = new Productor[NP]; 
		Consumidor consumidores[] = new Consumidor[NC];
				
		for(int i=0; i < NP; i++)
			productores[i]= new Productor(i, b,20);
		for(int i =0; i < NC; i++)
			consumidores[i] = new Consumidor(i, b,40);
		for(int i=0; i < NP; i++)
			productores[i].start();
		for(int i=0; i < NC; i++)
			consumidores[i].start();
	}
}
