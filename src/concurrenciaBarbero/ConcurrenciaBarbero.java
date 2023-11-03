package concurrenciaBarbero;



public class ConcurrenciaBarbero {

	public static void main(String[] args) {
		Acciones b = new Acciones();
		//Adiestrador adiestrador = new Adiestrador(b);
		//adiestrador.start();
		for (int i = 0; i < 10; i++) {			
			Cliente cliente = (new Cliente(i, b));	
			cliente.start();
			
		}
	}

}
