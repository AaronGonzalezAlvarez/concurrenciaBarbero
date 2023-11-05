package concurrenciaBarbero;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BarberiaLock {
	
	private AtomicInteger cola = new AtomicInteger(0);
    private AtomicInteger sillas = new AtomicInteger(0);
    private AtomicBoolean sillon = new AtomicBoolean(false);

	private ReentrantLock lock = new ReentrantLock();
	private Condition sillasCondition = lock.newCondition();
	private Condition colaCondition = lock.newCondition();
	private Condition sillonCondition = lock.newCondition();
	private Random rand = new Random();

	public void accesoBarberia(int i) throws InterruptedException {
		lock.lock();
        try {
        	while (sillas.get() == 5) {
                System.out.println("El cliente " + i + " espera en la cola para sentarse");
                //no se entra
                cola.incrementAndGet();
                colaCondition.await();
                cola.decrementAndGet();
            }
        	
            while (sillon.get()) {
                System.out.println("El cliente " + i + " espera en la silla");
                sillas.incrementAndGet();
                sillasCondition.await();
                sillas.decrementAndGet();
            }
            sillon.set(true);
            System.out.println("Cliente "+i + " esperando a ser pelado");
            sillonCondition.await();
            sillon.set(false);
			sillasCondition.signal();
			colaCondition.signal();
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
				if (!sillon.get()) {
					System.out.println("El barbero está durmiendo.");
					Thread.sleep((10 + rand.nextInt(11)) * 1000);
				} else {
					int numeroAleatorio = rand.nextInt(2) + 1;
					if (numeroAleatorio == 1) {
						System.out.println("Sigo durmiendo.");
						//Thread.sleep((10 + rand.nextInt(11)) * 1000);
						Thread.sleep(2000);
					} else {
						System.out.println("Estoy pelando al cliente.");
						// cortando el pelo al cliente
						Thread.sleep(7000);
						// limpiar el suelo
						Thread.sleep(2000);
						System.out.println("he acabado de cortar el pelo");
						sillonCondition.signal();
						Thread.sleep(1000);
					}
				}
			}while(prueba);
		} finally {
			lock.unlock();
		}
	}
}
