package practicas.prueba02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Transform {
	private static class Entidad implements Iterable<Entidad>{
		private String key;
		private ArrayList<Entidad> relaciones;
		
		public Entidad(String key) {
			this.key = key;
			this.relaciones = new ArrayList<>();
		}

		@Override
		public int hashCode() {
			return this.key.hashCode();
		}
		
		@Override
		public boolean equals(Object other) {
			return this.hashCode() == other.hashCode();
		}
		
		public void addRelaciones(Entidad...relaciones) {
			for(Entidad relacion: relaciones) {
				if (this.equals(relacion)) continue;
				if (this.relaciones.contains(relacion)) continue;
				this.relaciones.add(relacion);
			}
		}
		
		@Override
		public String toString() {
			String result = this.key + ":\n";
			for (Entidad entidad: this.relaciones) {
				result += "\t" + entidad.key + "\n";
			}
			return result;
		}

		@Override
		public Iterator<Entidad> iterator() {
			return this.relaciones.iterator();
		}
	}
	
	private HashSet<Entidad> datosOrigen = new HashSet<>();
	private HashMap<String, TreeSet<String>> datosDestino = new HashMap<>();
	
	public void add(Entidad...entidades) {
		this.datosOrigen.addAll(List.of(entidades));
	}
	
	public boolean check() {
		boolean k01 = "[k02, k04]".equals(this.datosDestino.get("k01").toString());
		boolean k02 = "[k01, k03, k05, k06]".equals(this.datosDestino.get("k02").toString());
		boolean k03 = "[k01, k02, k06]".equals(this.datosDestino.get("k03").toString());
		boolean k04 = "[k01]".equals(this.datosDestino.get("k04").toString());
		boolean k05 = "[k01, k03]".equals(this.datosDestino.get("k05").toString());
		boolean k06 = "[k02, k04]".equals(this.datosDestino.get("k06").toString());
		return k01 && k02 && k03 && k04 && k05 && k06;
	}
	
	public void transform() {
		for (Entidad entidad : datosOrigen) {
			TreeSet<String> aux = new TreeSet<>();
			for (Entidad s : entidad.relaciones) {
				aux.add(s.key);
			}
			datosDestino.put(entidad.key, aux);
		}
	}
	
	@Override
	public String toString() {
		return this.datosOrigen.toString();
	}
	
	public static void main(String[] args) {
		Transform ejercicio = new Transform();
		
		Entidad entidad01 = new Entidad("k01");
		Entidad entidad02 = new Entidad("k02");
		Entidad entidad03 = new Entidad("k03");
		Entidad entidad04 = new Entidad("k04");
		Entidad entidad05 = new Entidad("k05");
		Entidad entidad06 = new Entidad("k06");
		
		//añadimos relaciones
		entidad01.addRelaciones(entidad01, entidad02, entidad04);
		entidad02.addRelaciones(entidad01, entidad05, entidad03, entidad06);
		entidad03.addRelaciones(entidad01, entidad02, entidad03, entidad06);
		entidad04.addRelaciones(entidad01);
		entidad05.addRelaciones(entidad01, entidad03, entidad05);
		entidad06.addRelaciones(entidad02, entidad04, entidad06);
		
		ejercicio.add(entidad01, entidad02, entidad03, entidad04, entidad05, entidad06);
		
//		System.out.println(ejercicio.toString());
		ejercicio.transform();
		
		System.out.println(ejercicio.check() ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}
}