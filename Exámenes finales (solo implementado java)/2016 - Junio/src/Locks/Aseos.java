package Locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aseos {

	Lock l = new ReentrantLock();

	boolean hanDejadoPasarAAlguien = true;  //Si no pongo esto los limpiadores van a parecer los de la uma antes de la pandemia
	Condition condCliente = l.newCondition();

	boolean quierenLimpiar = true;
	Condition condLimpiadores = l.newCondition();

	int personasDentro = 0;

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		try {
			l.lock();

			while (quierenLimpiar) {
				condCliente.await();
			}
			personasDentro++;
			hanDejadoPasarAAlguien = true;
			System.out.format("Entra la persona %d. Hay %d personas dentro.\n", id, personasDentro);
			condLimpiadores.signalAll();

		} finally {
			l.unlock();
		}

	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 */
	public void salgoAseo(int id){
		try {
			l.lock();

			personasDentro--;
			System.out.format("Sale la persona %d. Hay %d personas dentro.\n", id, personasDentro);
			if (personasDentro == 0) {
				condLimpiadores.signalAll();
			}

		} finally {
			l.unlock();
		}

	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 * 
	 */
    public void entraEquipoLimpieza() throws InterruptedException {
    	try {
    		l.lock();

			while (!hanDejadoPasarAAlguien) {
				condLimpiadores.await();
			}
			System.out.println("\tLos limpiadores quieren entrar.");
			quierenLimpiar = true;
			while (personasDentro != 0) {
				condLimpiadores.await();
			}
			System.out.println("\tLos limpiadores entran.");

		} finally {
    		l.unlock();
		}
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza(){
    	try {
    		l.lock();

			quierenLimpiar = false;
			System.out.println("\tLos limpiadores salen.\n");
			hanDejadoPasarAAlguien = false;
			condCliente.signalAll();

		} finally {
    		l.unlock();
		}
	}
}
