package practicas.practica03.parte03;

import java.util.ArrayList;
import java.util.Objects;

public class NodoComponentes implements Comparable<NodoComponentes> {
	
	private ArrayList<Componente> componentes;

	public NodoComponentes(Componente... componentes) {
		this.componentes = new ArrayList<>();
		//No permitimos que existan componentes repetidos
		for (Componente c : componentes) {
			if(this.componentes.contains(c)) continue;
			this.componentes.add(c);
		}
		this.componentes.sort(null);
	}

	@Override
	public int hashCode() {
		//1 línea
		return this.componentes.hashCode();
	}

	@Override
	public boolean equals(Object otro) { 
		if (this == otro) return true;
		if (!(otro instanceof NodoComponentes))return false;
		return this.compareTo((NodoComponentes) otro) == 0;
	}

	@Override
	public int compareTo(NodoComponentes otro) {
		//¿Cómo comparamos dos ArrayList<>?
		//Esto ya está contado en el guion, así que revísalo si no te acuerdas
		//1 único for()
		//Mira la implementación del método compareTo() de la clase String
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