package practicas.practica02.parte03;

import java.util.ArrayList;

import practicas.auxiliar.BSTree;
import practicas.auxiliar.Par;

public class MyTreeMap<K extends Comparable<K>,V> {
	
	private final BSTree<Par<K,V>> treePar;

	public MyTreeMap(){
		this.treePar = new BSTree<>();
	}

	public V put(K key, V value) {
		Par<K,V> parCurr = this.treePar.find(new Par<>(key, null));
		V vOld = null;
		//Si la clave no existe, añadimos el par (key, value)
		//En caso contrario, modificamos el valor asociado a la clave (haciendo uso del método setValue() de la clase Par<K,V>
		//if ... else ...
		if(parCurr == null) {
			treePar.add(new Par<>(key,value));
		}else {
			vOld = parCurr.setValue(value);
		}
		return vOld;
	}
	
	public V get(K key) {
		//Si no existe la clave, devuelve null
		//En caso contrario, devuelve el valor asociado con la clave
		//2 líneas
		Par<K,V> par = this.treePar.find(new Par<>(key, null));
		return (par == null) ? null : par.getValue();
	}
	
	public boolean containsKey(K key) {
		return this.get(key) != null;
	}
	
	public void clear() {
		this.treePar.clear();
	}
	
	public boolean isEmpty() {
		return this.treePar.isEmpty();
	}

	public int size() {
		return this.treePar.size();
	}

	public ArrayList<K> keySet(){
		ArrayList<K> resultado  = new ArrayList<>();
		//1 for()
		for (Par<K,V> par : treePar) {
			resultado.add(par.getKey());
		}
		return resultado;
	}
	
	public ArrayList<V> values(){
		ArrayList<V> resultado  = new ArrayList<>();
		//1 for()
		for (Par<K,V> par : treePar) {
			resultado.add(par.getValue());
		}
		return resultado;
	}
	
	public ArrayList<Par<K,V>> pairSet(){
		ArrayList<Par<K,V>> resultado  = new ArrayList<>();
		//1 for()
		for (Par<K,V> par : treePar) {
			resultado.add(par);
		}
		return resultado;
	}

	@Override
	public String toString() {
		return this.pairSet().toString();
	}
}