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
		TreeMap<String, Double> aux = tm.get(tipo);
		if(aux == null) tm.put(tipo, aux = new TreeMap<>());
		aux.put(nombre, graduacion);
	}
	
	public String listarBebidasPorTipo(){
		//TODO Proporciona un listado de bebidas (tipo - nombre (graduacion))
		//Ordenado por tipo de bebida
		String result = "";
		for (Entry<String, TreeMap<String, Double>> par : tm.entrySet()) {
			for (Entry<String, Double> par2 : par.getValue().entrySet()) {
				result += par.getKey() + " - " + par2.getKey() + " (" + par2.getValue() + "º)\n";
			}
		}
		return result;
	}
	
	public String listarBebidasConGraduacionSuperiorIgualA(double graduacion){
		//TODO Proporciona un listado de bebidas (tipo - nombre (graduacion)) con graduacion superior a la pasada por parametro
		//Ordenado por tipo de bebida
		String result = "";
		for (Entry<String, TreeMap<String, Double>> par : tm.entrySet()) {
			for (Entry<String, Double> par2 : par.getValue().entrySet()) {
				if(par2.getValue() >= graduacion) result += par.getKey() + " - " + par2.getKey() + " (" + par2.getValue() + "º)\n";
			}
		}
		return result;
	}
	
	public String listarBebidasDeTipo(String tipo){
		//TODO Proporciona un listado de bebidas (tipo - nombre (graduacion)) del tipo especificado por parametro
		String result = "";
		TreeMap<String, Double> aux = tm.get(tipo);
		for (Entry<String, Double> par : aux.entrySet()) {
			result += tipo + " - " + par.getKey() + " (" + par.getValue() + "º)\n";
		}
		return result;
	}
	
	public int contarBebidasDeTipo(String tipo){
		//TODO Cuenta las bebidas del tipo especificado por parámetro
		TreeMap<String, Double> aux = tm.get(tipo);
		return aux == null ? 0 : aux.size();
	}
	
	public int contarBebidasConGraduacionSuperiorA(double graduacion){
		//TODO Cuenta las bebidas con graduacion superior a la especificada por parametro
		int result = 0;
		for (Entry<String, TreeMap<String, Double>> par : tm.entrySet()) {
			for (Entry<String, Double> par2 : par.getValue().entrySet()) {
				if(par2.getValue() >= graduacion) result++;
			}
		}
		return result;
	}
	
//	public int contarBebidasConGraduacionSuperiorA(double graduacion){
//		//TODO Cuenta las bebidas con graduacion superior a la especificada por parametro
//		return listarBebidasConGraduacionSuperiorIgualA(graduacion).split("\n").length;
//	}
	
	public String listadoBebidasPorGraduacion(){
		//TODO Proporciona un listado de bebidas ordenado de forma ascendente segun su graduacion (tipo - nombre (graduacion))
		TreeMap<Double, TreeSet<String>> aux = new TreeMap<>();
		for (Entry<String, TreeMap<String, Double>> par : tm.entrySet()) {
			for (Entry<String, Double> par2 : par.getValue().entrySet()) {
				TreeSet<String> t = aux.get(par2.getValue());
				if(t == null) aux.put(par2.getValue(), t = new TreeSet<>());
				t.add(par.getKey() + " - " + par2.getKey() + " (" + par2.getValue() + "º)");
			}
		}
		String result = "";
		for (TreeSet<String> par : aux.values()) {
			for (String s : par) {
				result += s + "\n";
			}
		}
		
		return result;
	}
	
	public String listarTiposDeBebida(){
		//TODO Proporciona un listado de los tipos de bebida
		return tm.keySet().toString().replace("[", "").replace("]", "");
	}
	
	public String listarNombresDeBebida(){
		//TODO Proporciona un listado de los nombres de las bebidas
		TreeSet<String> result = new TreeSet<>();
		for (TreeMap<String, Double> t : tm.values()) {
			result.addAll(t.keySet());
		}
		return result.toString().replace("[", "").replace("]", "");
	}
	
	public double mediaAlcohol(){
		//TODO Calcula la media de alcohol total por botella
		int n = 0;
		double suma = 0;
		for (TreeMap<String, Double> t : tm.values()) {
			for (Double d : t.values()) {
				suma += d;
				n++;
			}
		}
		return suma/n;
	}

}
