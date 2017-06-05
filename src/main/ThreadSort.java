package main;

import java.util.Arrays;

public class ThreadSort extends Thread{

	private BufferArray buffer = new BufferArray();
	private int nroThreads;
	private int nivelActual = 1;
	private MonitorArray originalArray;
	
	@Override
	public void run() {
		for(int i = 0; i < nroThreads; i++) {
			new Thread() {
		        @Override
		        public synchronized void run() {
		        	MonitorArray array = new MonitorArray();
		        	
		        	while(! buffer.isEmpty()) {
		        		if (buffer.size() == 2) {
		        			array = array.merge(buffer.pop(), buffer.pop());
		        			originalArray.setLista(array.getLista());
//		        			System.out.println (Arrays.toString(array.getLista()));
		        			
		        		} else { 
//		        			if(buffer.cantidadDeNivel(nivelActual) >= 2) {
			        		array = array.merge(buffer.pop(), buffer.pop());
			        		buffer.add(array, array.size());
			        		
//		        		} else { 
//		        			array = buffer.pop();
//		        			buffer.add(array, array.size());
//		        			nivelActual = nivelActual * 2;
		        		} 
		        		System.out.println(this.getName());
		        		System.out.println ("Tamaño buffer: " + buffer.size());
		        	}
				}
		    }.start();
		}
	}

	
	public void add(MonitorArray array) {
		buffer.add(array, array.size());
	}


	public void setNroThreads(int numThreads) {
		this.nroThreads = numThreads;
	}

	public void setArray(MonitorArray monitorArray) {
		this.originalArray = monitorArray;		
	}

}
