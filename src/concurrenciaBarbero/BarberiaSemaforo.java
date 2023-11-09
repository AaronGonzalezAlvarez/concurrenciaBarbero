package concurrenciaBarbero;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/*
Implementar una solución para el problema del barbero durmiente. El problema consiste en una barbería donde trabaja un barbero,
 la cual cuenta con un sillón para cortar el pelo y varias sillas para esperar (supongamos que N=5).
Al llegar un cliente, si hay sillas de espera se sienta en una, si no, espera a poder sentarse.
Los clientes que están en las sillas de espera intentan sentarse en el sillón de cortar el pelo
 en cuanto lo ven vacío y esperan a que el barbero venga a cortarles el pelo.
 
Si el barbero ve que hay un cliente en el sillón de cortar el pelo toma aleatoriamente una las siguientes decisiones:
    1) Hacerse el loco y continuar dormitando de 3 a 6 segundos
    2) Cortarle el pelo, por lo que tardara entre 5 y 10 segundos
Una vez cortado el pelo tardara otros 5 segundos en limpiar el suelo.
Si no ve ningún cliente en la silla de cortar el pelo dormitara de 10 a 20 segundos.
*/


public class BarberiaSemaforo {
    //5 sillas y un sillon
    private Semaphore sillas = new Semaphore(5);
    private Semaphore sillones = new Semaphore(1);
    private Semaphore irse = new Semaphore(0);
    private Random rand = new Random();  
	private HashMap<String, Vuelta> clientes = new HashMap<String,Vuelta>(); 
	private int clientesPelados =0;
    
	public synchronized void agregarClientes(int i) {
		if (!clientes.containsKey("cliente" + i)) {
			clientes.put("cliente" + i, new Vuelta());
		}
	}
    
    public void accesoBarberia(int i) throws InterruptedException {
		while(sillas.availablePermits() == 0) {
			System.out.println("Voy a dar una vuelta " + i);
			Thread.sleep((5 + rand.nextInt(10)) * 1000);
			clientes.get("cliente" + i).addVuelta();
			if (clientes.get("cliente" + i).getVuelta() == 3) {
				System.err.println("ME voy y no vuelva " + i);
				irse.acquire();
			}
		}
    	
    	//System.out.println("Cliente " + i + " en la cola para sentarme en la silla.");
	    sillas.acquire();
	    System.out.println("Cliente " + i + " se sentó en una silla de espera.");
	    
	    sillones.acquire();
	    sillas.release();
	    System.out.println("Cliente " + i + " a la espera de cortarme el pelo");
	}
    
    public void cortarPelo() {
        try {
            while (true) {
            	//sillones.availablePermits() == 1 significa que no se esta usando y por lo tanto puede descansar
                if (sillones.availablePermits() == 1) {
                    System.out.println("El barbero está durmiendo.");
                    Thread.sleep((10 + rand.nextInt(11)) * 1000);
                }else {
                    int numeroAleatorio = rand.nextInt(2) + 1;
                    if(numeroAleatorio == 1) {
                    	System.out.println("Sigo durmiendo.");
                        //Thread.sleep((10 + rand.nextInt(11)) * 1000);
                    	Thread.sleep(1000);
                    }else {
                    	System.out.println("Estoy pelando al cliente.");
                    	//cortando el pelo al cliente
                    	Thread.sleep(2000);
                    	//limpiar el suelo
                    	Thread.sleep(1000);
                    	clientesPelados++;
                    	System.out.println("he acabado de cortar el pelo y es el pelado " + clientesPelados + " del dia");                    	
                    	sillones.release();
                    }                	
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
