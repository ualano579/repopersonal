package practicas.practica03.parte02;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import practicas.auxiliar.Format;
import practicas.practica02.parte01.Asignatura;

public class GestionEstudiantes implements Iterable<Entry<Estudiante, TreeMap<Estudiante, Integer>>>{
	private final String centroId;
	private final TreeMap<String, Asignatura> asignaturasOfertadas = new TreeMap<>();//La diferencia entre hacerlo aqui o dentro del constructor es para q sea más eficiente a nivel de memoria cndo se busca
	private final TreeMap<Estudiante, Estudiante> estudiantesMatriculados = new TreeMap<>();

	private final TreeMap<Estudiante, TreeMap<Asignatura, ArrayList<Double>>> datos = new TreeMap<>();

	public GestionEstudiantes(String centroId) {
		this.centroId = centroId;
	}

	public boolean load(String fileName) {
		Scanner scan;
		String line;
		String[] items;
		this.clear();
		try {
			scan = new Scanner(new File(fileName));
		} catch (IOException e) {
			return false;
		}
		while (scan.hasNextLine()) {
			line = scan.nextLine().trim();
			if (line.isEmpty())
				continue;
			if (line.startsWith("#"))
				continue;
			if (line.startsWith("@")) {
				items = line.split("[ ]+");
				switch (items[0]) {
				case "@Asignaturas":
					for (int i = 0; i < Integer.parseInt(items[1]); i++) {
						String[] itemsAux = scan.nextLine().trim().split("[ ]+");
						Asignatura asignatura = new Asignatura(itemsAux[0], Integer.parseInt(itemsAux[1]));
						asignatura.addValoraciones(Integer.parseInt(itemsAux[2]));
						asignatura.addDocentes(Arrays.copyOfRange(itemsAux, 3, itemsAux.length));
						this.asignaturasOfertadas.put(itemsAux[0], asignatura);
					}
					break;
				case "@Estudiantes":
					for (int i = 0; i < Integer.parseInt(items[1]); i++) {
						String[] itemsAux = scan.nextLine().trim().split("[-]+");
						Estudiante estudiante = new Estudiante(itemsAux[1], itemsAux[0]);
						estudiante.setTelefono(itemsAux[2]);
						estudiante.setDireccion(itemsAux[3]);
						this.estudiantesMatriculados.put(new Estudiante(itemsAux[1], itemsAux[0]), estudiante);
					}
					break;
				case "@Notas":
	                for (int i=0; i < Integer.parseInt(items[1]); i++){
	                	String[] itemsAux = scan.nextLine().trim().split("[ \t]+");
	                	//Los estudiantes y las asignaturas deben existir (contenidos en this.estudiantesMatriculados y this.asignaturasOfertadas
	                	Estudiante estudiante = this.estudiantesMatriculados.get(new Estudiante(itemsAux[1], itemsAux[0]));
	                	if(estudiante == null) continue;
	                	Asignatura asignatura = this.asignaturasOfertadas.get(itemsAux[2]);
	                	if(asignatura == null) continue;
	                	Double[] notas = new Double[itemsAux.length-3];
	                    for(int j = 3; j < itemsAux.length; j++) {
	                    	notas[j-3] = Double.parseDouble(itemsAux[j]);
	                    }
	                    this.add(estudiante, asignatura, notas);
	                }
					break;
				default:
					return false;
				}
			}
		}
		scan.close();
		return true;
	}

	public void add(Estudiante estudiante, Asignatura asignatura, Double... notas) {
		// 0 containsKey()
		// 2 get()
		// 2 put()
		// 0 for()
		TreeMap<Asignatura, ArrayList<Double>> aux1 = this.datos.get(estudiante);
		if(aux1 == null) this.datos.put(estudiante, aux1 = new TreeMap<>());
		ArrayList<Double> aux2 = aux1.get(asignatura);
		if(aux2 == null) aux1.put(asignatura, aux2 = new ArrayList<>());
		aux2.addAll(List.of(notas));
	}
	
	public void clear() {
		this.datos.clear();
		this.asignaturasOfertadas.clear();
		this.estudiantesMatriculados.clear();
	}
	
	public int size() {
		return this.datos.size();//nº estudiantes
	}
	
	public int getNumEstudiantesMatriculados() {
		return this.estudiantesMatriculados.size();
	}
	
	public int getNumAsignaturasOfertadas() {
		return this.asignaturasOfertadas.size();
	}
	
	public TreeSet<String> getAsignaturasMatriculadas(String apellido, String nombre) {
		apellido = apellido.trim().toLowerCase();
		nombre = nombre.trim().toLowerCase();
		// 2 get()
		//1 for()
		TreeSet<String> result = new TreeSet<>();
		Estudiante e = this.estudiantesMatriculados.get(new Estudiante(apellido, nombre));
		if(e == null) return null;
		TreeMap<Asignatura, ArrayList<Double>> aux = this.datos.get(e);
		if(aux == null) return result;
		for (Asignatura a : aux.keySet()) {
			result.add(a.getAsignaturaId());
		}
		return result;
	}
	
