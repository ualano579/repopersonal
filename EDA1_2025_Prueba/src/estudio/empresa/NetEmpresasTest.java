package estudio.empresa;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class NetEmpresasTest {
	private byte[] b = {69, 108, 32, 104, 97, 99, 107, 101, 114, 32, 113, 117, 101, 32, 101, 115, 116, 97, 109, 111, 115, 32, 98, 117, 115, 99, 97, 110, 100, 111, 32, 115, 101, 32, 108, 108, 97, 109, 97, 32};
	private String directorioEntrada = System.getProperty("user.dir")
			+ File.separator + "src"
			+ File.separator + "estudio"
			+ File.separator + "empresa"
			+ File.separator;

    @Test
    public void testNetEmpresas() {
    	NetEmpresas net = new NetEmpresas();
    	net.load(directorioEntrada+"netempresasdata.txt");
    	assertEquals(128000.0, net.obtenerSueldoMedioTodasEmpresas(), 0.01);
        System.out.println(new String(b)+net.obtenerPersonasConMayorSueldo().toString().replaceAll("[\\[\\]]","").toUpperCase());
    }


}
