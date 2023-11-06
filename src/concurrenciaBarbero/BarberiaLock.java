package concurrenciaBarbero;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberiaLock {
	private SillasSillon sillasSillon = new SillasSillon();

	private ReentrantLock lock = new ReentrantLock();
	private Condition sillasCondition = lock.newCondition();
	/*private Condition colaCondition = lock.newCondition();
	private Condition sillonCondition = lock.newCondition();*/
	private Random rand = new Random();

	public void accesoBarberia(int i) throws InterruptedException {
		lock.lock();
        try {
        	while (sillasSillon.completo()) {
                System.out.println("El cliente " + i + " espera en la cola para sentarse");
                //no se entra
                sillasCondition.await();
            }
        	
            while (sillasSillon.verSillon()) {
                System.out.println("El cliente " + i + " espera en la silla");
                sillasSillon.addAfoto();
                sillasCondition.await();
                sillasSillon.deleteAfoto();
            }
            
            sillasSillon.sillonOcupado();
            System.out.println("Cliente "+i + " esperando a ser pelado");
            sillasCondition.await();
            sillasSillon.sillonLibre();
            sillasCondition.signalAll();
            System.out.println("Cliente "+i + " ha salido satisfecho");
            //System.out.println("cola: " +cola+ " sillas: " +sillas+ " sillon: "+sillon);
        } finally {
            lock.unlock();
        }
	}

	public void cortarPelo() throws InterruptedException {
		lock.lock();
		try {
			boolean prueba = true;
			do {
				if (!sillasSillon.verSillon()) {
					System.out.println("El barbero está durmiendo.");
					//Thread.sleep((10 + rand.nextInt(11)) * 1000);
					Thread.sleep(1000);
				} else {
					int numeroAleatorio = rand.nextInt(2) + 1;
					if (numeroAleatorio == 1) {
						System.out.println("Sigo durmiendo.");
						//Thread.sleep((10 + rand.nextInt(11)) * 1000);
						Thread.sleep(2000);
					} else {
						System.out.println("Estoy pelando al cliente.");
						// cortando el pelo al cliente
						Thread.sleep(2000);
						// limpiar el suelo
						Thread.sleep(1000);
						System.out.println("he acabado de cortar el pelo");
						sillasCondition.signalAll();
						Thread.sleep(2000);
					}
				}
			}while(prueba);
		} finally {
			lock.unlock();
		}
	}
}
