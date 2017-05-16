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

}
