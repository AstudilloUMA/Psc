package junio2018_monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {

	private int raciones = 0;
	private boolean hayRacion = true;
	private boolean hanLlamado = false;
	private boolean pizzaLista;
	Lock l = new ReentrantLock();
	Condition okLlamar = l.newCondition();
	Condition okComer = l.newCondition();
	Condition okEsperar = l.newCondition();
	Condition okPagada = l.newCondition();

	
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
			//Pre
			while(!hayRacion) okComer.await();
			if(raciones == 0)
			{
				System.out.println("\tNo quedan porciones. LLAMA: " + id);
				hayRacion = false;
				pizzaLista = false;
				hanLlamado = true;
				okLlamar.signal();
				okPagada.await();
				System.out.println("\t" + id + " ha pagado la pizza");
			}
			//Sc
			raciones--;
			System.out.println(id + " come pizza. RACIONES: " + raciones);
			if(raciones == 7) okComer.signalAll();
			//Post
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
			//Pre
			while(!hanLlamado) okLlamar.await();
			//Sc
			System.out.println("\tHan llamado");
			//Post
		} finally {
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
			//Pre

			//Sc
			raciones = 8;
			hanLlamado = false;
			hayRacion = true;
			pizzaLista = true;
			System.out.println("\tPIZZA ENTREGADA");
			okPagada.signal();
			//Post
		}
		finally {
			l.unlock();
		}
	}

}
