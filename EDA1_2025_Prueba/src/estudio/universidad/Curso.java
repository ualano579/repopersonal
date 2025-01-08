package estudio.universidad;

public class Curso implements Comparable<Object> {
	
	private String carrera;
	private int curso;
	private String asignatura;
	
	public Curso(String carrera, int curso, String asignatura) {
		this.carrera = carrera;
		this.curso = curso;
		this.asignatura = asignatura;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}
	
	@Override
	public boolean equals(Object obj) {
		//Dos cursos son iguales si tienen la misma carrera, curso y asignatura
		Curso otro = (Curso) obj;
		if(this.carrera == otro.carrera && this.curso == otro.curso && this.asignatura == otro.asignatura) return true;
		return false;
	}
	
	public int compareTo(Object o) {
		//Se comparan segun el criterio carrera, curso y asignatura
		Curso otro = (Curso) o;
		
		int cmp = this.carrera.compareTo(otro.carrera);
		if(cmp != 0) return cmp;
		
		cmp = Integer.compare(this.curso, otro.curso);
		if(cmp != 0) return cmp;
		
		cmp = this.asignatura.compareTo(otro.asignatura);
		if(cmp != 0) return cmp;
		
		return 0;
	}

	@Override
	public String toString() {
		return this.carrera + " " + this.curso + "º (" + this.asignatura + ")";
	}
	
	

}

