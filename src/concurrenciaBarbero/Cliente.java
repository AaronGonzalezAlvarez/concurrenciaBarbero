package concurrenciaBarbero;

import java.util.Random;

public class Cliente extends Thread {

	int i;
	BarberiaSynchronized barberia;
	private Random rand = new Random();

	public Cliente(int i, BarberiaSynchronized barberia) {
		this.i = i;
		this.barberia = barberia;
	}

	
	public void run() {
		try {
			Thread.sleep((1 + rand.nextInt(5)) * 1000);
			barberia.agregarClientes(i);
			barberia.accesoBarberia(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
