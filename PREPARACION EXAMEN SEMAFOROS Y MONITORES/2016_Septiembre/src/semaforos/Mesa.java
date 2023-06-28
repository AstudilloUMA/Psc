package semaforos;

import java.util.concurrent.*;
public class Mesa {
	private int nJugadores;
	private int numTiradas;
	private int idGanador;
	private int numCaras;
	
	private Semaphore mutex = new Semaphore (1);
	private Semaphore [] estaEsperando;
	private boolean[] resTiradas;
	public Mesa(int N){
		nJugadores=N;
		idGanador=N;
		numTiradas=0;
		numCaras=0;
		estaEsperando= new Semaphore[nJugadores];
		resTiradas= new boolean [nJugadores];
		
		for(int i=0;i<nJugadores;i++) {
			estaEsperando[i]= new Semaphore(0);
			resTiradas[i]=false;
		}
	}
	
	
	
	/**
	 * 
	 * @param id del jugador que llama al método
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ningún jugador 
	 * ha ganado y hay que repetir la partida. Si  devuelve un número menor que N, es el id del
	 * jugador ganador.
	 * 
	 * Un jugador llama al método nuevaJugada cuando quiere poner
	 * el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el último, espera a que el resto de 
	 * los jugadores pongan sus jugadas sobre la mesa.
	 * El último jugador comprueba si hay o no un ganador, y despierta
	 * al resto de jugadores
	 *  
	 */
	public int nuevaJugada(int id,boolean cara) throws InterruptedException{
		mutex.acquire();
		System.out.println("Va a tirar el jugador "+id);
		if(cara) {
			System.out.println("Ha sacado cara");
			resTiradas[id]=true;
		}
		numTiradas++;
		System.out.println("El numero de tiradas es "+numTiradas);
		if(numTiradas< nJugadores-1) {
			estaEsperando[id].acquire(); //Bloqueo el semáforo
		}else { //compruebo si hay algún ganador
			for(int i=0;i<nJugadores;i++) {
				mutex.release();
				estaEsperando[i].release();
				
			}
			System.out.println("Ha llegado la última hebraaaaa");
			for(int i=0;i<nJugadores;i++) {
				if(numCaras==0 && resTiradas[i]) {//No tenemos constancia de que haya habido mas caras
					idGanador=i;
				}else if(numCaras > 0 && numCaras<nJugadores-1 && resTiradas[i]) {
					idGanador= nJugadores;
				}else if(numCaras == nJugadores-1) {
					for(int j=0;j<nJugadores;j++) {
						if(!resTiradas[j]) {
							idGanador=j;
						}
					}
				}
				
			}
			//Vuelvo a recorrer todo el bucle liberando los semáforors
			numCaras=0;
			numTiradas=0;
			
		}
		
		mutex.release();
		return idGanador;
	}
}
