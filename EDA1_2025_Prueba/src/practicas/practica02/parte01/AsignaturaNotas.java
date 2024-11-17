package practicas.practica02.parte01;

import java.util.ArrayList;

import practicas.auxiliar.Format;

public class AsignaturaNotas implements Comparable<AsignaturaNotas> {
  
	private final Asignatura asignatura; //Referencia a una asignatura. ¿Por qué se distingue entre Asignatura y AsignaturaNotas? ¿Qué objetivo se persigue?
    private final ArrayList<String> notas; //Ojo con el tipo base de la colección: String

    public AsignaturaNotas(Asignatura asignatura) {
    	//2 líneas
    	this.asignatura = asignatura;
    	this.notas = new ArrayList<>();
    }
    
    public AsignaturaNotas(String asignaturaId) {
    	//2 líneas
    	this.asignatura = new Asignatura(asignaturaId);
    	this.notas = new ArrayList<>(); 
    }
    
    public int getCuatrimestre() {
    	return this.asignatura.getCuatrimestre();
    }
    
    public String getAsignaturaId() {
    	return this.asignatura.getAsignaturaId();
    }
    
    public void addNotas(Double... notas) {
    	//1 único for()
    	//Si una nota es null, se inserta la cadena "0.00"
    	//Para convertir double -> String hacemos uso de "nuestro" método Format.formatDouble() (ver clase Format en paquete practicas.auxiliar)
    	for (Double nota : notas) {
			this.notas.add(Format.formatDouble(nota == null ? 0 : nota));
		}
    }

    public String getNotaMedia() {
        double suma = .0;
        //1 simple for()
        for (String nota : notas) {
			suma += Double.parseDouble(nota);
			//suma += Double.valueOf(nota);
		}

        return notas.size()== 0 ? "0.00" :Format.formatDouble(suma/notas.size());
    }

    public void clear() {
        this.notas.clear();
    }
    
    public ArrayList<String> getDocentes() {
    	return this.asignatura.getDocentes(null);
    }

    @Override
    public String toString() {
    	//datos de asignatura -> [notas] <nota media>
    	return this.asignatura + " -> " + this.notas + " <"+getNotaMedia()+">"; //Cuando concateno un obj con una cadena de texto implicitamente se una 
    	// el toString del obj, q en el caso deq sea null devolvera null y no un excepción
    }
    
    @Override
	public boolean equals(Object otra) {
    	if (this == otra) return true; //Misma referencia en memoria, estamos hablando del mismo obj
    	if (!(otra instanceof AsignaturaNotas)) return false;// Si no es de la misma clase
		return this.compareTo((AsignaturaNotas)otra) == 0;
	}

	@Override
	public int compareTo(AsignaturaNotas otra) {
		//atributo clave: asignatura (orden ascendente)
		return this.asignatura.compareTo(otra.asignatura);
	}
}
