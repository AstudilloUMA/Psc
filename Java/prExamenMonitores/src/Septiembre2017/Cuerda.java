package Septiembre2017;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cuerda {

	Lock l = new ReentrantLock();

	boolean ocupadoNS = false;
	Condition okNS = l.newCondition();

	boolean ocupadoSN = false;
	Condition okSN = l.newCondition();

	private int nBabuinos = 0;

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		l.lock();
		try
		{
			System.out.printf("\tEl babuino %d quiere ENTRAR desde NORTE\n",id);
			while(ocupadoSN || nBabuinos+1 > 3) okNS.await();
			ocupadoNS = true;
			nBabuinos++;
			System.out.printf("ENTRA el babuino %d en NORTE. Babuinos: %d\n",id,nBabuinos);
		}
		finally {
			l.unlock();
		}
	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionSN(int id) throws InterruptedException{
		l.lock();
		try
		{
			System.out.printf("\tEl babuino %d quiere ENTRAR desde SUR\n",id);
			while(ocupadoNS || nBabuinos+1 > 3) okSN.await();
			ocupadoSN = true;
			nBabuinos++;
			System.out.printf("ENTRA el babuino %d en SUR. Babuinos: %d\n",id,nBabuinos);
		}
		finally {
			l.unlock();
		}
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		l.lock();
		try
		{
			nBabuinos--;
			System.out.printf("LLEGA el babuino %d a SUR. Babuinos: %d\n",id,nBabuinos);
			if(nBabuinos == 0)
			{
				ocupadoNS = false;
				okSN.signalAll();
			}
		}
		finally {
			l.unlock();
		}
			
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		l.lock();
		try
		{
			nBabuinos--;
			System.out.printf("LLEGA el babuino %d a NORTE. Babuinos: %d\n",id,nBabuinos);
			if(nBabuinos == 0)
			{
				ocupadoSN = false;
				okNS.signalAll();
			}

		}
		finally {
			l.unlock();
		}
	}	
		
}
