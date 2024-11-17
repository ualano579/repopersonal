package practicas.practica01;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class Practica01JUnit5 {

	@Test
	@Order(0)
	public void testDispositivo(){
 		Dispositivo d01 = new Dispositivo("     iPhone 15", "0000011111"); //Cuidado con los espacios en blanco detrás y delante del texto --> uso del método trim()
		Dispositivo d02 = new Dispositivo("iPhone 15 Pro       ", "102001q000"); //Característica == 0 -> false; true en caso contrario (valor != 0)
		Dispositivo d03 = new Dispositivo("  Samsung Galaxy   ", "0000060010");
		Dispositivo d04 = null;
		Dispositivo d05 = null;
		 
		assertEquals(new Dispositivo(""), new Dispositivo("", "0000000000")); //Si no se especifican las características, se asume que todas son igual a 0 (false)
		
		try {
			d04 = new Dispositivo("", "000"); //Si el número de características no es igual a 10,  se lanza una excepción
		}catch(Exception e) {
			assertEquals(e.getMessage(), "El número de características debe ser, exactamente, igual a 10");
		}

		
		try {
			d04 = new Dispositivo("", "000111000"); //Si el número de características no es igual a 10,  se lanza una excepción
		}catch(Exception e) {
			assertEquals(e.getMessage(), "El número de características debe ser, exactamente, igual a 10");
		}
		
		d04 = new Dispositivo("", "0000000000");
		d05 = new Dispositivo(d04); //Constructor copia
		
		assertEquals(d04, d05);
		
		assertEquals("iPhone 15 {5, 6, 7, 8, 9}",   d01.toString());
		assertEquals("iPhone 15 Pro {0, 2, 5, 6}",  d02.toString());
		assertEquals("Samsung Galaxy {5, 8}", d03.toString());
		assertEquals("noModel {}",     d04.toString());
		assertEquals("noModel {}",     d05.toString());
		
		
		d01 = d02 = d03 = d04 = d05 = null;
	}
	
	@Test
	@Order(1)
	public void testDispositivoCaracteristicas(){
		Dispositivo d01 = new Dispositivo("dispositivo01", "0001110111");
		Dispositivo d02 = new Dispositivo("dispositivo01", "1001100111");
		Dispositivo d03 = new Dispositivo("dispositivo03", "1811151116");
		Dispositivo d04 = new Dispositivo("dispositivo04");
		
		assertEquals("dispositivo01 {3, 4, 5, 7, 8, 9}", d01.toString());
		assertEquals("dispositivo01 {0, 3, 4, 7, 8, 9}", d02.toString());
		assertEquals("dispositivo03 {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}", d03.toString());
		assertEquals("dispositivo04 {}", d04.toString());
		
		assertTrue(d01.equals(d02)); //Son iguales, ¿por qué? Pq su clave es la misma, es decir son tocayos
		assertFalse(d01.equals(d03));
		assertFalse(d01.equals(d04));
		assertFalse(d02.equals(d03));
		assertFalse(d02.equals(d04));
		assertFalse(d03.equals(d04));
	
		assertEquals("{3, 4, 7, 8, 9}", d01.getInterseccion(d02).toString());
		assertEquals("{3, 4, 5, 7, 8, 9}", d01.getInterseccion(d03).toString());
		assertEquals("{}", d01.getInterseccion(d04).toString());
		assertEquals("{0, 3, 4, 7, 8, 9}", d02.getInterseccion(d03).toString());
		assertEquals("{}", d03.getInterseccion(d04).toString());
		
		d01 = d02 = d03 = d04 = null;
	}
	
	@Test
	@Order(2)
	public void testDispositivoMensajes() {
 		Dispositivo d01 = new Dispositivo("dispositivo01");
 		Dispositivo d02 = new Dispositivo("dispositivo02");
 		Dispositivo d03 = null;
 		
 		d01.enviarMensaje("Hola amiga que tal");
 		d01.enviarMensaje("Hola hOLa hoLA amigo como estamos que tal HOLA hola");
 		
 		d02.enviarMensaje("BueNas noches como esta usted");
 		d02.enviarMensaje("buenas noches me alegro saber que esta usted bien");
 		
 		
 		String[] salidaEsperadaD01 = {"hola", "amiga", "que", "tal", "amigo", "como", "estamos"}; //No se permiten palabras repetidas; todas en minúsculas
 		
 		int contD01 = 0;
 		for (String palabra: d01) { //Observa la forma en la que se itera sobre el dispositivo d01 (iteramos sobre la colección LinkedList<T> registroPalabras)
 			assertEquals(salidaEsperadaD01[contD01++], palabra);
 		}
 
 		
 		String[] salidaEsperadaD02 = {"buenas", "noches", "como", "esta", "usted", "me", "alegro", "saber", "que", "bien"}; 
 		
 		int contD02=0;
 		for (String palabra: d02) { //Observa la forma en la que se itera sobre el dispositivo d01 (sobre las palabras que se han enviado a través de él)
 			assertEquals(salidaEsperadaD02[contD02++], palabra); 
 		}
 
 		assertTrue(d01.getNumPalabras() == 7);
 		assertTrue(d02.getNumPalabras() == 10);

 		d03 = new Dispositivo(d02); //Constructor copia
 		
 		d01.vaciarDispositivo();
 		d02.vaciarDispositivo();

 		assertTrue(d01.getNumPalabras() == 0);
 		assertTrue(d02.getNumPalabras() == 0);
 		assertTrue(d03.getNumPalabras() == 10); //Si te falla esta restricción, comprueba que el constructor copia esté correctamente implementado
 		
 		d03.vaciarDispositivo();
 		
 		assertTrue(d03.getNumPalabras() == 0);
 		
 		d01 = d02 = d03 = null;
	}
	
	@Test
	@Order(3)
	public void testUsuario(){
		Usuario.inicializarNumUsuarios();
		Usuario u01 = new Usuario("   Bob   "); //cuidado con los espacios en blanco
		Usuario u02 = new Usuario("Alice  ");
		Usuario u03 = null;
		Usuario u04 = null;
		
		try {
			u03 = new Usuario(null);
		}catch (Exception e) {
			assertEquals("El atributo nombre no puede ser ni nulo ni vacío", e.getMessage());
		} 
		
		try {
			u03 = new Usuario("");
		}catch (Exception e) {
			assertEquals("El atributo nombre no puede ser ni nulo ni vacío", e.getMessage());
		}
		
		u03 = new Usuario("EvE");
		
		
		assertEquals("1.- Bob -> []", u01.toString()); //Como podéis observar, en este caso se respetan las mayúsculas y minúsculas
		assertEquals("2.- Alice -> []", u02.toString());
		assertEquals("3.- EvE -> []", u03.toString());
		
		assertEquals(u04 = new Usuario("Pedro"), u04 = new Usuario("Pedro")); //Atención a la doble asigación u04 = new... 

		assertNotEquals(u04 = new Usuario("Pedro"), u04 = new Usuario("Pedra")); //Atención a la doble asigación u04 = new... 

		assertEquals("7.- Pedra -> []", u04.toString()); //¿Por qué es Pedra y por qué su identificador es igual a 7?
		
		for (int i=0; i<4000; i++) {
			u04 = new Usuario("Pedro");
		}
		
		assertEquals("4007.- Pedro -> []", u04.toString()); //¿Qué ha pasado con los 4000 anteriores?
		
		u01 = u02 = u03 = u04 = null;
	}
	
	@Test
	@Order(4)
	public void testUsuarioDispositivos() {
 		Usuario.inicializarNumUsuarios();

		Usuario u01 = new Usuario("bob");
		Usuario u02 = new Usuario("alice");
		Dispositivo d01 = new Dispositivo("iPhone 15");
		Dispositivo d02 = new Dispositivo("Samsung Galaxy");
		Dispositivo d03 = new Dispositivo("iPhone 15");
		Dispositivo d04 = null;
		
		u01.addDispositivos(d01, d01, d02, d03, d03); //Asociamos 5 dispositivos con el usuario u01
		assertTrue(u01.getNumDispositivos() == 2); //Y según esto, el usuario u01 tiene solo 2 dispositivos...¿¿¿????????????
	
		for (int i=0; i<1000; i++) {
			u02.addDispositivos(d04 = new Dispositivo(""));
		}
	
		assertTrue(u02.getNumDispositivos() == 1); //¿¿Cuántos dispositivos piensas que debería tener el usuario u02???
		
		
		assertTrue(u01.enviarMensaje("iPhone 15", "este es un comentario de tranquilidad")); //d011 esta en la lista de dispositivos de u01
		assertTrue(u01.enviarMensaje("Samsung Galaxy", "este es un segundo comentario"));
		assertTrue(u01.enviarMensaje("iPhone 15", "este es un tercer comentario"));
		assertFalse(u01.enviarMensaje("", "este es un cuarto comentario")); //este nuevo dispositivo no esta en la lista de u01
	 
		assertTrue(u02.enviarMensaje("", "este es un quinto comentario")); 
		assertTrue(u02.enviarMensaje("", "que ocurre aqui")); 
	
		assertEquals("[comentario, de, es, este, segundo, tercer, tranquilidad, un]", u01.getPalabras().toString());
		assertEquals("[aqui, comentario, es, este, ocurre, que, quinto, un]", u02.getPalabras().toString());
		
		//Comprobar que se vacían correctamente...
		
		assertTrue(u01.getNumPalabras("iPhone 15") == 7); 
		assertTrue(u01.getNumPalabras("Samsung Galaxy") == 5);
		assertTrue(u01.getNumPalabras("") == 0);
		assertTrue(u02.getNumPalabras("iPhone 15") == 0); 
		assertTrue(u02.getNumPalabras("Samsung Galaxy") == 0);
		assertTrue(u02.getNumPalabras("") == 8);
		
		assertTrue(d01.getNumPalabras() == 0); //¿No debería ser = 7?
		assertTrue(d02.getNumPalabras() == 0); //¿No debería ser = 5? 
		assertTrue(d03.getNumPalabras() == 0); //¿No debería ser = 7?
		assertTrue(d04.getNumPalabras() == 0); //¿No debería ser = 5?
		
		//Si no entiendes estas últimas restricciones es que no tienes claro el concepto de referencia, así que ¡NO SIGAS!, ¡PREGUNTA! 
		
		u01.vaciarDispositivos(); 
		u02.vaciarDispositivos();
		
		assertTrue(u01.getNumPalabras("iPhone 15") == 0);
		assertTrue(u01.getNumPalabras("Samsung Galaxy") == 0);
		assertTrue(u02.getNumPalabras("") == 0);
		assertTrue(u01.getNumDispositivos() == 0);
		assertTrue(u02.getNumDispositivos() == 0);
		
		u01 = u02 = null;
		d01 = d02 = d03 = d04 = null;
	}
	
	@Test
	@Order(5)
	public void testGestionUsuariosInterseccion() {
		Usuario.inicializarNumUsuarios();

		GestionUsuarios gestion = new GestionUsuarios();
		Usuario usuario01 = new Usuario("Bob");
		Usuario usuario02 = new Usuario("Alice");
		Usuario usuario03 = new Usuario("Chace");
		Usuario usuario04 = new Usuario("Eve");
		Dispositivo dispositivo01 = new Dispositivo("disp01", "1110001000");
		Dispositivo dispositivo02 = new Dispositivo("disp02", "1111110000");
		Dispositivo dispositivo03 = new Dispositivo("disp03", "0001110001");
		Dispositivo dispositivo04 = new Dispositivo("disp04", "1110001111");

		gestion.addDispositivos(usuario01, new Dispositivo("disp05", "1000000001"));
		gestion.addDispositivos(usuario02, dispositivo01);
		gestion.addDispositivos(usuario02, dispositivo01, dispositivo02);
		gestion.addDispositivos(usuario03, dispositivo01, dispositivo03);
		gestion.addDispositivos(usuario04, dispositivo03, dispositivo04);
	
		assertEquals("[1.- Bob -> [disp05 {0, 9}], " +
				     "2.- Alice -> [disp01 {0, 1, 2, 6}, disp02 {0, 1, 2, 3, 4, 5}], " +
				     "3.- Chace -> [disp01 {0, 1, 2, 6}, disp03 {3, 4, 5, 9}], " +
				     "4.- Eve -> [disp03 {3, 4, 5, 9}, disp04 {0, 1, 2, 6, 7, 8, 9}]]", 
				      gestion.toString());
		
		assertEquals("{0}", usuario01.getInterseccion(usuario02).toString());
		assertEquals("{0, 9}", usuario01.getInterseccion(usuario03).toString());
		assertEquals("{0, 9}", usuario01.getInterseccion(usuario04).toString());
		assertEquals("{0, 1, 2, 3, 4, 5, 6}", usuario02.getInterseccion(usuario03).toString());
		assertEquals("{0, 1, 2, 3, 4, 5, 6}", usuario02.getInterseccion(usuario04).toString());
		assertEquals("{0, 1, 2, 3, 4, 5, 6, 9}", usuario03.getInterseccion(usuario04).toString());
		assertEquals("Bob -> Alice <1> Chace <2> Eve <2> ", gestion.getGradosSimilitud(usuario01));
		assertEquals("Alice -> Bob <1> Chace <7> Eve <7> ", gestion.getGradosSimilitud(usuario02));
		assertEquals("Chace -> Bob <2> Alice <7> Eve <8> ", gestion.getGradosSimilitud(usuario03));
		assertEquals("Eve -> Bob <2> Alice <7> Chace <8> ", gestion.getGradosSimilitud(usuario04));
	}
	
	@Test
	@Order(5)
	public void testGestionUsuariosExtra() {
		Usuario.inicializarNumUsuarios();
		
		GestionUsuarios gestion = new GestionUsuarios();
		
		gestion.addDispositivos(new Usuario("Bob"), new Dispositivo("disp01"));
		gestion.addDispositivos(new Usuario("Bob"), new Dispositivo("disp02"));
		gestion.addDispositivos(new Usuario("Alice"), new Dispositivo("disp01"));
		gestion.addDispositivos(new Usuario("Chace"), new Dispositivo("disp02"));
		gestion.addDispositivos(new Usuario("Eve"), new Dispositivo("disp01"), new Dispositivo("disp02"));
		
		
		assertTrue(gestion.enviarMensaje("Bob", "disp01", "palabra01, palabra02,.....       palabra03"));
		assertTrue(gestion.enviarMensaje("Alice", "disp01", "palabra04"));
		assertTrue(gestion.enviarMensaje("Chace", "disp02",  "palabra05, palabra06."));
		assertTrue(gestion.enviarMensaje("Bob", "disp02",  "palabra01, palabra07, palabra08,,,,"));
		assertTrue(gestion.enviarMensaje("Bob", "disp01",  "palabra01, palabra09,,,,"));
		assertFalse(gestion.enviarMensaje("Boby", "disp01",  "palabra01, palabra09,,,,"));

		assertNull(gestion.getPalabrasUsuario("Eva"));
		assertEquals("[palabra01, palabra02, palabra03, palabra09, palabra07, palabra08]", gestion.getPalabrasUsuario("Bob").toString()); //¿Es correcto el orden de las palabras? ¿Palabra09 antes que 07 y 08?
		assertEquals("[palabra04]", gestion.getPalabrasUsuario("Alice").toString());
		assertEquals("[palabra05, palabra06]", gestion.getPalabrasUsuario("Chace").toString());
		assertEquals("[]", gestion.getPalabrasUsuario("Eve").toString()); //Si Eve "comparte" con el resto de usuarios los dispositivos disp01 y disp02, ¿por qué sale vacío el conjunto de palabras?
		
		//Si el problema exige que se puedan compartir dispositivos entre distintos usuarios, ¿qué cambios habría que hacer en el código? ¡Piensa en un diseño alternativo!
	}
}