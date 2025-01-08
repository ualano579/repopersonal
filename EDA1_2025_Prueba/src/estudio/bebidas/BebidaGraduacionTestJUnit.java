package estudio.bebidas;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class BebidaGraduacionTestJUnit {
	String dir=System.getProperty("user.dir")+File.separator+"src"+File.separator+
			"estudio"+File.separator+"bebidas"+File.separator;
	String archivo;
	BebidaGraduacion bg;

	@Before
	public void setUp() throws Exception {
		archivo=dir+"bebidas.txt";
		bg=new BebidaGraduacion(archivo);
	}

	@Test
	public void testBebidaGraduacion() {
		try {
			bg=new BebidaGraduacion(dir+"unknownFile.txt");
			fail("Debe haberse lanzado la excepción");
		} catch (FileNotFoundException e) {
			assertTrue(true);
		}
		
		try {
			bg=new BebidaGraduacion(archivo);
			assertTrue(true);
		} catch (FileNotFoundException e) {
			fail("NO debe haberse lanzado la excepción");
		}
		
		
	}

	@Test
	public void testListarBebidasPorTipo() {
		String expected="Absenta - Hapsburg Gold (90.0º)"+
						"\nCerveza - Armageddon (65.0º)"+
						"\nCerveza - Baladin (40.0º)"+
						"\nCerveza - Brewdog (41.0º)"+
						"\nCerveza - Budweiser (12.0º)"+
						"\nCerveza - Koelschip (45.0º)"+
						"\nCerveza - Snake Venom (67.0º)"+
						"\nGinebra - Beefeater (38.0º)"+
						"\nGinebra - Sheriton (36.0º)"+
						"\nRon - Bacardi (75.0º)"+
						"\nRon - Barcelo (35.0º)"+
						"\nRon - Cacique (37.0º)"+
						"\nRon - Flor de caña (41.0º)"+
						"\nRon - Negrita (38.0º)"+
						"\nRon - Palido Montero (40.0º)"+
						"\nRon - Santa Teresa (39.0º)"+
						"\nTequila - Sierra Silver (75.0º)"+
						"\nVodka - Absolut (40.0º)"+
						"\nVodka - Smirnoff (39.0º)"+
						"\nVodka - Spirytus (96.0º)"+
						"\nWhisky - DyC (39.0º)"+
						"\nWhisky - J&B (40.0º)\n";
		assertEquals(expected, bg.listarBebidasPorTipo());
	}

	@Test
	public void testListarBebidasConGraduacionSuperiorIgualA() {
		String expected="Absenta - Hapsburg Gold (90.0º)"+
						"\nCerveza - Armageddon (65.0º)"+
						"\nCerveza - Baladin (40.0º)"+
						"\nCerveza - Brewdog (41.0º)"+
						"\nCerveza - Koelschip (45.0º)"+
						"\nCerveza - Snake Venom (67.0º)"+
						"\nRon - Bacardi (75.0º)"+
						"\nRon - Flor de caña (41.0º)"+
						"\nRon - Palido Montero (40.0º)"+
						"\nTequila - Sierra Silver (75.0º)"+
						"\nVodka - Absolut (40.0º)"+
						"\nVodka - Spirytus (96.0º)"+
						"\nWhisky - J&B (40.0º)"+
						"\n";
		assertEquals(expected, bg.listarBebidasConGraduacionSuperiorIgualA(40));
		assertEquals("", bg.listarBebidasConGraduacionSuperiorIgualA(100));
	}

	@Test
	public void testListarBebidasDeTipo() {
		String expected="Vodka - Absolut (40.0º)"+
						"\nVodka - Smirnoff (39.0º)"+
						"\nVodka - Spirytus (96.0º)"+
						"\n";
		assertEquals(expected, bg.listarBebidasDeTipo("Vodka"));
		
		expected="Absenta - Hapsburg Gold (90.0º)\n";
		assertEquals(expected, bg.listarBebidasDeTipo("Absenta"));
	}

	@Test
	public void testContarBebidasDeTipo() {
		int expected=3;
		assertEquals(expected, bg.contarBebidasDeTipo("Vodka"));
		expected=0;
		assertEquals(expected, bg.contarBebidasDeTipo("Agua"));
	}

	@Test
	public void testContarBebidasConGraduacionSuperiorA() {
		int expected=6;
		assertEquals(expected, bg.contarBebidasConGraduacionSuperiorA(50));
		expected=0;
		assertEquals(expected, bg.contarBebidasConGraduacionSuperiorA(100));
	}

	@Test
	public void testListadoBebidasPorGraduacion() {
		bg.getTm().get("Ron").put("LEGENDARIO", 39.0);
		String expected="Cerveza - Budweiser (12.0º)"+
						"\nRon - Barcelo (35.0º)"+
						"\nGinebra - Sheriton (36.0º)"+
						"\nRon - Cacique (37.0º)"+
						"\nGinebra - Beefeater (38.0º)"+
						"\nRon - Negrita (38.0º)"+
						"\nRon - LEGENDARIO (39.0º)"+
						"\nRon - Santa Teresa (39.0º)"+
						"\nVodka - Smirnoff (39.0º)"+
						"\nWhisky - DyC (39.0º)"+
						"\nCerveza - Baladin (40.0º)"+
						"\nRon - Palido Montero (40.0º)"+
						"\nVodka - Absolut (40.0º)"+
						"\nWhisky - J&B (40.0º)"+
						"\nCerveza - Brewdog (41.0º)"+
						"\nRon - Flor de caña (41.0º)"+
						"\nCerveza - Koelschip (45.0º)"+
						"\nCerveza - Armageddon (65.0º)"+
						"\nCerveza - Snake Venom (67.0º)"+
						"\nRon - Bacardi (75.0º)"+
						"\nTequila - Sierra Silver (75.0º)"+
						"\nAbsenta - Hapsburg Gold (90.0º)"+
						"\nVodka - Spirytus (96.0º)"+
						"\n";
		assertEquals(expected, bg.listadoBebidasPorGraduacion());
	}

	@Test
	public void testListarTiposDeBebida() {
		String expected="Absenta, Cerveza, Ginebra, Ron, Tequila, Vodka, Whisky";
		assertEquals(expected, bg.listarTiposDeBebida());
	}

	@Test
	public void testListarNombresDeBebida() {
		bg.getTm().get("Vodka").put("Negrita", 96.0);
		String expected="Absolut, Armageddon, Bacardi, Baladin, Barcelo, Beefeater, "
				+ "Brewdog, Budweiser, Cacique, DyC, Flor de caña, Hapsburg Gold, J&B, "
				+ "Koelschip, Negrita, Palido Montero, Santa Teresa, Sheriton, "
				+ "Sierra Silver, Smirnoff, Snake Venom, Spirytus";
		assertEquals(expected, bg.listarNombresDeBebida());
	}

	@Test
	public void testMediaAlcohol() {
		assertTrue(bg.mediaAlcohol()==48.54545454545455);
	}

}
