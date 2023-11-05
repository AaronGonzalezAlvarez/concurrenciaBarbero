package concurrenciaBarbero;

public class Barbero extends Thread {
	BarberiaLock accion;
	
	public Barbero(BarberiaLock accion) {
		this.accion = accion;
	}
	
	
	public void run() {
		try {
			Thread.sleep(3000);
			accion.cortarPelo();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
