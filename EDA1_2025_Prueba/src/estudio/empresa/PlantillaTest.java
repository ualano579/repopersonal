package estudio.empresa;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PlantillaTest {
    private Plantilla e;

    @Before
    public void setUp() {
        e = new Plantilla();
    }

    @Test
    public void testAgregarPersona() {
    	assertTrue(e.agregarPersona("Juan", 50000.0));
    	assertTrue(e.agregarPersona("Maria", 40000.0));
    	assertFalse(e.agregarPersona("juan", 50000.0));
    	assertFalse(e.agregarPersona("Juan", 30000.0));
    	assertFalse(e.agregarPersona("MARIA", 60000.0));
    }

    @Test
    public void testEliminarPersona() {
        e.agregarPersona("Ana", 60000.0);
        e.agregarPersona("ana", 60000.0);
        e.agregarPersona("Luis", 20000.0);
        assertTrue(e.eliminarPersona("Ana"));
        assertFalse(e.eliminarPersona("Lluis"));
        assertTrue(e.eliminarPersona("luis"));
    }

    @Test
    public void testObtenerListadoPersonas() {
        e.agregarPersona("Carlos", 70000.0);
        e.agregarPersona("Maria", 80000.0);
        assertEquals(2, e.obtenerListadoPersonas().size());
        assertTrue(e.obtenerListadoPersonas().contains("carlos"));
        assertTrue(e.obtenerListadoPersonas().contains("maria"));
        assertEquals("[carlos, maria]", e.obtenerListadoPersonas().toString());
    }
    
    @Test
    public void testBuscarPersonaExistente() {
        e.agregarPersona("Juan", 50000.0);
        Double sueldo = e.buscarPersona("Juan");
        assertEquals(50000.0, sueldo, 0.01);
        e.agregarPersona("Ana", 60000.0);
        sueldo = e.buscarPersona("Ana");
        assertEquals(60000.0, sueldo, 0.01);
        assertNull(e.buscarPersona("Luis"));
        
    }
    
    @Test
    public void testObtenerSueldo() {
    	e.agregarPersona("Juan", 50000.0);
        assertTrue(e.obtenerSueldo("Juan") == 50000.0);
        e.agregarPersona("Ana", 60000.0);
        assertTrue(e.obtenerSueldo("Ana") == 60000.0);
        assertNotNull(e.obtenerSueldo("Luis"));
        assertTrue(e.obtenerSueldo("Luis") == 0.0);
    }
    
    @Test
    public void testIncrementarSueldo() {
        e.agregarPersona("Juan", 50000.0);
        assertTrue(e.incrementarSueldo("Juan", 10000.0));
        assertEquals(60000.0, e.obtenerSueldo("Juan"), 0.01);
        assertFalse(e.incrementarSueldo("Maria", 10000.0));
    }
    
    @Test
    public void testIncrementoPorcentualSueldos() {
        e.agregarPersona("Juan", 50000.0);
        e.agregarPersona("Maria", 60000.0);
        e.incrementoPorcentualSueldos(10.0);

        assertEquals(55000.0, e.obtenerSueldo("Juan"), 0.01);
        assertEquals(66000.0, e.obtenerSueldo("Maria"), 0.01);
    }
    
    @Test
    public void testObtenerPersonaConMayorSueldo() {
        e.agregarPersona("Juan", 50000.0);
        e.agregarPersona("Maria", 60000.0);
        e.agregarPersona("Carlos", 55000.0);
        assertEquals("[maria]", e.obtenerPersonasConMayorSueldo().toString());
        e.agregarPersona("Luis", 60000.0);
        assertEquals("[luis, maria]", e.obtenerPersonasConMayorSueldo().toString());
        e.agregarPersona("Roberto", 75000.0);
        assertEquals("[roberto]", e.obtenerPersonasConMayorSueldo().toString());
        
    }
    
    @Test
    public void testObtenerSueldoMedio() {
    	assertEquals(0.0, e.obtenerSueldoMedio(), 0.01);
        e.agregarPersona("Juan", 50000.0);
        e.agregarPersona("Maria", 60000.0);
        e.agregarPersona("Carlos", 55000.0);
        assertEquals(55000.0, e.obtenerSueldoMedio(), 0.01);
        e.agregarPersona("Juan", 10000.0);
        assertEquals(55000.0, e.obtenerSueldoMedio(), 0.01);
        e.agregarPersona("Luis", 75000.0);
        assertEquals(60000.0, e.obtenerSueldoMedio(), 0.01);
    }
    
    @Test
    public void testActualizarSueldo() {
        e.agregarPersona("Juan", 50000.0);
        assertTrue(e.actualizarSueldo("Juan", 60000.0));
        assertEquals(60000.0, e.obtenerSueldo("Juan"), 0.01);
        assertFalse(e.actualizarSueldo("Maria", 60000.0));
    }

}
