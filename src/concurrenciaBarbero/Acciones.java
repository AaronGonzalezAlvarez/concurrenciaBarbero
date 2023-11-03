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
    private Semaphore sillones = new Semaphore(1);
    
    public void pelar(int i) throws InterruptedException {
        sillas.acquire();
        System.out.println("Cliente " + i + " se sentó en una silla de espera.");
        
        sillones.acquire();
        sillas.release();
        
        System.out.println("Cliente " + i + " me estoy pelando.");
        Thread.sleep(5000); // Simulación del corte de pelo
        sillones.release();
    }
}
