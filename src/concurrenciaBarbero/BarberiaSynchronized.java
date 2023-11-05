package concurrenciaBarbero;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberiaSynchronized {
	/*private int cola = 0;
	private int sillas = 0;
	private int sillon = 0;*/
	
	private AtomicInteger cola = new AtomicInteger(0);
    private AtomicInteger sillas = new AtomicInteger(0);
    private AtomicBoolean sillon = new AtomicBoolean(false);

	private Object sillasCondition = new Object();
	private Object colaCondition = new Object();
	private Object sillonCondition = new Object();
	private Object barbero = new Object();

	private Random rand = new Random();

	public void accesoBarberia(int i) throws InterruptedException {
		synchronized (colaCondition) {
			while (sillas.get() == 5) {
				System.out.println("El cliente " + i + " espera en la cola para sentarse");
				cola.incrementAndGet();
				colaCondition.wait();
				cola.decrementAndGet();
			}
		}

		synchronized (sillasCondition) {
			while (sillon.get()) {
				System.out.println("El cliente " + i + " espera en la silla");
				sillas.incrementAndGet();
				sillasCondition.wait();
				sillas.decrementAndGet();
			}
		}

		synchronized (sillonCondition) {
			System.out.println("Cliente " + i + " esperando a ser pelado");
			sillon.set(true);
			sillonCondition.wait();	
			sillon.set(false);
			System.out.println("Cliente " + i + " satisfecho");		
			synchronized (sillasCondition) {
				sillasCondition.notify();
			}
			synchronized (colaCondition) {
				colaCondition.notify();
			}
		}
	}

	public void cortarPelo() throws InterruptedException {		
			boolean prueba = true;
			do {
				if(sillon.get()) {
					System.out.println("Estoy pelando al cliente.");
					// cortando el pelo al cliente
					//Thread.sleep(7000);
					// limpiar el suelo
					Thread.sleep(2000);
					System.out.println("he acabado de cortar el pelo");
					synchronized (sillonCondition) {
						sillonCondition.notify();
						}
				}else {
					System.out.println("Sigo durmiendo.");
					Thread.sleep(4000);
				}
				Thread.sleep(1000);
			}while(prueba);
	}
}
