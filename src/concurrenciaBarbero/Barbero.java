package concurrenciaBarbero;

public class Barbero extends Thread {
	Acciones accion;
	
	public Barbero(Acciones accion) {
		this.accion = accion;
	}
	
	public void run() {
		try {
			Thread.sleep(10*1000);
			//accion.AdiestradorPuedeCantarPajaro();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