	public String getNotaMedia(String apellido, String nombre) {
		apellido = apellido.trim().toLowerCase();
		nombre = nombre.trim().toLowerCase();
		// Si el estudiante no existe se devuelve -1.00; 
		// Si existe, pero no está matriculado de ninguna asignatura, se devuelve 0.00
		Estudiante e = this.estudiantesMatriculados.get(new Estudiante(apellido, nombre));
		if(e == null) return "-1.00";
		TreeMap<Asignatura, ArrayList<Double>> aux1 = this.datos.get(e);
		if(aux1 == null) return "0.00";
		ArrayList<Double> aux = new ArrayList<>();
		for (ArrayList<Double> notas : aux1.values()) {
			aux.addAll(notas);
		}
		return Format.formatDouble(MyMath.calculaMedia(aux));
	}
	
	public TreeSet<String> getAlumnosMatriculados(String asignaturaId) {
		asignaturaId = asignaturaId.toLowerCase().trim();
		//Si la asignatura no existe, se devuelve null
		TreeSet<String> result = new TreeSet<>();
		Asignatura a = this.asignaturasOfertadas.get(asignaturaId);
		if(a == null) return null;
		//1 for()
		for (Entry<Estudiante, TreeMap<Asignatura, ArrayList<Double>>> par : this.datos.entrySet()) {
			if(par.getValue().containsKey(a)){
				result.add(par.getKey().getNombreApellidos());
			}
		}
		return result;
	}
	
	public String getNotaMedia(String asignaturaId) {
		asignaturaId = asignaturaId.toLowerCase().trim();
		Asignatura a = this.asignaturasOfertadas.get(asignaturaId);
		if(a == null) return "-1.00";
		ArrayList<Double> aux = new ArrayList<>();
		//1 for()
		for (TreeMap<Asignatura, ArrayList<Double>> t : this.datos.values()) {
			ArrayList<Double> notas = t.get(a);
			if(notas == null) continue;
			aux.addAll(notas);
		}
		return Format.formatDouble(MyMath.calculaMedia(aux));
	}
	 
	public String getNotaMedia(String apellido, String nombre, String asignaturaId) {
		apellido = apellido.trim().toLowerCase();
		nombre = nombre.trim().toLowerCase();
		asignaturaId = asignaturaId.toLowerCase().trim();
		Asignatura a = this.asignaturasOfertadas.get(asignaturaId);
		if(a == null) return "-1.00";
		Estudiante e = this.estudiantesMatriculados.get(new Estudiante(apellido, nombre));
		if(e == null) return "-1.00";
		TreeMap<Asignatura, ArrayList<Double>> aux = this.datos.get(e);
		if(aux == null) return "0.00";
		ArrayList<Double> notas = aux.get(a);
		if(notas == null) return "0.00";
		return Format.formatDouble(MyMath.calculaMedia(notas));
	}
	
	public String getNotaMediaDocente(String docenteId) {
		ArrayList<Double> notasAux = new ArrayList<>();
		//2 for()
		for (TreeMap<Asignatura, ArrayList<Double>> t : this.datos.values()) {
			for (Entry<Asignatura, ArrayList<Double>> t2 : t.entrySet()) {
				if(t2.getKey().esDocente(docenteId)) {
					notasAux.addAll(t2.getValue());
				}
			}
		}
		return notasAux.isEmpty() ? "-1.00" : Format.formatDouble(MyMath.calculaMedia(notasAux));
	}
	
	public String toStringEstudiante(String apellido, String nombre) {
		apellido = apellido.trim().toLowerCase();
		nombre = nombre.trim().toLowerCase();
		//2 líneas
		Estudiante e = this.estudiantesMatriculados.get(new Estudiante(apellido, nombre));
		return e == null ? "<estudiante desconocido>" : e.toString();
	}
	
	public String toStringAsignatura(String asignaturaId) {
		asignaturaId = asignaturaId.trim().toLowerCase();
		//2 líneas
		Asignatura a  = this.asignaturasOfertadas.get(asignaturaId);
		return a == null ? "<asignatura desconocida>" : a.toString();
	}
	
	public String toStringDocentes(String asignaturaId, Comparator<String> comp) {
		asignaturaId = asignaturaId.trim().toLowerCase();
		//2 líneas
		Asignatura a  = this.asignaturasOfertadas.get(asignaturaId);
		return a == null ? "<asignatura desconocida>" : a.getDocentes(comp).toString();
	}
	
	@Override
	public String toString() {
		return this.centroId;
	}
	
	public Iterator<Entry<Estudiante, TreeMap<Estudiante, Integer>>> iterator() {
		TreeMap<Estudiante, TreeMap<Estudiante, Integer>> aux = new TreeMap<>();
		for (Entry<Estudiante, TreeMap<Asignatura, ArrayList<Double>>> par1 : this.datos.entrySet()) {
			TreeMap<Estudiante, Integer> t = null;
			for (Entry<Estudiante, TreeMap<Asignatura, ArrayList<Double>>> par2 : this.datos.entrySet()) {
				if(par1.getKey().compareTo(par2.getKey())>= 0) continue;
				Set<Asignatura> a = new TreeSet<>(par1.getValue().keySet());
				a.retainAll(par2.getValue().keySet()); //Interseccion
				if(a.size() == 0) continue;
				if(t == null) aux.put(par1.getKey(), t = new TreeMap<>());
				t.put(par2.getKey(), a.size());
			}
		}
		return aux.entrySet().iterator();
	}
}