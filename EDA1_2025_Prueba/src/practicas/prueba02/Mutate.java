package practicas.prueba02;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class Mutate {
	private TreeMap<String, TreeMap<String, TreeSet<String>>> datosOrigen = new TreeMap<>();
	private TreeMap<String, TreeMap<String, TreeSet<String>>> datosDestino = new TreeMap<>();
	
	public void add(String mainKey, String secondKey, String...values) {
		TreeMap<String, TreeSet<String>> mainValues = this.datosOrigen.get(mainKey);
		if (mainValues == null) this.datosOrigen.put(mainKey, mainValues = new TreeMap<>());
		TreeSet<String> secondValues = mainValues.get(secondKey);
		if (secondValues == null) mainValues.put(secondKey, secondValues = new TreeSet<>());
		secondValues.addAll(List.of(values));
	}
	
	public void mutate() {
//	public TreeMap<String, TreeMap<String, TreeSet<String>>> mutate() {
//		TreeMap<String, TreeMap<String, TreeSet<String>>> result = new TreeMap<>();
		//Observa en datosOrigen la relación entre mK0X (mainKey), sK0X (secondaryKey) y w0X (valores)
		//En datosDestino, la clave principal será w0X, la clave secundaria será mK0X, teniendo a sK0X como valores de tercer nivel 
		for (Entry<String, TreeMap<String, TreeSet<String>>> par : datosOrigen.entrySet()) {
			for (Entry<String, TreeSet<String>> par2 : par.getValue().entrySet()) {
				for (String s : par2.getValue()) {
					TreeMap<String, TreeSet<String>> aux = datosDestino.get(s);
					if(aux == null) datosDestino.put(s, aux = new TreeMap<>());
					TreeSet<String> aux2 = aux.get(par.getKey());
					if(aux2 == null) aux.put(par.getKey(), aux2 = new TreeSet<>());
					aux2.add(par2.getKey());
				}
			}
		}
//		return result;
	}
	
	public boolean check() {
		boolean w01 = "{mK01=[sK01], mK02=[sK01, sK02, sK03]}".equals(this.datosDestino.get("w01").toString());
		boolean w02 = "{mK01=[sK01], mK02=[sK01, sK03]}".equals(this.datosDestino.get("w02").toString());
		boolean w03 = "{mK01=[sK01, sK02], mK02=[sK01, sK03]}".equals(this.datosDestino.get("w03").toString());
		boolean w04 = "{mK01=[sK01, sK02], mK02=[sK01, sK02]}".equals(this.datosDestino.get("w04").toString());
		return w01 && w02 && w03 && w04;
	}
	
	@Override
	public String toString() {
		return this.datosOrigen.toString();

	}
	public static void main(String[] args) {
		Mutate ejercicio = new Mutate();
		ejercicio.add("mK01",  "sK01", "w01", "w02", "w03", "w04");
		ejercicio.add("mK01",  "sK02", "w03", "w04");
		ejercicio.add("mK02",  "sK01", "w01", "w02", "w03", "w04");
		ejercicio.add("mK02",  "sK02", "w01", "w04");
		ejercicio.add("mK02",  "sK03", "w01", "w02", "w03");
		
		System.out.println(ejercicio);
		ejercicio.mutate();
		System.out.println(ejercicio.check() ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}
}
