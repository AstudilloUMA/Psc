package pizza;

import java.util.concurrent.locks.*;

public class Mesa {
		
		private Lock l = new ReentrantLock();
	
		private int raciones = 0;
		
		private boolean cogePizza = false;
		private Condition cEstudiar = l.newCondition();
		
		private boolean haLlamado = false;
		private Condition cLlamar = l.newCondition();
		
		private boolean haPagado = false;
		private Condition cPagar = l.newCondition();
		
		
		
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
		
			try {
				l.lock();
				if(this.raciones>0 ) {
					System.out.println("El estudiante se come un trozo");
					this.raciones--;//Se come un trozo de pizza.
				}if(this.raciones == 0 && !haLlamado) {
					System.out.println("NO HAY PIZZA Y NADIE HA LLAMADO, LLAMO AL PIZZERO");
					haLlamado=true;
					cLlamar.signalAll();
					while(!cogePizza) {
					cEstudiar.await();	
					}
					System.out.println("LLeg� el pizzero, espera a que le paguen");
					while(!haPagado) {
						cPagar.await();
					}
					
				}if(this.raciones == 0 && haLlamado) {
					while(!cogePizza) {
						cEstudiar.await();
					}
				}
				
			}finally {
				l.unlock();
			}
			
	}


	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * @throws InterruptedException 
	 */
	public void nuevoCliente() throws InterruptedException{
		try {
			l.lock();
			System.out.println("El pizzero ha llegado,entrega la pizza");
			this.raciones++;
			this.cogePizza=true;
			this.cEstudiar.signalAll();
			while(!haPagado) {
				cPagar.await();
			}
			System.out.println("El pizzero se ha ido");
			
		}finally {
			
		}
		
	}
	

/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * @throws InterruptedException 
	 */
	public void nuevaPizza() throws InterruptedException{
		}

}
