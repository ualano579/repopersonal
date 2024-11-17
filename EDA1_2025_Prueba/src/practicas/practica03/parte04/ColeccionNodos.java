package practicas.practica03.parte04;

import java.util.*;
import java.util.Map.Entry;

import practicas.practica03.parte03.Componente;

public class ColeccionNodos    {
	
	protected final HashMap<Nodo, HashSet<Nodo>> data;

	public ColeccionNodos(){
		this.data = new HashMap<>();
	}
	
	public void add(Nodo nodoOrigen, Nodo nodoDestino) {
		//...
	}

	public int size() {
		return this.data.size();
	}

	public void clear(){
		this.data.clear();
	}
	    
	private TreeMap<Nodo, TreeSet<Nodo>> toOrderedCollection() {
		TreeMap<Nodo, TreeSet<Nodo>> result = new TreeMap<>();
		//...
		return result;
	}

	@Override
	public String toString() {
		TreeMap<Nodo, TreeSet<Nodo>> aux = this.toOrderedCollection();
		String result = "";
		//...
		return result;
	}

	private static void checkComponente() {
		ColeccionNodos<Componente> gestion = new ColeccionNodos<>();
		Componente comp01 = new Componente("comp01");
		Componente comp02 = new Componente("comp02");
		Componente comp03 = new Componente("comp03");
		Componente comp04 = new Componente("comp04");
		Componente comp05 = new Componente("comp05");
		
		Nodo<Componente> nodo01 = new Nodo<>(comp01, comp03, comp05, comp04, comp01);
		Nodo<Componente> nodo02 = new Nodo<>(comp01, comp02, comp02);
		Nodo<Componente> nodo03 = new Nodo<>(comp05, comp03, comp01, comp05);
		Nodo<Componente> nodo04 = new Nodo<>(comp05);
	
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
	
	private static void checkString() {
		ColeccionNodos<String> gestion = new ColeccionNodos<>();
		
		Nodo<String> nodo01 = new Nodo<>("comp01", "comp03", "comp05", "comp04", "comp01");
		Nodo<String> nodo02 = new Nodo<>("comp01", "comp02", "comp02");
		Nodo<String> nodo03 = new Nodo<>("comp05", "comp03", "comp01", "comp05");
		Nodo<String> nodo04 = new Nodo<>("comp05");
	
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

	private static void checkInteger() {
		ColeccionNodos<Integer> gestion = new ColeccionNodos<>();
		
		Nodo<Integer> nodo01 = new Nodo<>(1, 3, 5, 4, 1);
		Nodo<Integer> nodo02 = new Nodo<>(1, 2, 2);
		Nodo<Integer> nodo03 = new Nodo<>(5, 3, 1, 5);
		Nodo<Integer> nodo04 = new Nodo<>(5);
	
		gestion.add(nodo01, nodo02);
		gestion.add(nodo02, nodo03);
		gestion.add(nodo02, nodo04);
		gestion.add(nodo03, nodo01);
		gestion.add(nodo03, nodo04);
		gestion.add(nodo04, nodo02);
		gestion.add(nodo04, nodo01);
		gestion.add(nodo01, nodo02);
		
		String salidaEsperada = "[1, 2]\n"
									+ "\t[1, 3, 5]\n"
									+ "\t[5]\n"
								+ "[1, 3, 4, 5]\n"
									+ "\t[1, 2]\n"
								+ "[1, 3, 5]\n"
									+ "\t[1, 3, 4, 5]\n"
									+ "\t[5]\n"
								+ "[5]\n"
									+ "\t[1, 2]\n"
									+ "\t[1, 3, 4, 5]\n";

		System.out.println(salidaEsperada.equals(gestion.toString()) ? "¡¡¡OK!!!" : "¡¡¡Error!!!");
	}
	public static void main(String[]args) {
		checkComponente();
		checkString();
		checkInteger();
	}
}