package estudio.empresa;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NetEmpresas extends GestionEmpresa {
	
	private enum Secciones {
		Empresas, Empleados, Actualizaciones;
		private String[] items;
		void load(NetEmpresas net, String line){
			switch (this){
			case Empresas: 
				net.agregarEmpresa(line);
				break;
			case Empleados:
				items = line.split(" - ");
				net.agregarEmpleado(items[0], items[1], Double.parseDouble(items[3]));
				break;
			case Actualizaciones:
				items = line.split(" - ");
				net.actualizarSueldo(items[0], items[1], Double.parseDouble(items[3]));
				break;
			}
			
		}
	}
	
	public void load(String filename){
		String line = "";
		Scanner scan = null;
		Secciones seccion = null; 
		
		try{
			scan = new Scanner(new File(filename), StandardCharsets.UTF_8.name());
		}catch(Exception e){
			System.out.println("Error al cargar el archivo --> " + e.getMessage());
			System.exit(-1);
		}
		
		while(scan.hasNextLine()){
			line = scan.nextLine().trim();
			if (line.isEmpty()) continue;
			if (line.startsWith("%")) continue;
			
			if (line.toLowerCase().contains("@empresas")){
				seccion = Secciones.Empresas;
				continue;
			}
			if (line.toLowerCase().contains("@empleados")){
				seccion = Secciones.Empleados;
				continue;
			}
			if (line.toLowerCase().contains("@actualizaciones")){
				seccion = Secciones.Actualizaciones;
				continue;
			}
			if (seccion == null) {
				System.out.println("Formato de archivo erroneo");
				System.exit(-1);
			}
			seccion.load(this, line);
		}
	}
	
}
