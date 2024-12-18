package estudio.tree;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class Mapa {
	private TreeMap<String, TreeMap<String, TreeSet<String>>> mapa;
	
	public Mapa() {
		this.mapa = new TreeMap<>();
	}
	
	public void add(String empresa, String proyecto, String... ciudades) {
		TreeMap<String, TreeSet<String>> aux = this.mapa.get(empresa);
		if(aux == null) this.mapa.put(empresa, aux = new TreeMap<>());
		TreeSet<String> aux2 = aux.get(proyecto);
		if(aux2 == null) aux.put(proyecto, aux2 = new TreeSet<>());
		aux2.addAll(List.of(ciudades));
	}
	
	public TreeSet<String> devolverEmpresasCiudad (String ciudad){
		TreeSet<String> result = new TreeSet<>();
		for (Entry<String, TreeMap<String, TreeSet<String>>> par : mapa.entrySet()) {
			for (TreeSet<String> t : par.getValue().values()) {
				if(!t.contains(ciudad)) continue;
				result.add(par.getKey());
			}
			
		}
		
		return result;
	}
	
	public TreeSet<String> devolverCiudadesEmpresa(String empresa){
		TreeSet<String> result = new TreeSet<>();
		TreeMap<String, TreeSet<String>> aux = mapa.get(empresa);
		for (TreeSet<String> t : aux.values()) {
			result.addAll(t);
		}
		return result;
	}
	
	public String devolverEmpresaConMayorNumeroDeProyectos() {
		String result = "";
		int max = 0;
		for (Entry<String, TreeMap<String, TreeSet<String>>> par : mapa.entrySet()) {
			if(par.getValue().keySet().size() > max) {
				max = par.getValue().keySet().size();
				result = par.getKey();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Mapa map = new Mapa();
		
		map.add("google", "ads", "Córdoba", "Sevilla", "Madrid");
		map.add("google", "chrome", "Zaragoza", "Sevilla", "Barcelona");
		map.add("google", "IA", "Córdoba", "Bilbao", "Jaén");
		map.add("apple", "ads", "Almería", "Pontevedra", "Madrid");
		map.add("apple", "iphone", "Barcelona", "Sevilla", "Madrid");
		map.add("intel", "i11", "Albox", "Sevilla", "Madrid");
		
		System.out.println(map.mapa);
		System.out.println(map.devolverEmpresasCiudad("Barcelona"));
		System.out.println(map.devolverCiudadesEmpresa("google"));
		System.out.println(map.devolverEmpresaConMayorNumeroDeProyectos());
	}
}
