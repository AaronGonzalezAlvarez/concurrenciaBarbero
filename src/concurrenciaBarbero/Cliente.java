package concurrenciaBarbero;


public class Cliente extends Thread {

	int i;
	BarberiaSynchronized barberia;

	public Cliente(int i, BarberiaSynchronized barberia) {
		this.i = i;
		this.barberia = barberia;
	}

	
	public void run() {
		try {
			barberia.accesoBarberia(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
