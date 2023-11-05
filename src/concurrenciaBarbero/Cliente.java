package concurrenciaBarbero;


public class Cliente extends Thread {

	int i;
	Barberia barberia;

	public Cliente(int i, Barberia barberia) {
		this.i = i;
		this.barberia = barberia;
	}

	
	public void run() {
		try {
			Thread.sleep(1*1000);
			barberia.accesoBarberia(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
