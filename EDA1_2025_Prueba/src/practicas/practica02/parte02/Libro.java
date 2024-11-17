package practicas.practica02.parte02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Par;

public class Libro implements Comparable<Libro>, Iterable<Par<String, Integer>>{
	private final String libroId; //El identificador de un libro es su titulo
	private final AVLTree<Par<String, Integer>> palabrasFrecuencias; //contenido del libro: conjunto de pares <palabra, frecuencia>
	
	public Libro(String libroId) {
		this.libroId = libroId.trim().toLowerCase();
		this.palabrasFrecuencias = new AVLTree<>();
	}
	
	public String getLibroId() {
		return this.libroId;
	}
		
	public void clear() {
		this.palabrasFrecuencias.clear();
	}
		
	public boolean load(String fileName) {
		Scanner scan;
		try {
			scan = new Scanner(new File(fileName));
		}catch (IOException e) {
			return false;
		}
		while (scan.hasNextLine()){
			String line = scan.nextLine().trim().toLowerCase();
			if (line.isEmpty()) continue;
			//llamamos al método this.add() que está implementado a continuación...haciendo uso del método split()
			add(line.split("[ ]+"));
		}
		scan.close();
		return true;
	}
	
	public void add(String...palabras) {
		//Insertamos en el árbol this.palabraFrecuencias todas y cada una de las palabras existentes en el parámetro palabras
		//Hay que tene en cuenta que si no existe la palabra, se inserta con frecuencia 1
		//Si existía la palabra en el árbol, hay que incrementar en 1 su frecuencia
		//1 for()
		for (String p : palabras) {
			Par<String, Integer> par = this.palabrasFrecuencias.find(new Par<>(p, null));
			if(par == null) {
				par = new Par<>(p, 1);
				this.palabrasFrecuencias.add(par);
			}else {
				par.setValue(par.getValue()+1);
			}
		}
	}
	
	public ArrayList<Par<Integer, ArrayList<String>>> getPalabrasFrecuentes(int freqMin) {
		//Este es el método más importante de este ejercicio, así que todo muy claro
		ArrayList<Par<Integer, ArrayList<String>>> result = new ArrayList<>();
		//1 for() para iterar sobre this.palabrasFrecuencias
		//Si la frecuencia de la palabra es menor que el valor del parámetro freqMin, la ignoramos
		//En caso contrario, insertamos en la estructura result teniendo en cuenta que la clave es la frecuencia y, asociado a cada valor de frecuencia,
		//una lista de palabras
		for (Par<String, Integer> par : palabrasFrecuencias) {
			if(par.getValue() < freqMin) continue;
			int pos = result.indexOf(new Par<>(par.getValue(), null));
			if(pos == -1) {
				result.add(new Par<>(par.getValue(), new ArrayList<>()));
				pos = result.size() -1;
			}
			result.get(pos).getValue().add(par.getKey());
		}
		result.sort(null);
		//¿Qué ocurre con el orden de las claves?
		return result;
	}
		
	@Override
	public String toString() {
		return this.libroId + " -> " + (this.palabrasFrecuencias.isEmpty() ? "[empty]" : this.palabrasFrecuencias); 
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Libro)) return false;
		if (this == o) return true;
		return this.compareTo((Libro)o) == 0;
	}
	
	@Override
	public int compareTo(Libro o) {
		//clave libroId, orden ascendente
		return this.libroId.compareTo(o.libroId);
	}
	
	@Override
	public Iterator<Par<String, Integer>> iterator() {
		//Iterar sobre un libro equivale a iterar sobre el contenido de la colección this.palabrasFrecuencias
		return this.palabrasFrecuencias.iterator();
	}
}