package practicas.prueba02;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Iterate implements Iterable<Entry<String, ArrayList<String>>> {
	private TreeMap<String, TreeMap<String, ArrayList<String>>> datos = new TreeMap<>();
	
	public void add(String mainKey, String secondKey, String...values) {
		TreeMap<String, ArrayList<String>> mainValues = this.datos.get(mainKey);
		if (mainValues == null) this.datos.put(mainKey, mainValues = new TreeMap<>());
		ArrayList<String> secondValues = mainValues.get(secondKey);
		if (secondValues == null) mainValues.put(secondKey, secondValues = new ArrayList<>());
		secondValues.addAll(List.of(values));
	}
	
	@Override
	public String toString() {
		return this.datos.toString();

	}

	@Override
	public Iterator<Entry<String, ArrayList<String>>> iterator() {
		TreeMap<String, ArrayList<String>> result = new TreeMap<>();
		for (TreeMap<String, ArrayList<String>> t : datos.values()) {
			for (Entry<String, ArrayList<String>> par : t.entrySet()) {
				for (String s : par.getValue()) {
					ArrayList<String> aux = result.get(s);
					if(aux == null) result.put(s, aux = new ArrayList<>());
					if(aux.contains(par.getKey())) continue;
					aux.add(par.getKey());
				}
			}
		}
		return result.entrySet().iterator();
	}

	
	public boolean check(TreeMap<String, ArrayList<String>> aux) {
		boolean w01 = "[sK01, sK02, sK03]".equals(aux.get("w01").toString());
		boolean w02 = "[sK01, sK03]".equals(aux.get("w02").toString());
		boolean w03 = "[sK01, sK02, sK03]".equals(aux.get("w03").toString());
		boolean w04 = "[sK01, sK02]".equals(aux.get("w04").toString());
		return w01 && w02 && w03 && w04;
	}
	
	public static void main(String[] args) {
		Iterate ejercicio = new Iterate();
		ejercicio.add("mK01",  "sK01", "w01", "w02", "w03", "w04", "w01", "w01");
		ejercicio.add("mK01",  "sK02", "w03", "w04", "w04");
		ejercicio.add("mK02",  "sK01", "w01", "w02", "w03", "w04");
		ejercicio.add("mK02",  "sK02", "w01", "w04", "w01", "w04");
		ejercicio.add("mK02",  "sK03", "w01", "w02", "w03", "w02", "w03");
		
		//System.out.println(ejercicio);
		TreeMap<String, ArrayList<String>> aux = new TreeMap<>();
		for (Entry<String, ArrayList<String>> par: ejercicio) {
			aux.put(par.getKey(), par.getValue());
		}
//		System.out.println(aux);
		System.out.println(ejercicio.check(aux) ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}	
}