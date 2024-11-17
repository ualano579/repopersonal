package practicas.practica02.parte01;

import java.util.*;

import practicas.auxiliar.Format;

public class Estudiante implements Comparable<Estudiante>, Iterable<AsignaturaNotas>{
    private final String alumnoId;
    private final ArrayList<AsignaturaNotas> matricula;

    public Estudiante(String alumnoId) {
    	//Si el parámetro alumnoId es nulo o está vacío, al atributo alumnoId se le asigna la cadena "sinIdentificación"
    	//2 líneas
    	this.alumnoId = alumnoId == null || alumnoId.isBlank() ? "sinIdentificación" : alumnoId.trim().toLowerCase();
    	this.matricula = new ArrayList<>();
    }

    public void addAsignaturas(Asignatura... asignaturas) {
    	//1 for()
    	for (Asignatura asignatura : asignaturas) {
			if(matricula.contains(new AsignaturaNotas(asignatura))) continue;
			matricula.add(new AsignaturaNotas(asignatura));
		}
    }

    public boolean addNotas(String asignaturaId, Double... notas) {
    	//Si el estudiante no está matriculado en la asignatura con clave asignaturaid, se devuelve false
    	//Haremos uso de indexOf(), ¿verdad?
    	//3 líneas
    	int pos = matricula.indexOf(new AsignaturaNotas(asignaturaId));
    	if (pos == -1) return false;
    	this.matricula.get(pos).addNotas(notas);
        return true;
    }

    public String getNotaMedia() {
        double suma = .0;
        //1 for()
        for (AsignaturaNotas an : matricula) {
			suma += Double.valueOf(an.getNotaMedia());
		}
        return Format.formatDouble(suma/matricula.size());
    }

    public String getNotaMedia(String asignaturaId) {
        int pos = this.matricula.indexOf(new AsignaturaNotas(asignaturaId));
        //1 única línea		
        if(pos == -1) return "-1.00";
        return this.matricula.get(pos).getNotaMedia();
    }
    public void clear() {
    	//1 for()
        for (AsignaturaNotas asignaturaNotas : matricula) {
			asignaturaNotas.clear();
		}
        this.matricula.clear();
    }

    @Override
    public String toString() {
        String result = "Estudiante con id = " + this.alumnoId;
        //Aquí se hace necesario el uso del comparator AsignaturaComp... + 1 for()
        //Recuerda que \n implica un salto de línea y \t un tabulador
        this.matricula.sort(new AsignaturaComp());
        for (AsignaturaNotas an : matricula) {
			result += "\n\t"+an;
		}
        return result + "\n";
    }

    @Override
    public int compareTo(Estudiante other) {
    	//clave alumnoId (orden ascendente)
    	return this.alumnoId.compareTo(other.alumnoId);
    }

    @Override
    public Iterator<AsignaturaNotas> iterator() {
    	//Iterar sobre un estudiante equivale a iterar sobre la colección this.matricula
        return this.matricula.iterator();
    }
}