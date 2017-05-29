package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BufferArray {
	
	private Map<MonitorArray, Integer> map = new HashMap<MonitorArray, Integer>();

	public synchronized void add(MonitorArray array, int size) {
		map.put(array, size);
		notifyAll();
	}

	public synchronized MonitorArray pop() {
		while (this.isEmpty()) {
            try { wait(); }
            catch (InterruptedException e) {  }
		}
            
		MonitorArray array = map.keySet().iterator().next();
		map.remove(0);
		return array;
	}

	private boolean isEmpty() {
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
		Integer cantNiveles = 0;
		for(Integer nivel : niveles) {
			if(nivel == nivelActual) 
				cantNiveles++;
		}
		return cantNiveles;
	}

	public Set<MonitorArray> getArrays() {
		return map.keySet();
	}

	public int size() {
		// TODO Auto-generated method stub
		return map.size();
	}
	
	

}
