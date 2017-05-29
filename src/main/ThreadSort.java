package main;

import java.util.Arrays;

public class ThreadSort extends Thread{

	private BufferArray buffer = new BufferArray();
	private int nroThreads;
	private int originalSize;
	private int nivelActual = 1;
	
	@Override
	public void run() {
		for(int i = 0; i < nroThreads; i++) {
			new Thread() {
		        @Override
		        public void run() {
//		        	while(noHayaArrayConTamanioOriginal()) {
		        		if(buffer.cantidadDeNivel(nivelActual) >= 2) {
			        		MonitorArray array = new MonitorArray();
			        		
			        		array = array.merge(buffer.pop(), buffer.pop());
			        		buffer.add(array, array.size());
			        		
		        		} else if (buffer.cantidadDeNivel(nivelActual) == 1) {
		        			MonitorArray elem1 = buffer.pop();
		        			buffer.add(elem1, elem1.size());
		        			nivelActual=nivelActual*2;
		        		}
		        		System.out.println(this.getName());
		        		System.out.println ("Tamaño buffer" + buffer.size());
		        	}
//				}
		    }.start();
		}
	}

	private Boolean noHayaArrayConTamanioOriginal(){
		return buffer.peek().size() != originalSize;
	}
	
	public void add(MonitorArray array) {
		buffer.add(array, array.size());
	}


	public void setNroThreads(int numThreads) {
		this.nroThreads = numThreads;
	}

	
	/*	Se guarda el tamaño original del array
	 */
	public void setSizeArray(int size) {
		this.originalSize = size;
	}
}
