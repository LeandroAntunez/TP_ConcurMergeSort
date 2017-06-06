package main;

import static org.junit.Assert.*;

import java.util.Arrays;

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
	
	@Test (expected = ListaYaOrdenadaException.class)
	public void sortConUnElementoYUnThread() throws ListaYaOrdenadaException{
		lista.add(2);
		
		lista.sort(1);
	}
	
	
	@Test
	public void sortConCincoElementosYDosThreads() throws ListaYaOrdenadaException {
		
		lista.add(12);
		lista.add(20);
		lista.add(123);
		lista.add(1);
		lista.add(7);
		
		lista.sort(2);
		
		assertEquals(5, lista.size());

		assertEquals(1, lista.pop()); 	//primero
		assertEquals(7, lista.pop());	//segundo
		assertEquals(12, lista.pop());	//tercero
		assertEquals(20, lista.pop());	//cuarto
		assertEquals(123, lista.pop());	//quinto
	}


}
