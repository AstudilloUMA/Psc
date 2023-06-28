package agua;


import java.util.concurrent.*;

public class GestorAgua {
	private int numO, numH;
	private Semaphore mutex, hpuedoEntrar, opuedoEntrar, espera;
	public GestorAgua(){
		mutex = new Semaphore(1);
		hpuedoEntrar = new Semaphore(1);
		opuedoEntrar = new Semaphore(1);
		espera = new Semaphore(0);
		numO = 0;
		numH = 0;
	}
	private boolean mezclaLista(){
		return numH == 2 && numO == 1;
	}
	public void hListo(int id) throws InterruptedException{
		hpuedoEntrar.acquire();
		mutex.acquire();
		numH++;
		System.out.println("[" + id + "] Ha entrado un atomo de Hidrogeno, hay H(" + numH + "), O(" + numO + ")");
		if(numH < 2) {
			hpuedoEntrar.release();
		}
		if(!mezclaLista()){
			System.out.println("La molecula NO esta lista");
			mutex.release();
			espera.acquire();
		} else{
			System.out.println("La mezcla esta lista");
		}

		numH--;
		if(numH == 0 && numO == 0){
			System.out.println("Vuelta a empezar");
			hpuedoEntrar.release();
			opuedoEntrar.release();
			mutex.release();
		} else {
			espera.release(); //Despierta a otro atomo
		}
	}
	
	public void oListo(int id) throws InterruptedException{ 
		opuedoEntrar.acquire();
		mutex.acquire();
		numO++;
		System.out.println("[" + id + "] Ha entrado un atomo de Oxigeno, hay H(" + numH + "), O(" + numO + ")");

		if(!mezclaLista()){
			System.out.println("Aun no esta listo...");
			mutex.release();
			espera.acquire();
		} else {
			System.out.println("La mezcla ya esta lista!!!");
		}

		numO--;
		if(numO == 0 && numH == 0){
			hpuedoEntrar.release();
			opuedoEntrar.release();
			mutex.release();
		} else {
			espera.release(); //Despierto a otro atomo
		}
	}
}