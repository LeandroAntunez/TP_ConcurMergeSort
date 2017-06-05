package main;

import java.util.ArrayList;
import java.util.List;

public class BufferArray {
	
	private List<MonitorArray> list = new ArrayList<MonitorArray>();

	public synchronized void add(MonitorArray array) {
		list.add(list.size(), array);
		notifyAll();
	}

	public synchronized MonitorArray pop() {
		MonitorArray array = list.get(0);
		list.remove(0);
		return array;
	}

	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}

	
	/*	Retorna el primer monitor array de la lista, sin sacarlo
	 */
	public synchronized MonitorArray peek() {
		while (this.isEmpty()) {
            try { wait(); }
            catch (InterruptedException e) {  }
		}
		return list.get(0);
	}

	public synchronized int size() {
		return list.size();
	}
	

}
