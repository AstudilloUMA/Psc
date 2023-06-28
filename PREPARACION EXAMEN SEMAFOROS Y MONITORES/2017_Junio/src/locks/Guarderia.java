package locks;

import java.util.concurrent.locks.*;

public class Guarderia {
	private long numBebes=0;
	private long numAdultos = 0;
	
	Lock l = new ReentrantLock();
	
	boolean puedeEntrarBebe=false;
	Condition condEntraBebe= l.newCondition();
	
	boolean puedeSalirAdulto=false;
	Condition condSalirAdulto=l.newCondition();
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		try {
			l.lock();
			while(!puedeEntrarBebe) {
				condEntraBebe.await();
			}
			
			if((numBebes+1)<=numAdultos*3) { //Esto es menor porque tengo en cuenta el siguiente bebé
				numBebes++;
				System.out.println("Ha entrado un bebé");
				System.out.println("ADULTOS: "+numAdultos+" BEBES: "+numBebes);				
				puedeEntrarBebe=true;
				condEntraBebe.signalAll();
			}else {
				puedeEntrarBebe=false;
			}
			
		}finally {
			l.unlock();
		}
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
			try {
				l.lock();
				numBebes--;
				System.out.println("Ha salido un bebé");
				System.out.println("ADULTOS: "+numAdultos+" BEBES: "+numBebes);
				if(numBebes<=(numAdultos-1)*3) {
					puedeSalirAdulto=true;
					condSalirAdulto.signalAll();
				}
				
			}finally {
				l.unlock();
			}
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		try {
			l.lock();
			numAdultos++;
			System.out.println("Ha entrado un adulto");
			System.out.println("ADULTOS: "+numAdultos+" BEBES: "+numBebes);
			if(numBebes+1<=numAdultos*3) {//Miro si con el nuevo adulto puedo meter otro bebé
				puedeEntrarBebe=true;
				condEntraBebe.signalAll();
			}
		}finally {
			l.unlock();
		}
		
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		try {
			l.lock();
			while(!puedeSalirAdulto) {
				condSalirAdulto.await();
			}
			if(numBebes<=(numAdultos-1)*3) {
				numAdultos--;
				System.out.println("Ha salido un adulto");
				System.out.println("ADULTOS: "+numAdultos+" BEBES: "+numBebes);
				puedeSalirAdulto=true;
				condSalirAdulto.signalAll();
			}else {
				puedeSalirAdulto=false;
			}
			
			
		}finally {
			l.unlock();
		}
		
	}

}
