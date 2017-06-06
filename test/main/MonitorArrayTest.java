package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

	
	@Test
	public void sortCon100ElementosY3Threads() throws ListaYaOrdenadaException {
		
		List<Integer> listaOrdenada = new ArrayList<Integer>();
		for(int i = 0; i < 100; i++) {
			int num = new Random().nextInt(1000);
			listaOrdenada.add(num);
			lista.add(num);
		}
		
		lista.sort(3);
		Collections.sort(listaOrdenada);
		
		List<Integer> listSort = new ArrayList<Integer>();
		for(int i = 0; i < 100; i++) {
			listSort.add(lista.getLista()[i]);
		}
		
		assertEquals(listSort, listaOrdenada);
	}

	
	@Test
	public void sortCon1000ElementosY6Threads() throws ListaYaOrdenadaException {
		
		List<Integer> listaOrdenada = new ArrayList<Integer>();
		for(int i = 0; i < 1000; i++) {
			int num = new Random().nextInt(5000);
			listaOrdenada.add(num);
			lista.add(num);
		}
		
		lista.sort(6);
		Collections.sort(listaOrdenada);
		
		List<Integer> listSort = new ArrayList<Integer>();
		for(int i = 0; i < 1000; i++) {
			listSort.add(lista.getLista()[i]);
		}
		
		assertEquals(listSort, listaOrdenada);
	}
}
