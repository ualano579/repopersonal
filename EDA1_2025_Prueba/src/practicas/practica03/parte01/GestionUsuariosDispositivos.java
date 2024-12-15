package practicas.practica03.parte01;

import java.util.Arrays;
import java.util.Map.Entry;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class GestionUsuariosDispositivos {
	private final TreeMap<String, TreeMap<String, TreeMap<String, Integer>>> datos = new TreeMap<>();


	public boolean load(String fileName) {
		Scanner scan;
		String line;
		String[] items;
		try {
			scan = new Scanner(new File(fileName));
		} catch (IOException e) {
			return false;
		}
		this.datos.clear();
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			if (line.isBlank()) continue;
			if (line.startsWith("#")) continue;

			items = line.toLowerCase().split("[\t ,.¿?:;()]+");
			//Llamamos al método this.add() 
			//1 única línea
			this.add(items[0], items[1], Arrays.copyOfRange(items, 2, items.length));
		}
		scan.close();
		return true;
	}
    
	public void add(String usuarioId, String dispositivoId, String...palabras) {
		//0 (CERO) llamadas al método containsKey()
		//3  get() y 3 put()
		//1 for()
		//Recuerda el tema de las stopWords() y la eliminación de acentos (cleanWord() - clase Auxiliar)
		TreeMap<String, TreeMap<String, Integer>> aux1 = this.datos.get(usuarioId);
		if(aux1 == null) this.datos.put(usuarioId, aux1 = new TreeMap<>());
		TreeMap<String, Integer> aux2 = aux1.get(dispositivoId);
		if (aux2 == null) aux1.put(dispositivoId, aux2 = new TreeMap<>());
		for (String p : palabras) {
			if(Auxiliar.isStopWord(p)) continue;
			p = Auxiliar.cleanWord(p);
			Integer i = aux2.get(p);
			aux2.put(p, i == null ? 1 : i + 1);
			
		}
		
	}

	public int size() {
		return this.datos.size();//nº claves
	}
	
	public void clear() {
		this.datos.clear();
	}

	public int getNumDispositivos(String usuarioId) {
		usuarioId = usuarioId.trim().toLowerCase();
		//1 único get()
		//2 líneas
		TreeMap<String, TreeMap<String, Integer>> aux1 = this.datos.get(usuarioId);
		return aux1 == null ? 0 : aux1.size();
	}

	public int getNumUsuarios(String dispositivoId) {
		dispositivoId = dispositivoId.trim().toLowerCase();
		//1 uso del método containsKey() (¿por qué aquí si utilizamos este método?)
		//1 for()
		int n = 0;
		for (TreeMap<String, TreeMap<String, Integer>> t : this.datos.values()) {
			if(t.containsKey(dispositivoId)) n++;
		}
		return n;
	}

	public int getNumPalabras(String usuarioId) {
		usuarioId = usuarioId.trim().toLowerCase();
		//1 get()
		//2 for()
		TreeMap<String, TreeMap<String, Integer>> aux = this.datos.get(usuarioId);
		if(aux == null) return 0;
		int n = 0;
		for (TreeMap<String, Integer> t : aux.values()) {
			for (Integer i : t.values()) {
				n+=i;
			}
		}
		return n;
	}
	
	public int getNumPalabras(String usuarioId, String dispositivoId) {
		usuarioId = usuarioId.trim().toLowerCase();
		dispositivoId = dispositivoId.trim().toLowerCase();
		//2 get()
		//1 for()
		TreeMap<String, TreeMap<String, Integer>> aux = this.datos.get(usuarioId);
		if(aux == null) return 0;
		TreeMap<String, Integer> aux1 = aux.get(dispositivoId);
		if(aux1 == null) return 0;
		int n = 0;
		for (Integer i : aux1.values()) {
			n+=i;
		}
		return n;
	}
	
	public int getFrecuencia(String usuarioId, String palabra) {
		usuarioId = usuarioId.trim().toLowerCase();
		palabra = Auxiliar.cleanWord(palabra);
		//2 get()
		//1 for()
		TreeMap<String, TreeMap<String, Integer>> aux = this.datos.get(usuarioId);
		if(aux == null) return 0;
		int n = 0;
		for (TreeMap<String, Integer> t : aux.values()) {
			Integer i = t.get(palabra);
			if (i == null) continue;
			n += i;
		}
		return n;
	}
	
	public int getFrecuencia(String usuarioId, String dispositivoId, String palabra) {
		usuarioId = usuarioId.trim().toLowerCase();
		dispositivoId = dispositivoId.trim().toLowerCase();
		palabra = Auxiliar.cleanWord(palabra);
		//3 get()
		TreeMap<String, TreeMap<String, Integer>> aux = this.datos.get(usuarioId);
		if(aux == null) return 0;
		TreeMap<String, Integer> aux2 = aux.get(dispositivoId);
		if(aux2 == null) return 0;
		Integer i = aux2.get(palabra);
		return i == null ? 0: i;
	}
	
	public TreeSet<String> getPalabras(String usuarioId) {
		usuarioId = usuarioId.trim().toLowerCase();
		TreeSet<String> result = new TreeSet<>();
		//1 get()
		//1 único for()
		TreeMap<String, TreeMap<String, Integer>> aux = this.datos.get(usuarioId);
		if(aux == null) return result;
		for (TreeMap<String, Integer> t : aux.values()) {
			result.addAll(t.keySet());
		}
		return result;
	}
	
	public TreeSet<String> getPalabras(String usuarioId, String dispositivoId) {
		usuarioId = usuarioId.trim().toLowerCase();
		dispositivoId = dispositivoId.trim().toLowerCase();
		TreeSet<String> result = new TreeSet<>();
		//2 get()
		TreeMap<String, TreeMap<String, Integer>> aux = this.datos.get(usuarioId);
		if(aux == null) return result;
		TreeMap<String, Integer> aux2 = aux.get(dispositivoId);
		if(aux2 == null) return result;
		result.addAll(aux2.keySet());
		return result;
	}

	public String getUsuarios(String palabra) {
		palabra = Auxiliar.cleanWord(palabra.toLowerCase().trim());
		TreeMap<String, Integer> result = new TreeMap<>();
		//2 for() entrySet() + values()
		//2 get()
		for (Entry<String, TreeMap<String, TreeMap<String, Integer>>> entry : this.datos.entrySet()) {
			int n = 0;
			for (TreeMap<String, Integer> t : entry.getValue().values()) {
				//Se podria poner el 2 get aqui para preguntar q tienes para este usuario pero como el mismo usuario solo sale una vez no es necesario
				Integer i = t.get(palabra);
				if (i != null) n+=i;
			}
			if(n != 0) result.put(entry.getKey(), n);
		}
		return result.toString();
	}
	
	public TreeSet<String> getPalabrasRepetidas(String usuarioId) {
		usuarioId = usuarioId.trim().toLowerCase();
		TreeSet<String> result = new TreeSet<>();
		//Una única pista: lo mismo interesa crear una estructura TreeMap<K,V> auxiliar
		//this.datos --> auxiliar --> result
		TreeMap<String, TreeMap<String, Integer>> aux = this.datos.get(usuarioId);
		if(aux == null) return result;
		TreeMap<String, Integer> auxiliar = new TreeMap<>();
		for (TreeMap<String, Integer> t : aux.values()) {
			for (String s : t.keySet()) {
				Integer i = auxiliar.get(s);
				auxiliar.put(s, i == null ? 1 : i+1);
			}
		}
		for (Entry<String, Integer> par : auxiliar.entrySet()) {
			if(par.getValue() > 1) result.add(par.getKey());
		}
		return result;
	}
	
	@Override 
	public String toString() {
		String aux = "";
		//3 for() entrySet() + entrySet() + entrySet()
		//Atención al uso del método lastEntry()
		/* El formato de salida en Consola es:
		 * 		usuaria:
		 * 			disp01: amor <2> - hola <3>
		 * 		usuario:
		 * 			disp01: adios <2> - amor <1> - hola <2> - leña <1>
		 * 			disp02: amar <1> - amor <3> - hola <1>
		 * 			disp03: adios<1> - amor <1> - hola <1> 
		 * 
		 */
		for (Entry<String, TreeMap<String, TreeMap<String, Integer>>> par1 : this.datos.entrySet()) {
			aux += par1.getKey()+":\n";
			for (Entry<String, TreeMap<String, Integer>> par2 : par1.getValue().entrySet()) {
				aux += "\t"+ par2.getKey() + ":";
				for (Entry<String, Integer> par3 : par2.getValue().entrySet()) {
					aux += " "+par3.getKey() +" <"+ par3.getValue()+">";
					if(!par3.equals(par2.getValue().lastEntry())) aux += " -";
				}
				aux += "\n";
			}
			
		}
		return aux;
	}
}