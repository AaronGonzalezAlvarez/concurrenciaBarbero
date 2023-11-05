package concurrenciaBarbero;


public class ConcurrenciaBarbero {

	public static void main(String[] args) {
		System.out.println("Barberia");
		Barberia b = new Barberia();
		Barbero barbero = new Barbero(b);
		barbero.start();
		for (int i = 0; i < 10; i++) {			
			Cliente cliente = (new Cliente(i, b));	
			cliente.start();
		}
	}

}
