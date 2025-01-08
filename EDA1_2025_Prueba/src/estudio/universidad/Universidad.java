package estudio.universidad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Universidad {
	
	private String nombre;
	private TreeMap<Curso, TreeSet<Alumno>> tm;
	
	public Universidad(File archivo) throws FileNotFoundException {
		cargarDatos(archivo);
	}

	private void cargarDatos(File archivo) throws FileNotFoundException {
		tm=new TreeMap<Curso, TreeSet<Alumno>>();
		Scanner sc = new Scanner(archivo);
		this.nombre=sc.nextLine();
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			String[] tokens = s.split(";");
			String dni=tokens[0];
			String nombre=tokens[1];
			String apellido1=tokens[2];
			String apellido2=tokens[3];
			String carrera=tokens[4];
			int curso=Integer.parseInt(tokens[5]);
			String asignatura=tokens[6];
			Alumno a = new Alumno(dni, nombre, apellido1, apellido2);
			Curso c = new Curso(carrera, curso, asignatura);
			addAlumno(c, a);
		}
		sc.close();
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void clear(){
		//Elimina todos los cursos y alumnos de la universidad
		tm.clear();
	}
	
	public void remove(Curso c){
		//Elimina el curso c de la universidad
		tm.remove(c);
	}
	
	public boolean addCurso(Curso c){
		//Añade un curso a la universidad si no existe ya. Devuelve true si se ha añadido
		TreeSet<Alumno> aux = tm.get(c);
		if(aux == null) {
			tm.put(c, aux = new TreeSet<>());
			return true;
		}
		return false;
	}
	
	public boolean addCurso(String carrera, int curso, String asignatura){
		return addCurso(new Curso(carrera, curso, asignatura));
	}
	
	public boolean addAlumno(Curso c, Alumno a){
		//Añade un alumno al curso c de la universidad. Devuelve true si se ha añadido el alumno
		TreeSet<Alumno> aux = tm.get(c);
		if(aux == null) tm.put(c, aux = new TreeSet<>());
		return aux.add(a);
	}
	
	public boolean addAlumno(Curso c, String dni, String nombre, String apellido1, String apellido2){
		return addAlumno(c, new Alumno(dni, nombre, apellido1, apellido2));
	}
	
	public int numeroAsignaturas(){
		//Devuelve el numero de asignaturas diferentes en la universidad
		return tm.size();
	}
	
	public int numeroAlumnos(){
		//Devuelve el numero de alumnos diferentes en la universidad
		TreeSet<Alumno> aux = new TreeSet<>();
		for (TreeSet<Alumno> alumnos : tm.values()) {
			aux.addAll(alumnos);
		}
		return aux.size();
	}
	
	public int numeroMatriculas(){
		//Devuelve el numero total de matriculas en la universidad
		int cont = 0;
		for (TreeSet<Alumno> t : tm.values()) {
			cont += t.size();
		}
		return cont;
	}
	
	public int numeroAlumnosDeCarrera(String carrera){
		//Devuelve el numero de alumnos de una carrera especifica
		TreeSet<Alumno> aux = new TreeSet<>();
		for (Curso c : tm.keySet()) {
			if(c.getCarrera().equals(carrera)) aux.addAll(tm.get(c));
		}
		return aux.size();
	}
	
	public int numeroMatriculasDeCarrera(String carrera){
		//Devuelve el numero de matriculas de una carrera especifica
		int cont = 0;
		for (Curso c : tm.keySet()) {
			if(c.getCarrera().equals(carrera)) cont += tm.get(c).size();
		}
		return cont;
	}
	
	public int numeroAlumnosDeAsignatura(String asignatura){
		//Devuelve el numero de alumnos matriculados en una asignatura especifica
		TreeSet<Alumno> aux = new TreeSet<>();
		for (Curso c : tm.keySet()) {
			if(c.getAsignatura().equals(asignatura)) aux.addAll(tm.get(c));
		}
		return aux.size();
	}
	
	public int numeroMatriculasDeAsignatura(String asignatura){
		//Devuelve el numero de matriculas en una asignatura especifica
		int cont = 0;
		for (Curso c : tm.keySet()) {
			if(c.getAsignatura().equals(asignatura)) cont += tm.get(c).size();
		}
		return cont;
	}
	
	public String listadoCursoAlumnos(){
		//Devuelve un listado de cursos con sus alumnos matriculados. Presta atención al formato en el test
		String s = "";
		boolean firstCurso = true;
		for (Entry<Curso, TreeSet<Alumno>> par : tm.entrySet()) {
			if (!firstCurso) s += "\n";
	        firstCurso = false;
			s += par.getKey().toString() +"\n";
			boolean firstAlumno = true;
			for (Alumno a : par.getValue()) {
				if (!firstAlumno) s += "\n";
		        firstAlumno = false;
				s += "\t" + a.toString();
			}
		}
		return s;
	}
	
	public String listadoAlumnosCarreraOrdenados(String carrera){
		//Devuelve un listado de alumnos de una carrera ordenados por orden natural
		TreeSet<Alumno> aux = new TreeSet<>();
		for (Entry<Curso, TreeSet<Alumno>> par : tm.entrySet()) {
			if(par.getKey().getCarrera().equals(carrera)) aux.addAll(par.getValue());
		}
		return aux.toString();
	}
	
	public String listadoAlumnosCursoOrdenados(int curso){
		//Devuelve un listado de alumnos de un curso ordenados por orden natural
		TreeSet<Alumno> aux = new TreeSet<>();
		for (Entry<Curso, TreeSet<Alumno>> par : tm.entrySet()) {
			if(Integer.compare(par.getKey().getCurso(), curso) == 0) aux.addAll(par.getValue());
		}
		return aux.toString();
	}
	
	public String listadoAlumnosCarreraCursoAsignatura(String carrera, int curso, String asignatura){
		//Devuelve un listado de alumnos de una carrera, curso y asignatura especificos ordenados por orden natural
		TreeSet<Alumno> aux = new TreeSet<>();
		for (Entry<Curso, TreeSet<Alumno>> par : tm.entrySet()) {
			if(par.getKey().getCarrera().equals(carrera) && Integer.compare(par.getKey().getCurso(), curso) == 0 && par.getKey().getAsignatura().equals(asignatura)) aux.addAll(par.getValue());
		}
		return aux.toString();
	}
	
	public String listadoCursosConMasDeKAlumnosMatriculados(int k){
		//Devuelve un listado de cursos con mas de k alumnos matriculados ordenados por orden natural
		TreeSet<Curso> aux = new TreeSet<>();
		for (Entry<Curso, TreeSet<Alumno>> par : tm.entrySet()) {
			if(par.getValue().size() > k) aux.add(par.getKey());
		}
		return aux.toString();
	}
	
	public String listadoAlumnosAsignaturasMatriculado(){
		//Devuelve un listado de alumnos con las asignaturas en las que estan matriculados. Presta atención al formato en el test
		TreeMap<String, TreeSet<String>> aux = new TreeMap<>();
		String s = "";
		for (Entry<Curso, TreeSet<Alumno>> par : tm.entrySet()) {
			for (Alumno a : par.getValue()) {
				String s1 = a.toString() + ":";
				String s2 = par.getKey().toString();
				TreeSet<String> t1 = aux.get(s1);
				if(t1 == null) aux.put(s1, t1 = new TreeSet<>());
				t1.add(s2);
			}
		}
		for (Entry<String, TreeSet<String>> par : aux.entrySet()) {
			s += par.getKey() + "\n";
			for (String str : par.getValue()) {
				s += "\t" + str + "\n";
			}
		}
		return s;
	}
	
	public String alumnosConMasMatriculas(){
		//Devuelve un listado de alumnos con mas matriculas matriculados. Presta atención al formato en el test
		TreeMap<Alumno, Integer> temp = new TreeMap<>();
		TreeSet<Alumno> aux = new TreeSet<>();
		int max = 0;
		for (TreeSet<Alumno> t : tm.values()) {
			for (Alumno a : t) {
				Integer i = temp.get(a);
				temp.put(a, i == null ? 1 : i + 1);
			}
		}
		for (Entry<Alumno, Integer> par : temp.entrySet()) {
			if(par.getValue() > max) {
				max = par.getValue();
				aux.clear();
			}
			if(par.getValue() == max) aux.add(par.getKey());
		}
		return max + " asignaturas: " + aux.toString();
	}
}
