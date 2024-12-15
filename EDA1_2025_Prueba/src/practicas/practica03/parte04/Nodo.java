package practicas.practica03.parte04;

import java.util.ArrayList;
import java.util.Objects;

public class Nodo <T extends Comparable<T>> implements Comparable<Nodo<T>>{
	
	private ArrayList<T> componentes;

	@SafeVarargs //Preguntar pq se tiene q poner esto
	public Nodo(T... componentes) {
		this.componentes = new ArrayList<>();
		//1 for()
		for (T t : componentes) {
			if(this.componentes.contains(t)) continue;
			this.componentes.add(t);
		}
		this.componentes.sort(null);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(componentes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object otro) { 
		if (this == otro) return true;
		if (!(otro instanceof Nodo))return false;
		return this.compareTo((Nodo<T>) otro) == 0;
	}
	
	@Override
	public int compareTo(Nodo<T> otro) {
		int l1 = this.componentes.size();
		int l2 = otro.componentes.size();
		for (int i = 0; i< l1  && i< l2; i++) {
			int comp = this.componentes.get(i).compareTo(otro.componentes.get(i));
			if(comp != 0) return comp;
		}
		return l1 - l2;
	} 

	@Override
	public String toString() {
		return this.componentes.toString();
	}
}