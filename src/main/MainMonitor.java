package main;

import java.time.LocalDateTime;
import java.util.Arrays;

public class MainMonitor {
	
	public static void main (String [ ] args) {
		 
		MonitorArray lista = new MonitorArray();
		
		lista.add(30);	lista.add(40);
		lista.add(2);	lista.add(324);
		lista.add(3);	lista.add(34);
		lista.add(6);	lista.add(46);
		lista.add(23);	lista.add(4);
		lista.add(14);	lista.add(67);
		lista.add(16);	lista.add(89);
		lista.add(20);	lista.add(37);
		lista.add(9);	lista.add(54);
		lista.add(31);	lista.add(76);
 
		System.out.println (Arrays.toString(lista.getLista()));
		System.out.println ("---------------------------------------------");
 
		lista.sort(5);

//		System.out.println (LocalDateTime.now());
//		System.out.println (Arrays.toString(lista.getLista()));
//		System.out.println (LocalDateTime.now());
    } 

}
