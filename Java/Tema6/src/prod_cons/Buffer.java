package prod_cons;

import java.util.concurrent.Semaphore;

public class Buffer {
	private int var;
	//Aniadir atributos
	Semaphore hayDato; // cerrado si no hay datos para consumir
	Semaphore hayHueco; //cerrado si no se ha consumido el dato
	public Buffer() {
		var = 0;
		//inializar nuevos atributos
		hayDato = new Semaphore(0);
		hayHueco = new Semaphore(1);
	}
	
	public void producir(int nuevoDato) throws InterruptedException {
		//pre
		   hayHueco.acquire();
		//sc
		var = nuevoDato;
		System.out.println("Dato producido "+ var);
		//pos
		  hayDato.release();
	}
	
	public int consumir() throws InterruptedException {
		int temp;
		//pre
		   hayDato.acquire();
		//sc
		temp = var;
		System.out.println("\t\t Dato consumido "+ temp);
		//pos
		  hayHueco.release();
		return temp;
	}
	
	public static void main(String[] args) {
		Buffer b = new Buffer();
		Productor p = new Productor(b);
		Consumidor c0 = new Consumidor(b,0);		
		
		p.start();
		c0.start();
	}

}
