package concurrenciaBarbero;


public class ConcurrenciaBarbero {

	public static void main(String[] args) {
		System.out.println("Barberia");
		//BarberiaSemaforo b = new BarberiaSemaforo();
		BarberiaLock b = new BarberiaLock();
		//BarberiaSynchronized b = new BarberiaSynchronized();
		Barbero barbero = new Barbero(b);
		barbero.start();
		for (int i = 0; i < 10; i++) {			
			Cliente cliente = (new Cliente(i, b));	
			cliente.start();
		}
	}

}
