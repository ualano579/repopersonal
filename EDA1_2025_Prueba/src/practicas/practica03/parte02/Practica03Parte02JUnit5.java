package practicas.practica03.parte02;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.io.File;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica03Parte02JUnit5 {

	String directorioEntrada = System.getProperty("user.dir") + File.separator +
			"src" + File.separator +
//			"main" + File.separator +
//			"java" + File.separator +
			"practicas" + File.separator +
			"practica03" + File.separator +
			"parte02" + File.separator;
			
	@Test
	@Order(0)
	public void test00() {
		GestionEstudiantes gestion = new GestionEstudiantes("ESI");
		assertEquals("ESI", gestion.toString());
		assertFalse(gestion.load("datos.txt"));
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		assertTrue(gestion.getNumAsignaturasOfertadas() == 12);
		assertTrue(gestion.getNumEstudiantesMatriculados() == 9);
		assertTrue(gestion.size() == 8); //¿No debería ser 9?
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		assertTrue(gestion.getNumAsignaturasOfertadas() == 12);
		assertTrue(gestion.getNumEstudiantesMatriculados() == 9);
		assertTrue(gestion.size() == 8); 
	
		assertEquals("<estudiante desconocido>", gestion.toStringEstudiante("Sánchez",  "Pedro"));
		assertEquals("aguilera, juan (999784571) - c/ salamanca 12, blq.2, 4ºa, roquetas de mar (almería)", gestion.toStringEstudiante("Aguilera", "JuAn   "));		
		assertEquals("ambrosio, juan (555223344) - c/ rincón 10, viator (almería)", gestion.toStringEstudiante("ambrosio", "juan"));
		
		assertEquals("eda1 (2º-1C) <10>", gestion.toStringAsignatura("eda1"));
		assertEquals("eda2 (2º-2C) <9>", gestion.toStringAsignatura("eda2"));
		assertEquals("la (1º-2C) <3>", gestion.toStringAsignatura("la"));
		assertEquals("<asignatura desconocida>", gestion.toStringAsignatura("le"));
		assertEquals("[antonio, paco]", gestion.toStringDocentes("eda1", Comparator.naturalOrder()).toString());
		assertEquals("[paco, antonio]", gestion.toStringDocentes("eda1", Comparator.reverseOrder()).toString());
		assertEquals("<asignatura desconocida>", gestion.toStringDocentes("le", null));
		gestion.clear();
		assertTrue(gestion.getNumAsignaturasOfertadas() == 0);
		assertTrue(gestion.getNumEstudiantesMatriculados() == 0);
		assertTrue(gestion.size() == 0); 
	}
	
	@Test
	@Order(1)
	public void test01() {
		GestionEstudiantes gestion = new GestionEstudiantes("ESI");
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		
		assertNull(gestion.getAsignaturasMatriculadas("sánche",  "pedra"));  //Este estudiante no existe
		assertEquals("[]", gestion.getAsignaturasMatriculadas("ambrosio",  "juan").toString()); //Este estudiante existe, pero no está matriculado en ninguna asignatura
		assertEquals("[ip, la, mp]", gestion.getAsignaturasMatriculadas("aguilera",  "juan").toString());
		assertEquals("[eda1, eda2]", gestion.getAsignaturasMatriculadas("lópez",  "pepe").toString());
		assertEquals("[ec, ip, si]", gestion.getAsignaturasMatriculadas("sánchez",  "andrés").toString());

		assertEquals("-1.00", gestion.getNotaMedia("sánche",  "pedra"));
		assertEquals("0.00", gestion.getNotaMedia("ambrosio",  "juan"));
		assertEquals("6.40", gestion.getNotaMedia("aguilera",  "juan"));
		assertEquals("7.01", gestion.getNotaMedia("lópez",  "pepe"));
		assertEquals("4.28", gestion.getNotaMedia("sánchez",  "andrés"));

		gestion.clear();
	}	
	 
	@Test
	@Order(2)
	public void test02() {
		GestionEstudiantes gestion = new GestionEstudiantes("ESI");
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		
		assertEquals("[andrea sánchez, pepe lópez]", gestion.getAlumnosMatriculados("eda1").toString());
		assertEquals("[andrea sánchez, antonio hernández, pepa martínez, pepe lópez]", gestion.getAlumnosMatriculados("eda2").toString());
		assertNull(gestion.getAlumnosMatriculados("eda3"));
		assertEquals("[andrea sánchez]", gestion.getAlumnosMatriculados("dra").toString());
				
		assertEquals("8.17", gestion.getNotaMedia("eda1"));
		assertEquals("6.05", gestion.getNotaMedia("eda2"));
		assertEquals("-1.00", gestion.getNotaMedia("eda3"));
		assertEquals("5.17", gestion.getNotaMedia("dra"));
		
		assertEquals("7.93", gestion.getNotaMedia("lópez", "pepe", "eda1"));
		assertEquals("6.09", gestion.getNotaMedia("lópez", "pepe", "eda2"));
		assertEquals("-1.00", gestion.getNotaMedia("lirola", "matías", "eda2"));
		assertEquals("-1.00", gestion.getNotaMedia("lópez", "pepe", "eda23"));
		assertEquals("0.00", gestion.getNotaMedia("ambrosio", "juan", "eda1"));
		
		assertEquals("6.29", gestion.getNotaMediaDocente("paco"));
		assertEquals("-1.00",gestion.getNotaMediaDocente("emilio"));
		assertEquals("5.35",gestion.getNotaMediaDocente("mercedes"));
		assertEquals("5.26",gestion.getNotaMediaDocente("julio"));
		assertEquals("6.75",gestion.getNotaMediaDocente("antonio"));
		
		gestion.clear();
	}	
}