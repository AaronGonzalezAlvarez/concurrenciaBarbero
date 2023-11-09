package concurrenciaBarbero;

public class Barbero extends Thread {
	BarberiaSynchronized accion;
	
	public Barbero(BarberiaSynchronized accion) {
		this.accion = accion;
	}
	
	
	public void run() {
		try {
			Thread.sleep(2000);
			accion.cortarPelo();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
