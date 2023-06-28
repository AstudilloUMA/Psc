package agua;

import java.util.concurrent.Semaphore;

public class GestorAgua {

	private int nO, nH, nA;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore entraH = new Semaphore(1);
	private Semaphore entraO = new Semaphore(1);
	private Semaphore moleculaLista = new Semaphore(0);
	
	public GestorAgua() {
		nO = 0;
		nH = 0;
		nA = 0;
	}
	
	
	public void hListo(int id) throws InterruptedException {
		//Pre
		entraH.acquire();
		mutex.acquire();
		//Sc
		nH++;
		nA++;
		System.out.println("Entra el atomo de hidrogendo " + id + ". Atomos: " + nA);
		//Post
		if(nA == 3)
		{
			System.out.println("\t\tSE HA CREADO UNA MOLECULA");
			nO = 0;
			nH = 0;
		}
		else
		{
			if(nH < 2) entraH.release();
			mutex.release();
			moleculaLista.acquire();
			mutex.acquire();
		}
		nA--;
		if(nA > 0) moleculaLista.release();
		else
		{
			entraH.release();
			entraO.release();
		}
		mutex.release();
	}
	
	public void oListo(int id) throws InterruptedException {
		//Pre
		entraO.acquire();
		mutex.acquire();
		//SC
		nO++;
		nA++;
		System.out.println("\tEntra el atomo de oxigeno " + id + ". Atomos: " + nA);
		//Post
		if(nA == 3)
		{
			System.out.println("\t\tSE HA CREADO UNA MOLECULA");
			nO = 0;
			nH = 0;
		}
		else
		{
			mutex.release();
			moleculaLista.acquire();
			mutex.acquire();
		}
		nA--;
		if(nA > 0) moleculaLista.release();
		else
		{
			entraH.release();
			entraO.release();
		}
		mutex.release();
	}

	private void sacarMolecula() throws InterruptedException {
		moleculaLista.acquire();
	}
	
	public static void main(String[] args) {
		GestorAgua gestor = new GestorAgua();
		Oxigeno oxigenos[] = new Oxigeno[5];
		Hidrogeno hidrogenos[] = new Hidrogeno[5];
		for(int i = 0 ; i < 5; i++) {
			oxigenos[i] = new Oxigeno(i, gestor);
			hidrogenos[i] = new Hidrogeno(i, gestor);
		}

		for(int i = 0 ; i < 5; i++) {
			oxigenos[i].start();
			hidrogenos[i].start();
		}
	}

}
