package practicas.practica04.parte02;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import practicas.auxiliar.Format;
import practicas.auxiliar.Par;
import practicas.practica03.parte02.Estudiante;
import practicas.practica03.parte02.GestionEstudiantes;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica04Parte02Junit5 {
	private final String directorioEntrada = System.getProperty("user.dir") + File.separator +
			                                 "src" + File.separator +
//			                                 "main" + File.separator +
//			                                 "java" + File.separator +
									         "practicas" + File.separator +
			                           		 "practica03" + File.separator +
			                           		 "parte02" + File.separator;
	@Test
	@Order(0)
	public void test00() {
		EstudiantesNetwork red = new EstudiantesNetwork();
	
		assertTrue(red.load(directorioEntrada + "datos.txt", "ESI"));
		
		assertTrue(red.numberOfVertices() == 8);
		assertTrue(red.numberOfEdges() == 36);
		
		Estudiante pedraSanchez = new Estudiante("sánchez", "pedra");
		Estudiante juanAguilera = new Estudiante("aguilera", "juan");
		Estudiante andreaSanchez = new Estudiante("sánchez", "andrea");
		Estudiante pepaMartinez = new Estudiante("martínez", "pepa");		
		Estudiante pepeLopez = new Estudiante("lópez", "pepe");
		
		assertNull(red.getNeighbors(pedraSanchez));
		assertNotNull(red.getNeighbors(juanAguilera));//¿Te dice que es null? ¿Qué le falta a la clase Estudiante para que todo funcione correctamente?
		assertNotNull(red.getNeighbors(andreaSanchez));
		assertNotNull(red.getNeighbors(pepaMartinez));
		
		assertTrue(red.getNeighbors(juanAguilera).size() == 3); 
		assertTrue(red.getNeighbors(andreaSanchez).size() == 6);
		assertTrue(red.getNeighbors(pepaMartinez).size() == 7);
		
		
		//Caso 01
		assertEquals("-1.00", Format.formatDouble(red.getWeight(juanAguilera, pepeLopez))); 
		assertEquals("0.33", Format.formatDouble(red.getWeight(juanAguilera, andreaSanchez)));
		assertEquals("0.50", Format.formatDouble(red.getWeight(andreaSanchez, pepeLopez)));
				
		String juanAguilera_pepeLopez = "";
		for(Par<Estudiante, Double> par: red.getDijkstra(juanAguilera, pepeLopez)) {
			juanAguilera_pepeLopez += par.getKey().getNombreApellidos() + " <" +  Format.formatDouble(par.getValue()) + "> ";
		}
		assertEquals("juan aguilera <0.00> andrea sánchez <0.33> pepe lópez <0.83> ", juanAguilera_pepeLopez);
				
		//Caso 02
		assertEquals("0.20", Format.formatDouble(red.getWeight(andreaSanchez, pepaMartinez)));
		
		String andreaSanchez_pepaMartinez = "";
		for(Par<Estudiante, Double> par: red.getDijkstra(andreaSanchez, pepaMartinez)) {
			andreaSanchez_pepaMartinez += par.getKey().getNombreApellidos() + " <" + Format.formatDouble(par.getValue()) + "> ";
		}
		assertEquals("andrea sánchez <0.00> pepa martínez <0.20> ", andreaSanchez_pepaMartinez);
		

		//Caso 03		
		assertEquals("1.00", Format.formatDouble(red.getWeight(pepeLopez, pepaMartinez)));
		assertEquals("0.50", Format.formatDouble(red.getWeight(pepeLopez, andreaSanchez)));
		assertEquals("0.20", Format.formatDouble(red.getWeight(andreaSanchez, pepaMartinez)));

		String pepeLopez_pepaMartinez = "";
		for(Par<Estudiante, Double> par: red.getDijkstra(pepeLopez, pepaMartinez)) {
			 pepeLopez_pepaMartinez += par.getKey().getNombreApellidos() + " <" +  Format.formatDouble(par.getValue()) + "> ";
		}
		assertEquals("pepe lópez <0.00> andrea sánchez <0.50> pepa martínez <0.70> ", pepeLopez_pepaMartinez);
		
		//Hay que modificar mínimamente el método getDijkstra()...¿por qué?
		ArrayList<Par<Estudiante, Double>> aux = red.getDijkstra(juanAguilera, pepeLopez);
		assertEquals("aguilera, juan (999784571) - c/ salamanca 12, blq.2, 4ºa, roquetas de mar (almería)", aux.get(0).getKey().toString());
		assertEquals("sánchez, andrea (654321487) - avda. madrid, 14, blq. 9, 9ºb, almería (almería)", aux.get(1).getKey().toString());
		assertEquals("lópez, pepe (835777599) - avda. los limoneros 13, 3ºa, almería (almería)", aux.get(2).getKey().toString());
		red.clear();
	}
	
	@Test
	@Order(1)
	public void test01() {
		GestionEstudiantes gestion = new GestionEstudiantes("ESI");
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		
		int cont = 0;
		Iterator<Entry<Estudiante, TreeMap<Estudiante, Integer>>> it = gestion.iterator(); //Viaje al pasado. ¿Por qué volver al uso explícito de la clase Iterator?
		while (it.hasNext()) {
			cont++;
			it.next();
		}
		assertTrue(cont==6);
	}
}