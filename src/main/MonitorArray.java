package main;

import java.util.Arrays;

public class MonitorArray {

    private int limite = 10;
    private int[] lista = new int[limite];
    private int contadorPosicion = 0;

    public synchronized int size(){ return contadorPosicion;}

    public synchronized boolean isEmpty() { return contadorPosicion == 0; }

    public synchronized boolean contains(int e){
        boolean resultado = false;
        for (int i = 0; i < this.size(); i++) {
            if (lista[i] == e) { resultado = true; }
        }
        return resultado;
    }


    /**
     * add que agrega un elemento al final de la lista (si no hay suficiente espacio en el
     array debe crearse uno nuevo del doble de tama�o y copiar todos los elementos
     contenidos en el original)
     * @param e
     */
    public synchronized void add(int e) {
        if (this.size() == this.limite) {
            limite = limite * 2;
            int[] listaAuxiliar = new int[limite];
            for (int i = 0; i < this.size(); i++){
                int numeroActual = this.lista[i];
                listaAuxiliar[i] = numeroActual;
            }
            this.lista = listaAuxiliar;
        }
        lista[contadorPosicion] = e;
        contadorPosicion++;
        notifyAll();
    }

    /**
     * @return el primer valor de la lista (sin modificarla)
     */
    public synchronized int peek(){
        while (this.isEmpty()) {
            try { wait(); }
            catch (InterruptedException e) {  }
        }
        return this.lista[0];
    }

    /**
     * @return y remueve el primer valor de la lista (si la lista esta vacia debe
     * bloquear al thread ejecutando el metodo)
     */
    public synchronized int pop(){
        while (this.isEmpty()) {
            try { wait(); }
            catch (InterruptedException e) {  }
        }
        int resultado = lista[0];
        for (int i = 1; i < this.size(); i++){
            int valorPosicion = lista[i];
            lista[i - 1] = valorPosicion;
        }
        contadorPosicion--;
        return resultado;
    }


    public int[] getLista() {
    	return lista;
    }
    
    
    
    public void sort(int nroThreads) {
    	threadSort(this, nroThreads);
    }

    
	private void threadSort(MonitorArray array, int nroThreads) {
		if(nroThreads <= 1) {
    		mergeSort(array);
    	} else {
    		MonitorArray left  = array.primeraMitad(0, array.size()/2);
    		MonitorArray right = array;
    		
    		Thread threadLeft  = threadMergeSort(left, nroThreads);
    		Thread threadRight = threadMergeSort(left, nroThreads);
    		
    		threadLeft.start();
    		threadRight.start();
    		    		
    		merge(array, left, right);
    	}
	}
    
    
    private Thread threadMergeSort(MonitorArray array, int nroThreads) {
    	return new Thread() {
            @Override
            public void run()
            {
                threadSort(array, nroThreads / 2);
                System.out.println(this.getName());
            }
        };
	}

    
	public synchronized void mergeSort(MonitorArray lista){
    	if (this.size() > 1) { 
    		MonitorArray left = lista.primeraMitad(0, lista.size()/2);
    		MonitorArray right = lista;
    		mergeSort(left);
    		mergeSort(right);
    		merge(lista, left, right);
    	}
    }


    /* Recibe dos listas, que ya estan ordenadas, y verifica que el primer elemento de 
     * la primera lista es menor al de la segunda, despues agrega al menor en otra lista
     * y asi hasta que una de las dos esta vacia
     */
	public void merge(MonitorArray array, MonitorArray left, MonitorArray right) {
		int contador = 0;
		
		while(!left.isEmpty() && !right.isEmpty()) {
			if(left.peek() <= right.peek())
				array.lista[contador] = left.pop();
			else
				array.lista[contador] =  right.pop();
			contador++;
		}
		
		this.addAll(array.lista, left, contador);
		this.addAll(array.lista, right, contador);
	}
	
	
	public MonitorArray primeraMitad(int primerPosicion, int ultimaPosicion) {
		// Funcion auxiliar: Retorna una sublista de la clase MonitorArray,
		// a partir de una lista con un rango el cual determina el primer y
		// ultimo elemento de esa sublista.
		MonitorArray listaAux = new MonitorArray();
		int totalElementos = ultimaPosicion - primerPosicion;
		
		while(0 < totalElementos){ 
			listaAux.add(this.pop());
			totalElementos--;
		}
		return listaAux;
	}

	
	private void addAll(int[] nuevaListaOrdenada, MonitorArray listaOrdenada, int contador) {
		while(!listaOrdenada.isEmpty()) { 
			nuevaListaOrdenada[contador] = listaOrdenada.pop();
			contador++;
		}
	}

    

}
