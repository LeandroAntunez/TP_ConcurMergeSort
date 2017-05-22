package main;

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
     array debe crearse uno nuevo del doble de tamaï¿½o y copiar todos los elementos
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

    
    public synchronized void sort(int cantidadMonitores, MonitorArray list){
    	
    	
    	mergesort(list);
    	
    	
    }
    
    

    public synchronized void mergesort(MonitorArray list){
    	if (this.size() <= 1) { }
    	else {
    		MonitorArray left = list.primeraMitad(0, list.size()/2);
    		MonitorArray right = list;
    		this.mergesort(left);
    		this.mergesort(right);
    		///
    		list = merge(left, right);
    		
    	}
   
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
	
    /* Recibe dos listas, que ya estan ordenadas, y verifica que el primer elemento de 
     * la primera lista es menor al de la segunda, despues agrega al menor en otra lista
     * y asi hasta que una de las dos esté vacia
     */
	public MonitorArray merge(MonitorArray left, MonitorArray right) {
		MonitorArray listaOrdenada = new MonitorArray();
		int posicionActual = 0;
		while(!left.isEmpty() && !right.isEmpty()) {
			if(left.peek() <= right.peek())
				listaOrdenada.add(left.pop());
			else
				listaOrdenada.add(right.pop());
			posicionActual++;
		}
		this.addAll(listaOrdenada, left, posicionActual);
		this.addAll(listaOrdenada, right, posicionActual);
		return listaOrdenada;
	}

	private void addAll(MonitorArray listaOrdenada, MonitorArray lista, int posicionActual) {
		while(!lista.isEmpty()) {
			listaOrdenada.add(lista.pop());
			posicionActual++;
		}
	}

	
    

}
