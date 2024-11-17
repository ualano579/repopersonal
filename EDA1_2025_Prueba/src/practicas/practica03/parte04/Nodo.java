package practicas.practica03.parte04;

import java.util.ArrayList;
import java.util.Objects;

public class Nodo implements Comparable<Nodo>{
	
	private ArrayList<¿?> componentes;

	
	public Nodo(¿?... componentes) {
		this.componentes = new ArrayList<>();
		//1 for()
		//...
		this.componentes.sort(null);
	}

	@Override
	public int hashCode() {
		return //...
	}

	
	@Override
	public boolean equals(Object otro) { 
		//...
	}

	@Override
	public int compareTo(Nodo otro) {
		//...
	} 

	@Override
	public String toString() {
		return this.componentes.toString();
	}
}