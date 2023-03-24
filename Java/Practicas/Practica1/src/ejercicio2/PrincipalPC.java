package ejercicio2;

import com.sun.jdi.PathSearchingVirtualMachine;

import java.util.Arrays;

public class PrincipalPC {
	public static void main(String[] args) {
		int s = 6, n = 30;
		Buffer b = new Buffer(s);
		Productor p = new Productor(b, n);
		Consumidor c = new Consumidor(b,n);
		p.start();
		c.start();
	}
}
