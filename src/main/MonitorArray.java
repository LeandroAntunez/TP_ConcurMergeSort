package main;



public class MonitorArray extends Thread {

    private int limite = 10;
    private int[] lista = new int[limite];
    private int contadorPosicion = 0;
    private BufferArray buffer = new BufferArray();
    private Boolean permiso = true;
	private int threadsLanzados = 0;
	private int threadsEjecutandose = 0;
    

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
     *  add que agrega un elemento al final de la lista (si no hay suficiente espacio en el
     *  array debe crearse uno nuevo del doble de tamaï¿½o y copiar todos los elementos
     *  contenidos en el original)
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



    public synchronized int[] getLista() {
    	return lista;
    }
    
    private synchronized void setLista(int[] lista) {
    	this.lista = lista;
    }
    	
    /**	Ordena los elementos de dos listas de menor a mayor en una lista.
     * 	Las listas ingresadas deben estar ordenadas de menor a mayor
     */
	private MonitorArray merge(MonitorArray left, MonitorArray right) {
		MonitorArray nuevaLista = new MonitorArray();
		
		while(!left.isEmpty() && !right.isEmpty()) {
			if(left.peek() <= right.peek())
				nuevaLista.add(left.pop());
			else
				nuevaLista.add(right.pop());
		}
		addAll(nuevaLista, left);
		addAll(nuevaLista, right);
		
		return nuevaLista;
	}


	
	/**	 Agrega todos los elementos de una lista en la nueva lista
	 */
	private void addAll(MonitorArray nuevaLista, MonitorArray listaOrdenada) {
		while(!listaOrdenada.isEmpty())
			nuevaLista.add(listaOrdenada.pop());
	}

	
	private void mergeSort(MonitorArray array) {
		if(array.size() == 1) {
			buffer.add(array);
		} else {
			MonitorArray left  = firstHalf(array); 
			MonitorArray right = array;
			
			mergeSort(left);
			mergeSort(right);
		}
	}

	
	/*	Retorna la primera mitad de un Monitor Array
	 */
	private MonitorArray firstHalf(MonitorArray array) {
		MonitorArray half = new MonitorArray();
		int mid = array.size() / 2;
		while(array.size() > mid)
			half.add(array.pop());
		return half;
	}
	       
	
	public void sort(int numThreads) throws ListaYaOrdenadaException {
		if (1 >= this.size()){
			throw new ListaYaOrdenadaException("No se puede ordenar una lista con un solo elemento.");
		}
		mergeSort(this);
		lanzarThread(numThreads, this);	
		esperarAQueFinalicenLosThreads(numThreads);
	}

	private synchronized void esperarAQueFinalicenLosThreads(int numThreads) {
		while(threadsLanzados != numThreads) {
			try { wait(); }
            catch (InterruptedException e) {  }
		}
		threadsLanzados = 0;
	}
	
	private synchronized void sumarThreadTerminado() {
		threadsLanzados++;
		notify();
	}
	
	private synchronized void pedirTurno(){
		 while (! permiso) {
	            try { wait(); }
	            catch (InterruptedException e) {  }
	      }
		 permiso = false;
	}
	
	private synchronized void liberarTurno() {
		permiso = true;
		notifyAll();
	}
	
	/*
	 * (while) Mientras haya elementos por mergear.
	 * 
	 * 			(if) Si hay elementos en el nivel N.
	 * 
	 * 					(if) Si hay otro thread accediendo a dos elementos para mergear.
	 * 							(wait) espera.
	 * 					garantizar exclusion mutua.
	 * 					extrae 2 elementos.
	 * 					(notify) finalizar la exclusion mutua
	 * 
	 * 			(else) Si no hay elementos en el nivel N
	 * 
	 * 					(recursion) busca elementos en el nivel N+1.
	 * 
	 * 
	 *  - Luego de extraer, ¿mergear antes o despues del notify?
	 *  
	 *  - ¿Como garantizar que al mergear, despues deposite el bloque en el nivel N+1 en el orden esperado
	 *  por otros threads? Ejemplo: A1, A2, A3, A4 podrian quedar por error como B2(A3,A4), B1(A1,A2).
	 *  
	 *  - ¿Cual es la condicion por la cual ya no hay elementos por mergear en un buffer multinivel?
	 *  
	 * 	- ¿Que hacer si la cantidad de bloques a ordenar es impar?	
	 * */
	
	
	
	private synchronized void finalizarEjecucion() {
		this.threadsEjecutandose--;
	}

	
	private synchronized void iniciarCiclo() {
		this.threadsEjecutandose++;
	}
	
	private synchronized Boolean haythreadsEjecutandose() {
		return  0 < threadsEjecutandose;
	}
	
	private synchronized void lanzarThread(int nroThreads, MonitorArray originalArray){
		for(int i = 0; i < nroThreads; i++) {
			new MonitorArray() {
				@Override
			    public synchronized void run() {
			    	MonitorArray array = new MonitorArray();
			    	
			    	while(! buffer.isEmpty() ){
			    		pedirTurno();
			    		if (buffer.size() == 2 && !haythreadsEjecutandose()) {
			    			System.out.println("[IF-MERGE] " + this.getName() + "[Tamaño buffer] "
				    				+ buffer.size() + ", [Threads esperando]: " + originalArray.threadsEjecutandose + "\n");
			    			MonitorArray elem1 = buffer.pop();
			    			MonitorArray elem2 = buffer.pop();
			    			liberarTurno();
			    			System.out.println("[IF-MERGE] " + this.getName() + "[Tamaño buffer] "
				    				+ buffer.size());
			    			
			    			array = array.merge(elem1, elem2);
			    			originalArray.setLista(array.getLista());
			    			originalArray.contadorPosicion = array.contadorPosicion;
			    			
			    		} else if (!buffer.isEmpty() && buffer.size() > 2 ){
			    				iniciarCiclo();
			    				System.out.println("[ELSE-MERGE] " + this.getName() + "[Tamaño buffer] "
					    				+ buffer.size() + ", [Threads esperando]: " + originalArray.threadsEjecutandose + "\n");
			    				MonitorArray elem1 = buffer.pop();
				    			MonitorArray elem2 = buffer.pop();
				    			liberarTurno();
				    			
				    			array = array.merge(elem1, elem2);
			    				buffer.add(array);
			    				System.out.println("[Finalizo Else] " + this.getName() + "[Tamaño buffer] "
					    				+ buffer.size());
			    				finalizarEjecucion();
			    		} else {
			    			liberarTurno();
			    		}
			    	}
			    	originalArray.sumarThreadTerminado();
			    	System.out.println("Numero de Threads terminados: " + originalArray.threadsLanzados);
				}
		    }.start();
		}
	}
	
}