package Junio2022;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SupermercadoMonitores implements Supermercado{

	Lock l = new ReentrantLock();

	Condition okPagar = l.newCondition();

	private boolean cobrando = false;
	Condition okCobrar = l.newCondition();

	private boolean abierto = true;

	private Cajero permanente;

	private int nClientes;


	
	
	public SupermercadoMonitores() throws InterruptedException {
		permanente = new Cajero(this, true); //crea el primer cajero, el permanente
		permanente.start();
		this.nClientes = 0;
	}
	
	@Override
	public void fin() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nuevoCliente(int id) throws InterruptedException {
		while(cobrando) okPagar.await();
		if(abierto)
		{

		}
		
	}

	@Override
	public boolean permanenteAtiendeCliente(int id) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;//borrar
	}

	@Override
	public boolean ocasionalAtiendeCliente(int id) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;//borrar
	}

}
