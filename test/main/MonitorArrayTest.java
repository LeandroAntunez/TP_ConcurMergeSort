package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


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
	public void merge() {
		
		MonitorArray left = new MonitorArray();
		MonitorArray right = new MonitorArray();
		
		lista.add(12);
		lista.add(20);
		lista.add(123);
		lista.add(1);
		lista.add(3);
		
		left.add(3);
		left.add(12);
		left.add(20);
		
		right.add(1);
		right.add(123);
		
		lista = lista.merge(left, right);
		assertEquals(5, lista.size());
		
		int primero = lista.pop();
		int segundo = lista.pop();
		int tercero = lista.pop();
		int cuarto = lista.pop();
		int quinto = lista.pop();
		
		assertEquals(1, primero);
		assertEquals(3, segundo);
		assertEquals(12, tercero);
		assertEquals(20, cuarto);
		assertEquals(123, quinto);
	}
	
	
	@Test
	public void mergeSort() {
		
		lista.add(12);
		lista.add(20);
		lista.add(123);
		lista.add(1);
		lista.add(3);
		
		lista.mergeSort(lista);
		
		int primero = lista.pop();
		int segundo = lista.pop();
		int tercero = lista.pop();
		int cuarto = lista.pop();
		int quinto = lista.pop();
		
		assertEquals(1, primero);
		assertEquals(3, segundo);
		assertEquals(12, tercero);
		assertEquals(20, cuarto);
		assertEquals(123, quinto);
	}

}
