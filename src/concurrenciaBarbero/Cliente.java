package concurrenciaBarbero;


public class Cliente extends Thread {

	int i;
	Acciones accion;

	public Cliente(int i, Acciones accion) {
		this.i = i;
		this.accion = accion;
	}

	
	public void run() {
		try {
			Thread.sleep(1*1000);
			accion.aLaEspera(i);
			Thread.sleep(2*1000);
			accion.sentarSilla(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	//apartado 4
	/*public void run() {
		try {
			Thread.sleep(8*1000);
			accion.alaEspera(i, tipo);
			Thread.sleep(5*1000);					
			accion.cantar(i,tipo);
			Thread.sleep(2*1000);
			accion.dejarCantar(i, tipo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
}
