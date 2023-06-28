package diciembre2017_monitores;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {
    //0 - piedra, 1 - papel, 2 - tijeras
	Lock l = new ReentrantLock();
	private int juegos = 0;
	private int jugadas[] = new int[3];
	private boolean jugar = false;
	Condition okJugar = l.newCondition();
	Condition okGanador = l.newCondition();
	private int ganador = -1;


	/**
	 * 
	 * @param jug jugador que llama al m�todo (0,1,2)
	 * @param juego jugada del jugador (0-piedra,1-papel, 2-tijeras)
	 * @return  si ha habido un ganador en esta jugada se devuelve 
	 *          la jugada ganadora
	 *         o -1, si no ha habido ganador
	 * @throws InterruptedException
	 * 
	 * El jugador que llama a este m�todo muestra su jugada, y espera a que 
	 * est�n la de los otros dos. 
	 * Hay dos condiciones de sincronizaci�n
	 * CS1- Un jugador espera en el m�todo hasta que est�n las tres jugadas
	 * CS2- Un jugador tiene que esperar a que finalice la jugada anterior para
	 *     empezar la siguiente
	 * 
	 */
	public int nuevaJugada(int jug,int juego) throws InterruptedException{
		l.lock();
		try
		{
			//Pre
			while(juegos == 3) okJugar.await();
			//Sc
			jugadas[jug] = juego;
			juegos++;
			System.out.printf("El jugador %d ha sacado %d\n", jug, juego);
			//Post
			if (juegos < 3) okGanador.await();
			else
			{
				ganador = ganador();
				if(ganador == -1) System.out.println("EMPATE");
				juegos = 0;
				okGanador.signalAll();
				okJugar.signal();
			}
		}
		finally {
			l.unlock();
		}
		return ganador;
	}

	public int ganador()
	{
		int res = -1;
		if(gana(jugadas[0],jugadas[1]) && gana(jugadas[0],jugadas[2])) res = 0;
		else if(gana(jugadas[1],jugadas[0]) && gana(jugadas[1],jugadas[2])) res = 1;
		else if(gana(jugadas[2],jugadas[0]) && gana(jugadas[2],jugadas[1])) res = 2;
		return res;
	}

	public boolean gana (int a, int b)
	{
		boolean res = false;
		if(a == 0)
		{
			if(b == 1) res = true;
		}
		else if(a == 1)
		{
			if(b == 2) res = true;
		}
		else if(a == 2)
		{
			if(b == 0) res = true;
		}
		return res;
	}


}
