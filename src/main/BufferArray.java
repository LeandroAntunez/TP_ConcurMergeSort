package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BufferArray {
	
	private Map<MonitorArray, Integer> map = new HashMap<MonitorArray, Integer>();

	public synchronized void add(MonitorArray array, int size) {
		map.put(array, size);
		notifyAll();
	}

	public synchronized MonitorArray pop() {
		MonitorArray array = this.peek();
		map.remove(array);
		return array;
	}

	public synchronized boolean isEmpty() {
		return map.isEmpty();
	}

	
	/*	Retorna el primer monitor array del map, sin sacarlo
	 */
	public synchronized MonitorArray peek() {
		while (this.isEmpty()) {
            try { wait(); }
            catch (InterruptedException e) {  }
		}
		return map.keySet().iterator().next();
	}

	
	public synchronized int cantidadDeNivel(int nivelActual) {
		Collection<Integer> niveles = map.values();
		return (int) niveles.stream().filter(nivel -> nivel == nivelActual).count();
	}


	public synchronized int size() {
		return map.size();
	}
	

}
