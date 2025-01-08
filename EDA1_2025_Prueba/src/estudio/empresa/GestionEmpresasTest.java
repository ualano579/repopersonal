package estudio.empresa;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GestionEmpresasTest {
    private GestionEmpresa empresa;

    @Before
    public void setUp() {
        empresa = new GestionEmpresa();
    }
    
    @Test
    public void testAgregarEmpresa() {
    	assertTrue(empresa.agregarEmpresa("EmpresaA"));
        assertTrue(empresa.agregarEmpresa("EmpresaB"));
        assertFalse(empresa.agregarEmpresa("EmpresaA"));
    }

    @Test
    public void testAgregarEmpleado() {
    	testAgregarEmpresa();
        assertTrue(empresa.agregarEmpleado("EmpresaA", "Juan", 50000.0));
        assertFalse(empresa.agregarEmpleado("EmpresaA", "Juan", 30000.0));
        assertTrue(empresa.agregarEmpleado("EmpresaA", "Maria", 40000.0));
        assertTrue(empresa.agregarEmpleado("EmpresaB", "Juan", 60000.0));
    }
    
    @Test
    public void testEliminarEmpleado() {
    	testAgregarEmpleado();
        assertTrue(empresa.eliminarEmpleado("EmpresaA", "Juan"));
        assertFalse(empresa.eliminarEmpleado("EmpresaA", "Juan"));
        assertTrue(empresa.eliminarEmpleado("EmpresaA", "Maria"));
        assertTrue(empresa.eliminarEmpleado("EmpresaB", "Juan"));
    }
    
    @Test
    public void testBuscarPersona() {
    	testAgregarEmpleado();
    	assertTrue(empresa.buscarEmpleado("EmpresaA", "Juan") == 50000.0);
    	assertTrue(empresa.buscarEmpleado("EmpresaB", "Juan") == 60000.0);
    	assertNull(empresa.buscarEmpleado("EmpresaB", "Maria"));
    }
    
    @Test
    public void testObtenerListadoPersonas() {
    	testAgregarEmpleado();
    	assertEquals(2, empresa.obtenerListadoPersonas("EmpresaA").size());
        assertTrue(empresa.obtenerListadoPersonas("EmpresaA").contains("juan"));
        assertTrue(empresa.obtenerListadoPersonas("EmpresaA").contains("maria"));
        assertEquals("[juan, maria]", empresa.obtenerListadoPersonas("EmpresaA").toString());
        assertEquals(1, empresa.obtenerListadoPersonas("EmpresaB").size());
        assertNull(empresa.obtenerListadoPersonas("EmpresaC"));
    }
    
    @Test
    public void testObtenerSueldo() {
    	testAgregarEmpleado();
    	assertTrue(empresa.obtenerSueldo("EmpresaA", "Luis") == 0.0);
    	assertTrue(empresa.obtenerSueldo("EmpresaA", "Juan") == 50000.0);
    	assertTrue(empresa.obtenerSueldo("EmpresaA", "Maria") == 40000.0);
    	assertTrue(empresa.obtenerSueldo("EmpresaB", "Juan") == 60000.0);
    	assertTrue(empresa.obtenerSueldo("EmpresaC", "Juan") == 0.0);
    }
    
    @Test
    public void testIncrementarSueldo() {
    	testAgregarEmpleado();
    	assertTrue(empresa.incrementarSueldo("EmpresaA", "Juan", 1000.0));
    	assertTrue(empresa.obtenerSueldo("EmpresaA", "Juan") == 51000.0);
    	assertTrue(empresa.incrementarSueldo("EmpresaA", "Maria", 2000.0));
    	assertTrue(empresa.obtenerSueldo("EmpresaA", "Maria") == 42000.0);
    	assertFalse(empresa.incrementarSueldo("EmpresaA", "Luis", 5000.0));
    	assertTrue(empresa.incrementarSueldo("EmpresaB", "Juan", -1000.0));
    	assertTrue(empresa.obtenerSueldo("EmpresaB", "Juan") == 59000.0);
    	assertFalse(empresa.incrementarSueldo("EmpresaC", "Juan", 1000.0));
    }

    @Test
    public void testIncrementarSueldoTodosEmpleados() {
    	testAgregarEmpleado();
        empresa.incrementoPorcentualSueldos("EmpresaA", 50);
        assertTrue(empresa.obtenerSueldo("EmpresaA", "Juan") == 75000.0);
        assertTrue(empresa.obtenerSueldo("EmpresaA", "Maria") == 60000.0);
        assertTrue(empresa.obtenerSueldo("EmpresaB", "Juan") == 60000.0);
        empresa.incrementoPorcentualSueldos("EmpresaB", 25);
        assertTrue(empresa.obtenerSueldo("EmpresaB", "Juan") == 75000.0);
        empresa.incrementoPorcentualSueldos("EmpresaC", 10);
        assertTrue(empresa.obtenerSueldo("EmpresaA", "Juan") == 75000.0);

    }
    
    @Test
    public void testIncrementarSueldoTodosEmpleadosTodasEmpresas() {
    	testAgregarEmpleado();
        empresa.incrementoPorcentualSueldos(50);
        assertTrue(empresa.obtenerSueldo("EmpresaA", "Juan") == 75000.0);
        assertTrue(empresa.obtenerSueldo("EmpresaA", "Maria") == 60000.0);
        assertTrue(empresa.obtenerSueldo("EmpresaB", "Juan") == 90000.0);

    }
    
    @Test
    public void testObtenerPersonaConMayorSueldo() {
    	testAgregarEmpleado();
        assertEquals("[juan]", empresa.obtenerPersonasConMayorSueldo().toString());
        empresa.agregarEmpleado("EmpresaA","Luis", 60000.0);
        assertEquals("[juan, luis]", empresa.obtenerPersonasConMayorSueldo().toString());
        empresa.agregarEmpleado("EmpresaA", "Roberto", 75000.0);
        assertEquals("[roberto]", empresa.obtenerPersonasConMayorSueldo().toString());
        
    }
    
    @Test
    public void testObtenerSueldoMedioEmpresa() {
    	testAgregarEmpleado();
        assertEquals(45000.0, empresa.obtenerSueldoMedio("EmpresaA"), 0.01);
        assertEquals(60000.0, empresa.obtenerSueldoMedio("EmpresaB"), 0.01);
        assertEquals(0.0, empresa.obtenerSueldoMedio("EmpresaC"), 0.01);
    }
    
    @Test
    public void testObtenerSueldoMedioTodasEmpresas() {
    	testAgregarEmpleado();
        assertEquals(50000.0, empresa.obtenerSueldoMedioTodasEmpresas(), 0.01);
        empresa.agregarEmpleado("EmpresaA","Luis", 60000.0);
        assertEquals(52500.0, empresa.obtenerSueldoMedioTodasEmpresas(), 0.01);
    }
    
    @Test
    public void testActualizarSueldo() {
    	testAgregarEmpleado();
        assertTrue(empresa.actualizarSueldo("EmpresaA", "Juan", 60000.0));
        assertEquals(60000.0, empresa.obtenerSueldo("EmpresaA", "Juan"), 0.01);
        assertTrue(empresa.actualizarSueldo("EmpresaA", "Maria", 58000.0));
        assertEquals(58000.0, empresa.obtenerSueldo("EmpresaA", "Maria"), 0.01);
        assertFalse(empresa.actualizarSueldo("EmpresaB","Maria", 60000.0));
        assertFalse(empresa.actualizarSueldo("EmpresaC","Maria", 60000.0));
    }

}
