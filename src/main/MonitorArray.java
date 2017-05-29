package main;

public class MonitorArray {

    private int limite = 10;
    private int[] lista = new int[limite];
    private int contadorPosicion = 0;
    private ThreadSort thread = new ThreadSort();
    

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
     *  array debe crearse uno nuevo del doble de tama�o y copiar todos los elementos
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



    public int[] getLista() {
    	return lista;

    /**	Ordena los elementos de dos listas de menor a mayor en una lista.
     * 	Las listas ingresadas deben estar ordenadas de menor a mayor
     */
	public MonitorArray merge(MonitorArray left, MonitorArray right) {
		MonitorArray nuevaLista = new MonitorArray();
		
		while(!left.isEmpty() && !right.isEmpty()) {
			if(left.peek() <= right.peek())
				nuevaLista.add(left.pop());
			else
				nuevaLista.add(right.pop());
		addAll(nuevaLista, left);
		addAll(nuevaLista, right);
		
		return nuevaLista;
	}


	
	/**	 Agrega todos los elementos de una lista en la nueva lista
	 */
	private void addAll(MonitorArray nuevaLista, MonitorArray listaOrdenada) {
		while(!listaOrdenada.isEmpty()) {
			nuevaLista.add(listaOrdenada.pop());

	
	public void mergeSort(MonitorArray array) {
		if(array.size() == 1) {
			thread.add(array);
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
	       
	
	public void sort(int numThreads) {
		thread.setSizeArray(this.size());
		mergeSort(this);
		thread.setNroThreads(numThreads);
		thread.start();
	}

	
	
}