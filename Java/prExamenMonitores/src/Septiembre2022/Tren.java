package Septiembre2022;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tren {
	Lock l = new ReentrantLock();
	Condition okSubir = l.newCondition();
	Condition okBajar = l.newCondition();
	Condition okViajar = l.newCondition();

	private int n1, n2, n1Max, n2Max;
	boolean completo = false;
	boolean viajando = false;
	boolean bajando = false;
	boolean subiendo = true;
	private int nPasajeros, MAX_PASAJEROS, nVagon, nBajados;

	public Tren()
	{
		this.nPasajeros = 0;
		this.nBajados = 0;
		this.MAX_PASAJEROS = 5;
		this.nVagon = 1;
	}

	public void viaje(int id) throws InterruptedException {
		l.lock();
		try
		{
			while(viajando || bajando) okSubir.await();
			nPasajeros++;
			System.out.printf("El pasajero %d se sube al vagon %d\n",id,nVagon);
			if(nPasajeros == MAX_PASAJEROS) nVagon++;
			if(nPasajeros == 2*MAX_PASAJEROS)
			{
				nVagon = 1;
				viajando = true;
				subiendo = false;
				okViajar.signal();
				nBajados = 0;
			}

			while(viajando || subiendo) okBajar.await();
			nBajados++;
			System.out.printf("El pasajero %d se baja del vagon %d\n",id,nVagon);
			if(nBajados == MAX_PASAJEROS) nVagon++;
			if(nBajados == 2*MAX_PASAJEROS)
			{
				System.out.println("***************************************************");
				nVagon = 1;
				viajando = false;
				bajando = false;
				subiendo = true;
				okSubir.signalAll();
				nPasajeros = 0;
			}
		}
		finally {
			l.unlock();
		}

			
	}

	public void empiezaViaje() throws InterruptedException {
		l.lock();
		try
		{
			while(subiendo || bajando) okViajar.await();
			viajando = true;
			System.out.println("        Maquinista:  empieza el viaje");
		}
		finally {
			l.unlock();
		}
	}
	public void finViaje() throws InterruptedException  {
		l.lock();
		try
		{
			viajando = false;
			bajando = true;
			System.out.println("        Maquinista:  fin del viaje");
			okBajar.signalAll();
		}
		finally {
			l.unlock();
		}
	}
}
