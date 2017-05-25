package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.MonitorArray;

public class MonitorArrayTest {
	
	private MonitorArray lista;

	@Before
	public void setUp() throws Exception {
		
		lista = new MonitorArray();
	}

	@Test
	public void cuandoAgregoUnElementoALaListaSuSizeEs1() {
		lista.add(20);
		
		assertEquals(1, lista.size());
	}
	
	
	@Test
	public void cuandoLlegoAlLimiteDelArray_10_yAgregoUnElementoSeAgregaCorrectamente() {
		for (int i = 0; i < 10; i++) {
			lista.add(i);
		}
		
		lista.add(20);
		
		assertEquals(11, lista.size());
	}
	
	@Test
	public void cuandoSeHacePopSeSacaDeLaListaYElSizeSeActualizaEnMenosUno() {
		lista.add(20);
		lista.add(9);
		
		assertEquals(2, lista.size());
		
		int numeroObtenido = lista.pop();
		
		assertEquals(1, lista.size());
		assertFalse(lista.contains(numeroObtenido));
	}
	
	
	@Test
	public void cuandoHagoUnaSublistaHastalaPrimeraMitadDeElementosReciboUnaListaDeLaPrimeraALaMitad() {
		lista.add(1);
		lista.add(3);
		lista.add(4);
		lista.add(6);
		
		MonitorArray sublista = lista.primeraMitad(0, 2);
		
		// Compruebo que recibo la mitad de elementos.
		assertEquals(2, sublista.size());
		
		int primerElemento = sublista.pop();
		int segundoElemento = sublista.pop();
		
		// Compruebo que la sublista tiene los elementos correspondientes.
		assertEquals(1, primerElemento);
		assertEquals(3, segundoElemento);
		
		// Compruebo que al quitarle los elementos, los cuales son la primera mitad de la lista
		// original, queda vacia.
		assertTrue(sublista.isEmpty());
	}
	
	@Test
	public void mergesort() {
		
		lista.add(12);
		lista.add(20);
		lista.add(123);
		lista.add(1);
		
		lista.mergeSort(lista);
		
		int primero = lista.pop();
		assertTrue(primero < lista.peek());
	}
	
	
	

}
