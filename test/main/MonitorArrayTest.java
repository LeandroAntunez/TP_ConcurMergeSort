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
		
		MonitorArray array = new MonitorArray();
		
		array.add(12);
		array.add(20);
		array.add(123);
		array.add(1);
		array.add(7);
		
		System.out.println (Arrays.toString(array.getLista()));

		array.sort(2);
		
		System.out.println (Arrays.toString(array.getLista()));
		assertEquals(5, array.size());

		
		int primero = array.pop();
		int segundo = array.pop();
		int tercero = array.pop();
		int cuarto = array.pop();
		int quinto = array.pop();
		
		assertEquals(1, primero);
		assertEquals(3, segundo);
		assertEquals(12, tercero);
		assertEquals(20, cuarto);
		assertEquals(123, quinto);
	}


}
