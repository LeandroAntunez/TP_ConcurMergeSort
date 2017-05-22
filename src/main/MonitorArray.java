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

    /*
    mergesort ( list ) {
		if ( list . size () <= 1) return;
		left = list . sublist (0, list . size ()/2);
		right = list . sublist ( list . size ()/2 , list . size ());
		mergesort ( left );
		mergesort ( right );
		list = merge (left , right );
		}
     */

    public synchronized void mergesort(MonitorArray list){
    	if (this.size() <= 1) { }
    	else {
    		MonitorArray left = list.primeraMitad(0, list.size()/2);
    		MonitorArray right = list;
    		this.mergesort(left);
    		this.mergesort(right);
    		//list = merge(left, right);
    		
    		
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

    

}
