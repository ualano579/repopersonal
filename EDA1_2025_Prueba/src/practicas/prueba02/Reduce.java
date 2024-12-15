package practicas.prueba02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Reduce {
	TreeMap<String, TreeMap<String, ArrayList<Double>>> datosOrigen = new TreeMap<>();
	TreeMap<String, Double> datosDestino = new TreeMap<>();
	
	public void add(String mainKey, String secondKey, Double...values) {
		TreeMap<String, ArrayList<Double>> mainValues = this.datosOrigen.get(mainKey);
		if (mainValues == null) this.datosOrigen.put(mainKey, mainValues = new TreeMap<>());
		ArrayList<Double> secondValues = mainValues.get(secondKey);
		if (secondValues == null) mainValues.put(secondKey, secondValues = new ArrayList<>());
		secondValues.addAll(List.of(values));
	}
	
	public void reduce() {
		//Observa en datosOrigen la relación entre mK0X (puede ser un identificador de alumno, sK0X (identificador de asignatura) y los valores (que pueden ser notas)
		//Observa en check() cuál es la operación que debes realizar sobre la clave de interés (sK0X --- ignorando mK0X) 
		TreeMap<String, ArrayList<Double>> aux = new TreeMap<>();
		for (TreeMap<String, ArrayList<Double>> t : datosOrigen.values()) {
			for (Entry<String, ArrayList<Double>> par : t.entrySet()) {
				ArrayList<Double> array = aux.get(par.getKey());
				if(array == null) aux.put(par.getKey(), array = new ArrayList<>());
				array.addAll(par.getValue());
			}
		}
		for (Entry<String, ArrayList<Double>> par3 : aux.entrySet()) {
			datosDestino.put(par3.getKey(), Collections.max(par3.getValue()));
		}
	}
	
	public boolean check() {
		return "{sK01=9.5, sK02=10.0, sK03=8.3}".equals(this.datosDestino.toString());
	}
	
	@Override
	public String toString() {
		return this.datosOrigen.toString();

	}
	public static void main(String[] args) {
		Reduce ejercicio = new Reduce();
		ejercicio.add("mK01",  "sK01", 3.5, 0.3, 2.8, 4.4, 9.5);
		ejercicio.add("mK01",  "sK02", 6.5, 9.8, 4.5, 6.6, 3.3);
		ejercicio.add("mK02",  "sK01", 3.3, 7.3, 6.3, 9.3, 7.3, 5.5);
		ejercicio.add("mK02",  "sK02", 2.2, 10., 9.5, 6.6, 7.4);
		ejercicio.add("mK02",  "sK03", 3.3, 3.5, 6.9, 7.8, 8.3, 6.6);
		
		System.out.println(ejercicio.toString());
		ejercicio.reduce();
		System.out.println(ejercicio.check() ? "¡¡¡OK!!!" : "¡¡¡Error!!!");

	}
}
