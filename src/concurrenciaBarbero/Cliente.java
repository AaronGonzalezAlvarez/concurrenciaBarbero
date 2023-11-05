package concurrenciaBarbero;


public class Cliente extends Thread {

	int i;
	BarberiaLock barberia;

	public Cliente(int i, BarberiaLock barberia) {
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
