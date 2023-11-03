package concurrenciaBarbero;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Acciones {
    //5 sillas y un sillon
    private Semaphore cola = new Semaphore(30);
    private Semaphore sillas = new Semaphore(5);
    private Semaphore sillon = new Semaphore(1);
    
    public void aLaEspera(int i) throws InterruptedException {
    	
    	while(sillas.availablePermits() == 0) {
        	System.out.println("Cliente " + i +" En la cola para sentarme");
        	cola.acquire();
    	} 
    	sillas.acquire();
    	
    }
    
    public void sentarSilla(int i) {
    	System.out.println("Cliente " + i +" En la cola para pelarme");
    	sillas.release();
    	cola.release();
    }
}
