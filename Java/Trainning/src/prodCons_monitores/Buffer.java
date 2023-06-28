package prodCons_monitores;
public class Buffer {
	private int p; /*posicion por la que se va produciendo*/
	private int c[]; /*posicion por la que se va consumiendo cada consumidor*/
	private int[] elem;
	private int numC, numHuecos, numDatos[], numLecturas[];
	private boolean produciendo = false;
	private boolean consumiendo = false;
	
	public Buffer(int N, int nCon)
	{
		c = new int[nCon];
		elem = new int[N];
		numDatos = new int[nCon];
		numLecturas = new int[N];
		for(int i = 0; i < nCon; i++){
			numDatos[i] = N;
		}
		numHuecos = N;
		numC = nCon;
	}
	
	//Productor
	public synchronized void producir(int id, int e) throws InterruptedException {
		//Pre
		while(numHuecos == 0) wait();
		//Sc
		System.out.println("El productor " + id + " produce el dato " + e);
		numHuecos--;
		numLecturas[p] = 0;
		elem[p] = e;
		p = (p+1)% elem.length;
		//Post
		for(int i = 0; i < numDatos.length; i++) numDatos[i]++;
		notifyAll();
	}
	
	public synchronized int consumir(int id) throws InterruptedException {
		//Pre
		while(numDatos[id] == 0) wait();
		//SC
		int dato = elem[c[id]];
		numLecturas[c[id]]++;
		numDatos[id]--;
		System.out.println("\tEl consumidor " + id + " consume el dato " + dato);
		//Post
		if(numLecturas[c[id]] == c.length) numHuecos++;
		c[id] = (c[id]+1)%elem.length;
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
