package Junio2018;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {

	Lock l = new ReentrantLock();
	private boolean llamar = false;
	private boolean comePrimero = false;

	private boolean esta = false;
	Condition okComer = l.newCondition();
	Condition okLlevar = l.newCondition();

	private int nPorciones;
	
	/**
	 * 
	 * @param id
	 * El estudiante id quiere una ración de pizza. 
	 * Si hay una ración la coge y sigue estudiante.
	 * Si no hay y es el primero que se da cuenta de que la mesa está vacía
	 * llama al pizzero y
	 * espera hasta que traiga una nueva pizza. Cuando el pizzero trae la pizza
	 * espera hasta que el estudiante que le ha llamado le pague.
	 * Si no hay pizza y no es el primer que se da cuenta de que la mesa está vacía
	 * espera hasta que haya un trozo para él.
	 * @throws InterruptedException 
	 * 
	 */
	public void nuevaRacion(int id) throws InterruptedException{
		l.lock();
		try
		{
			System.out.printf("\tEl estudiante %d QUIERE comerse una pizza\n",id);
			if(nPorciones > 0)
			{
				while(comePrimero) okComer.await();
				nPorciones--;
				System.out.printf("El estudiante %d se COME una porcion. TOTAL: %d\n",id,nPorciones);
				okComer.signalAll();
			}
			if (nPorciones == 0 && !llamar)
			{
				comePrimero = true;
				System.out.printf("\t\tEl estudiante %d LLAMA al pizzero\n",id);
				llamar = true;
				okLlevar.signal();
				while(nPorciones == 0 || !esta) okComer.await();
				nPorciones--;
				System.out.printf("El estudiante %d se COME una porcion. TOTAL: %d\n",id,nPorciones);
				comePrimero = false;
			}
		}
		finally {
			l.unlock();
		}
	
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public void nuevoCliente() throws InterruptedException{
		l.lock();
		try
		{
			System.out.println("El pizzero ha llevado una pizza");
			nPorciones += 8;
			esta = true;
			llamar = false;
			okComer.signalAll();
		}
		finally{
			l.unlock();
		}

	}
	

/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public void nuevaPizza() throws InterruptedException{
		l.lock();
		try
		{
			while(!llamar) okLlevar.await();
		}
		finally{
			l.unlock();
		}
	}

}
