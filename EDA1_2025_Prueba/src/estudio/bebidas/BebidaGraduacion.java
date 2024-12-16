package estudio.bebidas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.TreeSet;

public class BebidaGraduacion {
	
	//TreeMap<TIPO, TreeMap<NOMBRE, GRADUACION>>

	//	ESTO ES UN EJEMPLO DE ESTRUCTURA
	//	-----------------------------------------
	//	| 			| nombre1 -> graduacion1	|
	//	|	tipo1	| nombre2 -> graduacion2	|
	//	| 			| nombre3 -> graduacion3	|
	//	|---------------------------------------|
	//	|			| nombre4 -> graduacion4	|
	//	|	tipo2	| nombre5 -> graduacion5	|
	//	|---------------------------------------|
	//	| 	tipo3	| nombre6 -> graduacion6	|
	//	-----------------------------------------
	private TreeMap<String, TreeMap<String, Double>> tm;

	public TreeMap<String, TreeMap<String, Double>> getTm() {
		return tm;
	}
	
	public BebidaGraduacion(String archivo) throws FileNotFoundException{
		cargarDatos(archivo);
	}
	
	private void cargarDatos(String archivo) throws FileNotFoundException{
		tm=new TreeMap<String, TreeMap<String,Double>>();
		File f = new File(archivo);
		Scanner sc = new Scanner(f);
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			String[] tokens = s.split(";");
			String tipo=tokens[0];
			String nombre=tokens[1];
			double graduacion=Double.parseDouble(tokens[2]);
			addBebida(tipo, nombre, graduacion);
		}
		sc.close();
		
	}

	public void addBebida(String tipo, String nombre, double graduacion){
		//TODO Agrega una bebida con nombre y graduacion, atendiendo a su tipo
		//Este método es utilizado en el método "cargarDatos"
	}
	
	public String listarBebidasPorTipo(){
		//TODO Proporciona un listado de bebidas (tipo - nombre (graduacion))
		//Ordenado por tipo de bebida
		return null;
	}
	
	public String listarBebidasConGraduacionSuperiorIgualA(double graduacion){
		//TODO Proporciona un listado de bebidas (tipo - nombre (graduacion)) con graduacion superior a la pasada por parametro
		//Ordenado por tipo de bebida
		return null;
	}
	
	public String listarBebidasDeTipo(String tipo){
		//TODO Proporciona un listado de bebidas (tipo - nombre (graduacion)) del tipo especificado por parametro
		return null;
	}
	
	public int contarBebidasDeTipo(String tipo){
		//TODO Cuenta las bebidas del tipo especificado por parámetro
		return 0;
	}
	
	public int contarBebidasConGraduacionSuperiorA(double graduacion){
		//TODO Cuenta las bebidas con graduacion superior a la especificada por parametro
		return 0;
	}
	
	public String listadoBebidasPorGraduacion(){
		//TODO Proporciona un listado de bebidas ordenado de forma ascendente segun su graduacion (tipo - nombre (graduacion))
		return null;
	}
	
	public String listarTiposDeBebida(){
		//TODO Proporciona un listado de los tipos de bebida
		return null;
	}
	
	public String listarNombresDeBebida(){
		//TODO Proporciona un listado de los nombres de las bebidas
		return null;
	}
	
	public double mediaAlcohol(){
		//TODO Calcula la media de alcohol total por botella
		return 0;
	}

}
