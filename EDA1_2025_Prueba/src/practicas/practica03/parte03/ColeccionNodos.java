package practicas.practica03.parte03;

import java.util.*;
import java.util.Map.Entry;

public class ColeccionNodos {
	
	protected final HashMap<NodoComponentes, HashSet<NodoComponentes>> data;

	public ColeccionNodos(){
		this.data = new HashMap<>();
	}
	
	public void add(NodoComponentes nodoOrigen, NodoComponentes nodoDestino) {
		//1 get()
		//1 put()
		HashSet<NodoComponentes> aux = this.data.get(nodoOrigen);
		if(aux == null) this.data.put(nodoOrigen, aux = new HashSet<>());
		aux.add(nodoDestino);
	}

	public int size() {
		return this.data.size();
	}

	public void clear(){
		this.data.clear();
	}
	    
	private TreeMap<NodoComponentes, TreeSet<NodoComponentes>> toOrderedCollection() {
		TreeMap<NodoComponentes, TreeSet<NodoComponentes>> result = new TreeMap<>();
		//1 for()
		for (Entry<NodoComponentes, HashSet<NodoComponentes>> par : this.data.entrySet()) {
			TreeSet<NodoComponentes> aux = result.get(par.getKey());
			if(aux == null) result.put(par.getKey(), aux = new TreeSet<>());
			aux.addAll(par.getValue());
		}
		
		return result;
	}

	@Override
	public String toString() {
		TreeMap<NodoComponentes, TreeSet<NodoComponentes>> aux = this.toOrderedCollection();
		String result = "";
		//2 for()
		//Fíjate el formato de salidaEsperada especificado en el método main()
		for (Entry<NodoComponentes, TreeSet<NodoComponentes>> par : aux.entrySet()) {
			result += par.getKey() + "\n";
			for (NodoComponentes par2 : par.getValue()) {
				result += "\t" + par2 + "\n";
			}
		}
		return result;
	}

	
	public static void main(String[]args) {
		ColeccionNodos gestion = new ColeccionNodos();
		Componente comp01 = new Componente("comp01");
		Componente comp02 = new Componente("comp02");
		Componente comp03 = new Componente("comp03");
		Componente comp04 = new Componente("comp04");
		Componente comp05 = new Componente("comp05");
		
		NodoComponentes nodo01 = new NodoComponentes(comp01, comp03, comp05, comp04, comp01);
		NodoComponentes nodo02 = new NodoComponentes(comp01, comp02, comp02);
		NodoComponentes nodo03 = new NodoComponentes(comp05, comp03, comp01, comp05);
		NodoComponentes nodo04 = new NodoComponentes(comp05);
	
		gestion.add(nodo01, nodo02);
		gestion.add(nodo02, nodo03);
		gestion.add(nodo02, nodo04);
		gestion.add(nodo03, nodo01);
		gestion.add(nodo03, nodo04);
		gestion.add(nodo04, nodo02);
		gestion.add(nodo04, nodo01);
		gestion.add(nodo01, nodo02);
		
		String salidaEsperada = "[comp01, comp02]\n"
									+ "\t[comp01, comp03, comp05]\n"
									+ "\t[comp05]\n"
								+ "[comp01, comp03, comp04, comp05]\n"
									+ "\t[comp01, comp02]\n"
								+ "[comp01, comp03, comp05]\n"
									+ "\t[comp01, comp03, comp04, comp05]\n"
									+ "\t[comp05]\n"
								+ "[comp05]\n"
									+ "\t[comp01, comp02]\n"
									+ "\t[comp01, comp03, comp04, comp05]\n";

		System.out.println(salidaEsperada.equals(gestion.toString()) ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}
}