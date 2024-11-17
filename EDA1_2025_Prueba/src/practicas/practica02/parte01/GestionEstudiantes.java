package practicas.practica02.parte01;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Format;


public class GestionEstudiantes {
    private final String centroId;
    private final AVLTree<Asignatura> asignaturasOfertadas;
    private final AVLTree<Estudiante> estudiantesMatriculados;

    public GestionEstudiantes(String centroId) {
        this.centroId = centroId.trim();
        this.asignaturasOfertadas = new AVLTree<>();
        this.estudiantesMatriculados = new AVLTree<>();
    }

    public void clear() {
        this.asignaturasOfertadas.clear();
        this.estudiantesMatriculados.clear();
    }

    public void addAsignaturas(Asignatura... asignaturas) {
    	//Recuerda que this.asignaturasOfertadas es de tipo AVLTree<> y que NO permite elementos repetidos; no es necesario preguntar si ya existe la asignatura antes de insertar
    	//1 for()
    	for (Asignatura asignatura : asignaturas) {
			this.asignaturasOfertadas.add(asignatura);
		}
    }

    public void addEstudiantes(Estudiante... estudiantes){
    	//1 for()
    	for (Estudiante estudiante : estudiantes) {
			this.estudiantesMatriculados.add(estudiante);
		}
    }

    public boolean addMatricula(String estudianteId, String...asignaturasId) {
    	//Si el estudiante con clave estudianteId no está matriculado, se devuelve false
    	//Hay que comprobar que cada asignatura con clave asignaturaId exista previamente como asignatura ofertada; si no existe, se ignora
    	//1 for()
    	Estudiante e = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	if(e == null) return false;
    	for (String asig : asignaturasId) {
    		Asignatura a = this.asignaturasOfertadas.find(new Asignatura(asig));
    		if(a == null) continue;
    		e.addAsignaturas(a);
		}
        return true;
    }

    public boolean addNotas(String estudianteId, String asignaturaId, Double... notas){
    	//Si el estudiante con clave estudianteId no existe, se devuelve false
    	//3 líneas
    	Estudiante e = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	if(e == null) return false;
    	return e.addNotas(asignaturaId, notas);
    }

    public String getNotaMedia(String estudianteId) {
    	//Si el estudiante con clave estudianteId no existe, se devuelve la cadena "-1.00"
    	//Recuerda el uso de la clase Format
    	//3 líneas
    	Estudiante e = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	if(e == null) return "-1.00";
    	return e.getNotaMedia();
    }

    public String getNotaMedia(String estudianteId, String asignaturaId) {
        //Igual que en los casos anteriores, si el estudiante no existe, el método devuelve "-1.00"
        //3 líneas
    	Estudiante e = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
    	if(e == null) return "-1.00"; //Format.formatDouble(-1);
    	return e.getNotaMedia(asignaturaId);
    }

    public String getNotaMediaAsignatura(String asignaturaId) {
    	double suma = .0;
    	int cont = 0;
    	for (Estudiante e : estudiantesMatriculados) {
    		String nm = e.getNotaMedia(asignaturaId);
			if(nm.equals("-1.00")) continue;
			suma += Double.parseDouble(nm);
			cont++;
		}
    	return cont == 0 ? "-1.00" : Format.formatDouble(suma/cont);
    }

    public String getEquipoDocenteEstudiante(String estudianteId) {
        ArrayList<String> result = new ArrayList<>();
        Estudiante estudianteCurr = this.estudiantesMatriculados.find(new Estudiante(estudianteId));
        if (estudianteCurr == null) return "[]";
        //2 for()
        for (AsignaturaNotas an : estudianteCurr) {
			for (String d : an.getDocentes()) {
				if(result.contains(d)) continue;
				result.add(d);
			}
		}
        return result.toString();
    }

    public boolean load(String fileName) {
        Scanner scan;
        String line;
        String[] items;
        int iter;
        this.asignaturasOfertadas.clear();
        this.estudiantesMatriculados.clear();
        try {
            scan = new Scanner(new File(fileName));
        } catch (IOException e) {
            return false; //Si no encuentra el archivo, devuelve false
        }
        while (scan.hasNextLine()) { //Entiende bien cada instrucción...y antes mira el formato del archivo de entrada (datos.txt)
            line = scan.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("#")) continue;
            if (line.startsWith("@")) {
                items = line.split("[ ]+");
                switch (items[0]){
                    case "@Asignaturas":
                    	//1 for()
                    	iter = Integer.valueOf(items[1]);
                    	for(int i = 0; i < iter; i++) {
                    		items = scan.nextLine().trim().split("[ ]+");
                    		Asignatura a = new Asignatura(items[0], Integer.valueOf(items[1]));
                    		a.addDocentes(Arrays.copyOfRange(items, 2, items.length));
                    		this.asignaturasOfertadas.add(a);
                    	}
                        break;
                    case "@Estudiantes":
                    	//1 for()
                    	iter = Integer.valueOf(items[1]);
                    	for(int i = 0; i < iter; i++) {
                    		this.estudiantesMatriculados.add(new Estudiante(scan.nextLine().trim()));
                    	}
                        break;
                    case "@Matrículas":
                    	//2 for()
                    	iter = Integer.valueOf(items[1]);
                    	for(int i = 0; i < iter; i++) {
                    		items = scan.nextLine().trim().split("[ ]+");
                    		addMatricula(items[0], Arrays.copyOfRange(items, 1, items.length));
                    	}
                        break;
                    case "@Notas":
                    	//2 for()
                    	iter = Integer.valueOf(items[1]);
                    	for(int i = 0; i < iter; i++) {
                    		items = scan.nextLine().trim().split("[ \t]+");
                    		for (int j = 2; j < items.length; j++) {
								addNotas(items[0], items[1], Double.valueOf(items[j]));
							}
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

    @Override
    public String toString() {
        return this.centroId;
    }
}