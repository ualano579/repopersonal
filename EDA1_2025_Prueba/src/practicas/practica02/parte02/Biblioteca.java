package practicas.practica02.parte02;

import java.util.ArrayList;
import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Par;

public class Biblioteca {
	private final String bibliotecaId; //identificador de biblioteca
 	private final AVLTree<Libro> libros;
	private final AVLTree<String> usuarios;
	private final AVLTree<Par<Libro, String>> prestamos;
	private final AVLTree<Par<String, ArrayList<Libro>>> historicoPrestamos;
	
	public Biblioteca(String bibliotecaId) {
		this.bibliotecaId = bibliotecaId;
		this.libros = new AVLTree<>();
		this.usuarios = new AVLTree<>();
		this.prestamos = new AVLTree<>();
		this.historicoPrestamos = new AVLTree<>();
	}
	
	public void addLibro(Libro libro) {
		this.libros.add(libro);
	}
	
	public void addLibro(String libroId) {
		this.libros.add(new Libro(libroId));
	}
		
	public void addUsuario(String usuarioId) {
		this.usuarios.add(usuarioId);
	}
	
	public void clear() {
		this.libros.clear();
		this.usuarios.clear();
		this.prestamos.clear();
		this.historicoPrestamos.clear();
	}
	
	public boolean prestarLibro(String usuarioId, String libroId) {
		//Si el libro con clave libroId no pertenece a la biblioteca (no está contenido en la colección this.libros) --> devuelve false
		//Si el usuario con clave usuarioId no es miembro de la biblioteca (no está contenido en la colección this.usuarios) --> devuelve false
		//Si ya está prestado (el par (libro, usuario) existe en la colección this.prestamos) --> devuelve false
		//En caso contrario, se añade el par (libro, usuario) y se guardaHistorico
		//6 líneas
		Libro libroCurr = this.libros.find(new Libro(libroId));
		if(libroCurr == null) return false;
		String usuarioCurr = this.usuarios.find(usuarioId);
		if(usuarioCurr == null) return false;
		if(this.prestamos.contains(new Par<>(libroCurr, usuarioCurr))) return false;
		this.prestamos.add(new Par<>(libroCurr, usuarioCurr));
		guardarHistorico(libroCurr, usuarioCurr);
		return true;
	}

	private void guardarHistorico(Libro libro, String usuario) {
		//Si no existe el par con clave usuario se añade la entrada en la colección historicoPrestamos
		//Se actualiza añadiendo el libro a la colección asociada con la clave usuario
		Par<String, ArrayList<Libro>> parCurr = this.historicoPrestamos.find(new Par<>(usuario, null));
		if(parCurr == null){
			parCurr = new Par<>(usuario, new ArrayList<>());
			this.historicoPrestamos.add(parCurr);
		}
		parCurr.getValue().add(libro);
	}

	public boolean devolverLibro(String usuarioId, String libroId) {
		//Si el libro con clave libroId no pertenece a la biblioteca (no está contenido en la colección this.libros) --> devuelve false
		//Si el usuario con clave usuarioId no es miembro de la biblioteca (no está contenido en la colección this.usuarios) --> devuelve false
		//Si no estaba prestado (el par (libro, usuario) no existe en la colección this.prestamos) --> devuelve false
		//Si está prestado, pero a otro usuario, se devuelve false
		//En caso contrario, se elimina el par de la colección this.prestamos
		//8 líneas
		Libro libroCurr = this.libros.find(new Libro(libroId));
		if(libroCurr == null) return false;
		String usuarioCurr = this.usuarios.find(usuarioId);
		if(usuarioCurr == null) return false;
		Par<Libro, String> prestCurr = this.prestamos.find(new Par<>(libroCurr, null));
		if(prestCurr == null) return false;
		if(!prestCurr.getValue().equals(usuarioCurr)) return false;
		this.prestamos.remove(prestCurr);
		return true;
	}

	public ArrayList<String> getUsuariosLibro(String libroId) {		
		ArrayList<String> result = new ArrayList<>();
		//Si el libro no existe en la biblioteca, se devuelve null
		//1 for() para iterar sobre la colección this.historicoPrestamos
		//Ten en cuenta que un usuario puede tomar prestado el mismo libro múltiples veces
		Libro libroCurr = this.libros.find(new Libro(libroId));
		if(libroCurr == null) return null;
		for (Par<String,ArrayList<Libro>> par : historicoPrestamos) {
			if(result.contains(par.getKey())) continue;
			if(par.getValue().contains(libroCurr)) result.add(par.getKey());
		}
		return result;
	}
	
	public ArrayList<String> getLibrosPrestados(String usuarioId){
		ArrayList<String> result = new ArrayList<>(); //¿Podemos mejorar esta instrucción creando el ArrayList solo cuando sea necesario?
		//Si el ususario no existe, se devuelve null
		//Devolvemos los libros que el usuario con clave usuarioId ha tomado prestados teniendo en cuenta la colección this.historicoPrestamos
		//1 for()
		String usuarioCurr = this.usuarios.find(usuarioId);
		if(usuarioCurr == null) return null;
		Par<String, ArrayList<Libro>> p = this.historicoPrestamos.find(new Par<>(usuarioId, null));
		if(p != null) {
			for (Libro l : p.getValue()) {
				result.add(l.getLibroId());
			}
		}
		return result;
		
	}
		
	public ArrayList<String> getLibrosPrestados(){
		ArrayList<String> result = new ArrayList<>();
		//Añadimos en result todos los identificadores de libros que están actualmente prestados
		//1 for()
		for (Par<Libro,String> par : prestamos) {
			result.add(par.getKey().getLibroId());
		}
		return result;
	}

	@Override
	public String toString() {
		//"Biblioteca UAL (" + NUM_LIBROS + " libros y " + NUM_USUARIOS + " usuarios)"
		return this.bibliotecaId + " (" + this.libros.size() + " libros y " + this.usuarios.size() + " usuarios)";
	}
}