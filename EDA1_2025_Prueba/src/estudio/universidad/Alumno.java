package estudio.universidad;

public class Alumno implements Comparable<Object>{
	
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	public Alumno(String dni, String nombre, String apellido1, String apellido2) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
	}
	
	public Alumno(String dni) {
		this(dni, null, null, null);
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Override
	public boolean equals(Object obj) {
		//Dos alumnos son iguales si tienen el mismo dni
		Alumno otro = (Alumno) obj;
		if(this.getDni() == otro.getDni()) return true;
		return false;
	}

	public int compareTo(Object o) {
		//Se comparan segun el criterio apellido1, apellido2, nombre y dni
		Alumno otro = (Alumno) o;
		
		
	    
		int cmp = this.apellido1.compareTo(otro.apellido1);
	    if (cmp != 0) return cmp;

	    // Comparar por apellido2
	    cmp = this.apellido2.compareTo(otro.apellido2);
	    if (cmp != 0) return cmp;
	    
	 // Comparar por nombre
	    cmp = this.nombre.compareTo(otro.nombre);
	    if (cmp != 0) return cmp;
	    
	    // Comparar por dni 
	    cmp = this.dni.compareTo(otro.dni);
	    if(cmp != 0) return cmp;

	    return 0;
	}

	@Override
	public String toString() {
		return this.dni + " -> " + this.nombre + " " +this.apellido1 + " " + this.apellido2;
	}
	
}