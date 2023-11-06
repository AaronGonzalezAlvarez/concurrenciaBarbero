package concurrenciaBarbero;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberiaSynchronized {
	
	private Object sillasCondition = new Object();
	private Object colaCondition = new Object();
	private Object sillonCondition = new Object();
	private SillasSillon sillasSillon = new SillasSillon();
	private Object prueba = new Object();
	private Object barbero = new Object();
	private int personasPeladas = 0;

	private Random rand = new Random();

	public void accesoBarberia(int i) throws InterruptedException {
		synchronized (prueba) {
			while (sillasSillon.completo()) {
				System.out.println("El cliente " + i + " espera en la cola para sentarse");
				prueba.wait();
			}
			while (sillasSillon.verSillon()) {
				System.out.println("El cliente " + i + " espera en la silla");
				sillasSillon.addAfoto();
				prueba.wait();
				sillasSillon.deleteAfoto();
			}
			System.out.println("Cliente " + i + " esperando a ser pelado");
			sillasSillon.sillonOcupado();
			prueba.wait();
			sillasSillon.sillonLibre();
			System.out.println("Cliente " + i + " satisfecho");
			prueba.notify();
		}
	}

	public void cortarPelo() throws InterruptedException {
		synchronized (barbero) {

			boolean pruebaB = true;
			do {
				Thread.sleep(3000);
				if (sillasSillon.verSillon()) {
					personasPeladas++;
					System.out.println("Estoy pelando al cliente. y es el cliente del dia numero: " + personasPeladas);
					// cortando el pelo al cliente
					// Thread.sleep(7000);
					// limpiar el suelo
					Thread.sleep(2000);
					System.out.println("he acabado de cortar el pelo");
					synchronized (prueba) {
						prueba.notifyAll();
					}
				} else {
					System.out.println("Sigo durmiendo.");
					Thread.sleep(1000);
				}
			} while (pruebaB);

		}
	}
}