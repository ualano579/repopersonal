package auxiliar;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Par;


public class Prueba2 {
	TreeMap<String, TreeMap<String, ArrayList<String>>> datos;
	
	public Prueba2() {
		this.datos = new TreeMap<>();
	}
	
	public TreeMap<String, TreeMap<String, TreeMap<String, Integer>>> converter() {
		TreeMap<String, TreeMap<String, TreeMap<String, Integer>>> result = new TreeMap<>();
		for (Entry<String, TreeMap<String, ArrayList<String>>> par : this.datos.entrySet()) {
			TreeMap<String, TreeMap<String, Integer>> aux = result.get(par.getKey());
			if(aux == null) result.put(par.getKey(), aux = new TreeMap<>());
			for (Entry<String, ArrayList<String>> par2 : par.getValue().entrySet()) {
				TreeMap<String, Integer> aux2 = aux.get(par2.getKey());
				if(aux2 == null) aux.put(par2.getKey(), aux2 = new TreeMap<>());
				for (String p : aux2.keySet()) {
					Integer i = aux2.get(p);
					aux2.put(p, i == null ? 1 : i+1);
				}
			}
		}
		return result;
	}
	
	public class Prueba3 {
		TreeMap<String, TreeMap<String, TreeMap<String, Integer>>> datosDestino;
		AVLTree<Par<String, AVLTree<Par<String, AVLTree<Par<String, Integer>>>>>> datosOrigen;
		
		public Prueba3() {
			this.datosDestino = new TreeMap<>();
			this.datosOrigen = new AVLTree<>();
		}
		
		public void transform() {
			TreeMap<String, TreeMap<String, Integer>> aux;
			for (Par<String, AVLTree<Par<String, AVLTree<Par<String, Integer>>>>> par : datosOrigen) {
				datosDestino.put(par.getKey(), aux = new TreeMap<>());
				TreeMap<String, Integer> aux2;
				for (Par<String, AVLTree<Par<String, Integer>>> par2 : par.getValue()) {
					aux.put(par2.getKey(), aux2 = new TreeMap<>());
					for (Par<String, Integer> par3 : par2.getValue()) {
						aux2.put(par2.getKey(), par3.getValue());
					}
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
