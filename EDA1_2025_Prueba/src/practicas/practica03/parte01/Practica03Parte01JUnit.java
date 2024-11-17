package practicas.practica03.parte01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;


public class Practica03Parte01JUnit {

	String directorioEntrada = System.getProperty("user.dir") + File.separator +
							   "src" + File.separator +
//							   "main" + File.separator +
//							   "java" + File.separator +
							   "practicas" + File.separator +
							   "practica03" + File.separator ;
//							   "parte01" + File.separator;

	@Test
	@Order(0)
	public void test00() {
		GestionUsuariosDispositivos gestion = new GestionUsuariosDispositivos();
		gestion.add("usuario", "disp01", "hola", "adiós", "hola", "adiós", "amor", "leña"); 
		gestion.add("usuario", "disp02", "amor", "amar", "amor", "amor", "la", "y", "con", "por"); // y, con, la... son stopWords (palabras que se eliminan)
		gestion.add("usuaria", "disp01",  "amor", "hola", "hola");
		gestion.add("usuaria", "disp01",  "hola", "amor");
		gestion.add("usuario", "disp02", "hola");
		gestion.add("usuario",  "disp03",  "hola", "adiós", "amor"); //Los acentos también se eliminan (ver métodos de la clase Auxiliar - no confundir con el paquete practicas.auxiliar)
			/* El formato de salida en Consola es:
			 * 		usuaria:
			 * 			disp01: amor <2> - hola <3>
			 * 		usuario:
			 * 			disp01: adios <2> - amor <1> - hola <2> - leña <1>
			 * 			disp02: amar <1> - amor <3> - hola <1>
			 * 			disp03: adios<1> - amor <1> - hola <1> 
			 * 
			 */
		assertEquals("usuaria:\n\tdisp01: amor <2> - hola <3>\nusuario:\n\tdisp01: adios <2> - amor <1> - hola <2> - leña <1>\n\tdisp02: amar <1> - amor <3> - hola <1>\n\tdisp03: adios <1> - amor <1> - hola <1>\n",  gestion.toString());
		
		assertTrue(gestion.getNumDispositivos("usuaria") == 1);
		assertTrue(gestion.getNumDispositivos("usuario") == 3);
		assertTrue(gestion.getNumDispositivos("usuarie") == 0);
		assertTrue(gestion.getNumUsuarios("disp01") == 2);
		assertTrue(gestion.getNumUsuarios("disp02") == 1);
		assertTrue(gestion.getNumUsuarios("disp03") == 1);
		assertTrue(gestion.getNumUsuarios("dizp01") == 0);
		
		
		assertTrue(gestion.getNumPalabras("usuaria") == 5); //sumamos las frecuencias de las palabras (2 de "amor" y 3 de "hola")
		
		assertTrue(gestion.getPalabras("usuaria").size() == 2); //¿Y esto por qué?
		assertEquals("[amor, hola]", gestion.getPalabras("usuaria").toString()); 		
		assertTrue(gestion.getFrecuencia("usuaria", "hola") == 3); 
		assertTrue(gestion.getFrecuencia("usuaria", "amor") == 2); //3 + 2 = 5
		
		
		assertTrue(gestion.getNumPalabras(" UsUaRIO") == 14);
		
		assertTrue(gestion.getPalabras("uSuARIo").size() == 5); 
		assertEquals("[adios, amar, amor, hola, leña]", gestion.getPalabras(" usuario     ").toString());

		assertTrue(gestion.getFrecuencia("usuario", "adiós") == 3);
		assertTrue(gestion.getFrecuencia("usuario", "amar") == 1);
		assertTrue(gestion.getFrecuencia("usuario", "amor") == 5);
		assertTrue(gestion.getFrecuencia("usuario", "hola") == 4);
		assertTrue(gestion.getFrecuencia("usuario", "leña") == 1);// 3 + 1 + 5 + 4 + 1 = 14 (¿tenemos claro el significado de estas cuentas?
		
		assertTrue(gestion.getNumPalabras("user") == 0);
		assertTrue(gestion.getFrecuencia("user09", "hola") == 0);
		assertEquals("[]",gestion.getPalabras("usuarios").toString());
		assertTrue(gestion.getFrecuencia("usuarias", "ola") == 0);
		
		assertTrue(gestion.getNumPalabras("usuario", "disp01") == 6);
		assertTrue(gestion.getNumPalabras("usuario", "disp02") == 5);
		assertTrue(gestion.getNumPalabras("usuario", "disp03") == 3);
		
		assertTrue(gestion.getNumPalabras("usuaria", "disp01") == 5);
		assertTrue(gestion.getNumPalabras("usuaria", "disp02") == 0);
		assertTrue(gestion.getNumPalabras("usuarias", "disp01") == 0);
		
		
		assertTrue(gestion.getFrecuencia("usuario", "disp01", "amor") == 1);
		assertTrue(gestion.getFrecuencia("usuario", "disp02", "amor") == 3);
		assertTrue(gestion.getFrecuencia("usuario", "disp03", "amor")  == 1);
		
		assertTrue(gestion.getFrecuencia("usuaria", "disp01", "hola")  == 3);
		assertTrue(gestion.getFrecuencia("usuaria", "disp02", "hola")  == 0);
		assertTrue(gestion.getFrecuencia("ussuaria", "disp01", "hola")  == 0);
		
		assertEquals("[adios, amor, hola, leña]", gestion.getPalabras("usuario", "disp01").toString());
		assertEquals("[amar, amor, hola]", gestion.getPalabras("usuario", "disp02").toString());
		assertEquals("[adios, amor, hola]", gestion.getPalabras("usuario", "disp03").toString());
		assertEquals("[]", gestion.getPalabras("usuario", "disp04").toString());
		assertEquals("[amor, hola]", gestion.getPalabras("usuaria", "disp01").toString());
		assertEquals("[]", gestion.getPalabras("user", "disp04").toString());
				
		
		assertEquals("{usuaria=2, usuario=5}", gestion.getUsuarios("amor").toString());
		assertEquals("{usuario=1}", gestion.getUsuarios("amAr"));
		assertEquals("{usuaria=3, usuario=4}", gestion.getUsuarios("hOlA"));
		assertEquals("{usuario=3}", gestion.getUsuarios("adiós"));
		assertEquals("{}", gestion.getUsuarios("amore"));

		gestion.clear();
	}
	
