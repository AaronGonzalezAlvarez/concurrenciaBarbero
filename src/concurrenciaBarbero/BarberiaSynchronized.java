package concurrenciaBarbero;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberiaSynchronized {
	
	private SillasSillon sillasSillon = new SillasSillon();
	private Object cliente = new Object();
	private Object barbero = new Object();
	private Object irse = new Object();
	private int personasPeladas = 0;
	private HashMap<String, Vuelta> clientes = new HashMap<String,Vuelta>();

	private Random rand = new Random();

	public synchronized void agregarClientes(int i) {
			if (!clientes.containsKey("cliente" + i)) {
				clientes.put("cliente" + i, new Vuelta());
			}
	}
	
	public void accesoBarberia(int i) throws InterruptedException {
		while (sillasSillon.completo()) {
			System.out.println("Voy a dar una vuelta " + i);
			Thread.sleep((5 + rand.nextInt(10)) * 1000);
			clientes.get("cliente" + i).addVuelta();
			if (clientes.get("cliente" + i).getVuelta() == 3) {
				System.err.println("ME voy y no vuelva " + i);
				synchronized (irse) {
					irse.wait();
				}
			}
			// System.out.println("El cliente " + i + " espera en la cola para sentarse");
			// cliente.wait();
		}

		synchronized (cliente) {
			if (clientes.get("cliente" + i).getVuelta() != 3) {
				while (sillasSillon.verSillon()) {
					System.out.println("El cliente " + i + " espera en la silla");
					sillasSillon.addAfoto();
					cliente.wait();
					sillasSillon.deleteAfoto();
				}
				System.out.println("Cliente " + i + " esperando a ser pelado");
				sillasSillon.sillonOcupado();
				cliente.wait();
				sillasSillon.sillonLibre();
				System.out.println("Cliente " + i + " satisfecho");
				cliente.notify();
			}
		}
	}

	public void cortarPelo() throws InterruptedException {
		synchronized (barbero) {

			boolean pruebaB = true;
			do {
				Thread.sleep(3000);
				if (sillasSillon.verSillon()) {
					int num = rand.nextInt(2);
					if(num == 1) {
						System.out.println("voy a dormir mas");
						Thread.sleep((3 + rand.nextInt(3)) * 1000);
					}else {
						personasPeladas++;
						System.out.println("Estoy pelando al cliente. y es el cliente del dia numero: " + personasPeladas);
						// cortando el pelo al cliente
						//Thread.sleep((5 + rand.nextInt(5)) * 1000);
						// limpiar el suelo
						Thread.sleep(5000);
						System.out.println("he acabado de cortar el pelo");
						synchronized (cliente) {
							cliente.notifyAll();
						}
					}					
				} else {
					System.out.println("Sigo durmiendo.");
					//Thread.sleep((10 + rand.nextInt(11)) * 1000);
					Thread.sleep(1000);
				}
			} while (pruebaB);
		}
	}
}