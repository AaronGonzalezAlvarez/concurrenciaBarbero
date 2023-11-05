package concurrenciaBarbero;

public class Barbero extends Thread {
	Barberia accion;
	
	public Barbero(Barberia accion) {
		this.accion = accion;
	}
	
	
	public void run() {
		try {
			Thread.sleep(5*1000);
			accion.cortarPelo(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
}