	@Test
	@Order(1)
	public void test01() {
		GestionUsuariosDispositivos gestion = new GestionUsuariosDispositivos();
		assertFalse(gestion.load("datos.txt"));
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		assertTrue(gestion.size() == 4);
	
		assertTrue(gestion.getNumDispositivos("pepa") == 2);
		assertTrue(gestion.getNumDispositivos("pepe") == 3);
		assertTrue(gestion.getNumDispositivos("JUAN   ") == 2);
		assertTrue(gestion.getNumDispositivos("  JUANA   ") == 2);
		assertTrue(gestion.getNumUsuarios("   dispOSitivo01") == 4);
		assertTrue(gestion.getNumUsuarios("dispositivO02") == 4);
		assertTrue(gestion.getNumUsuarios("dispoSitivo03") == 1);
		assertTrue(gestion.getNumUsuarios("disposiTIvo04") == 0);
		
		assertTrue(gestion.getNumPalabras("pEpE") == 460);
		assertTrue(gestion.load(directorioEntrada + "datos.txt"));
		assertTrue(gestion.getNumPalabras("pEpE") == 460);
		
		assertTrue(gestion.getNumPalabras("Pepe", "dispositivo01") == 412);
		assertTrue(gestion.getNumPalabras("PePe", "dispositivo02") == 14);
		assertTrue(gestion.getNumPalabras("pEPe", "dispositivo03") == 34); // 412 + 14 + 34 = 460 OK!
		
		

		assertTrue(gestion.getPalabras("pepe").size() == 315); 
		assertTrue(gestion.getPalabras("pepe", "dispositivo01").size() == 290);
		assertTrue(gestion.getPalabras("pepe", "dispositivo02").size() == 14);
		assertTrue(gestion.getPalabras("pepe", "dispositivo03").size() == 28); // 290 + 14 + 28 = 332 //¿Por qué no suman 315? 
		
		assertTrue(gestion.getPalabrasRepetidas("pepe").size() == 16); // 332 - 315 = 17 //NO coincide...¿por qué?
		
		assertTrue(gestion.getPalabras("pepa").size() == 396); 
		assertTrue(gestion.getPalabras("pepa", "dispositivo01").size() == 214);
		assertTrue(gestion.getPalabras("pepa", "dispositivo02").size() == 224); // 214 + 224 = 438
		
		assertTrue(gestion.getPalabrasRepetidas("pepa").size() == 42); // 438 - 396 = 42 //Aquí sí coincide...¿por qué?
		
		assertEquals("[]", gestion.getPalabrasRepetidas("pepote").toString());
		assertEquals("{juana=1, pepa=1}", gestion.getUsuarios("alegria").toString());
		assertEquals("{juan=1, pepa=2, pepe=4}", gestion.getUsuarios("mi").toString());
		assertEquals("{juana=1, pepa=1, pepe=4}", gestion.getUsuarios("siempre").toString());
		assertEquals("{}", gestion.getUsuarios("ogra").toString());

		
		gestion.clear();
	}
}