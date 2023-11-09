package concurrenciaBarbero;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BarberiaLock {
	private SillasSillon sillasSillon = new SillasSillon();

	private ReentrantLock lock = new ReentrantLock();
	private ReentrantLock lockBarbero = new ReentrantLock();
	private Condition sillasCondition = lock.newCondition();
	//private Condition colaCondition = lock.newCondition();
	private Condition sillonCondition = lock.newCondition();
	private Condition barberoCondition = lock.newCondition();
	private Condition irseCondition = lock.newCondition();
	private Random rand = new Random();  
	private HashMap<String, Vuelta> clientes = new HashMap<String,Vuelta>(); 
	private int clientesPelados =0;
	
	
	public synchronized void agregarClientes(int i) {
		if (!clientes.containsKey("cliente" + i)) {
			clientes.put("cliente" + i, new Vuelta());
		}
	}

	public void accesoBarberia(int i) throws InterruptedException {

		Thread.sleep((1 + rand.nextInt(2)) * 1000);
		while (sillasSillon.completo()) {
			System.out.println("Voy a dar una vuelta " + i);
			Thread.sleep((5 + rand.nextInt(10)) * 1000);
			clientes.get("cliente" + i).addVuelta();
			if (clientes.get("cliente" + i).getVuelta() == 3) {
				System.err.println("ME voy y no vuelva " + i);
				lock.lock();
				try {
					irseCondition.await();
				} finally {
					lock.unlock();
				}

				lock.unlock();
			}
		}
		lock.lock();
		try {
			while (sillasSillon.verSillon()) {
				System.out.println("El cliente " + i + " espera en la silla");
				sillasSillon.addAfoto();
				sillasCondition.await();
				sillasSillon.deleteAfoto();
			}

			sillasSillon.sillonOcupado();
			System.out.println("Cliente " + i + " esperando a ser pelado");
			barberoCondition.signal();
			sillonCondition.await();
			sillasCondition.signal();
			sillasSillon.sillonLibre();
			System.out.println("Cliente " + i + " ha salido satisfecho");
			// sillasCondition.signalAll();
			// colaCondition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public void cortarPelo() throws InterruptedException {
		lockBarbero.lock();
		try {
			while (true) {
				if (!sillasSillon.verSillon()) {
					System.out.println("El barbero está durmiendo.");
					// Thread.sleep((10 + rand.nextInt(11)) * 1000);
					Thread.sleep(1000);
				} else {
					int numeroAleatorio = rand.nextInt(2) + 1;
					if (numeroAleatorio == 1) {
						System.out.println("Sigo durmiendo.");
						// Thread.sleep((10 + rand.nextInt(11)) * 1000);
						Thread.sleep(2000);
					} else {
						System.out.println("Estoy pelando al cliente.");
						// cortando el pelo al cliente
						Thread.sleep(2000);
						// limpiar el suelo
						Thread.sleep(1000);
						clientesPelados++;
						System.out.println("he acabado de cortar el pelo al cliente: he pelado a " + clientesPelados+ " personas");
						lock.lock();
						try {
							sillonCondition.signal();
							// colaCondition.signalAll();
							barberoCondition.await();
							Thread.sleep(2000);
						} finally {
							lock.unlock();
						}
					}
				}
			}

		} finally {
			lockBarbero.unlock();
		}
	}
	
	private boolean hayHilosEsperandoEnCondicion(Condition condition) {
        return lock.hasWaiters(condition);
    }
}