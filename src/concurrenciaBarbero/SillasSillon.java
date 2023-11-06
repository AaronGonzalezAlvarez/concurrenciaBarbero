package concurrenciaBarbero;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SillasSillon {
	
	private AtomicInteger aforo = new AtomicInteger(5);
    private AtomicInteger aforoActual = new AtomicInteger(0);
    private AtomicBoolean sillon = new AtomicBoolean(false);
	
	//sillas
	public synchronized int addAfoto() {
		return aforoActual.getAndIncrement();
	}
	
	public synchronized int deleteAfoto() {
		return aforoActual.getAndDecrement();
	}
	
	public synchronized boolean completo() {
		return aforoActual.get() == aforo.get();
	}
	
	//sillon
	
	public synchronized void sillonOcupado() {
		 sillon.set(true);
	}
	
	public synchronized void sillonLibre() {
		sillon.set(false);
	}
	
	public synchronized boolean verSillon() {
		return sillon.get();
	}

}
