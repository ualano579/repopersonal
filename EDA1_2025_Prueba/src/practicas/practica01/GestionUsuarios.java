package practicas.practica01;

import java.util.ArrayList;

public class GestionUsuarios {
	private ArrayList<Usuario> usuarios;
	 
	public GestionUsuarios() {
		this.usuarios = new ArrayList<>();
	}
	
	public void addDispositivos(Usuario usuario, Dispositivo... dispositivos) {
		//Antes de hacer nada, habrá que comprobar si el usuario ya está en la colección this.usuarios
		//Si está, pues nada, se accede a su posición y se inserta el conjunto de dispositivos especificados como parámetros
		//Si no está, se añade a la colección this.usuarios y, acto seguido, se añaden los dispositivos.
		//1 línea + 1 if con solo dos instrucciones + 1 última línea
		int pos = this.usuarios.indexOf(usuario);
		if(pos == -1) {
			this.usuarios.add(usuario);
			pos = this.usuarios.size()-1;
		}
		this.usuarios.get(pos).addDispositivos(dispositivos);
	}
	
	public String getGradosSimilitud(Usuario usuario) {
		//"Bob -> Alice <1> Chace <2> Eve <2> "
		String result = usuario.getNombre()+ " -> ";
		//1 for()
		for (Usuario user : usuarios) {
			if(usuario.equals(user)) continue;
			result += user.getNombre()+ " <"+ usuario.getInterseccion(user).cardinality() + "> ";
		}
		return result;
	}

	public boolean enviarMensaje(String nombreUsuario, String nombreDispositivo, String mensaje) {
		//Si el usuario con clave nombreUsuario no existe, el método devuelve false
		//True en caso contrario
		//3 líneas
		int pos = this.usuarios.indexOf(new Usuario(nombreUsuario));
		if (pos == -1) return false;
		return this.usuarios.get(pos).enviarMensaje(nombreDispositivo, mensaje);
	}
	
	public ArrayList<String> getPalabrasUsuario(String nombreUsuario) {
		ArrayList<String> result = new ArrayList<>();
		//Si el usuario con clave nombreUsuario no existe, el método devuelve null
		//En caso contrario, devuelve el conjunto de palabras iterando sobre usuarios y, para cada usuario, sobre sus dispositivos...
		//Recuerda que iterar sobre un dispositivo equivale a iterar sobre la colección this.registroPalabra que contiene
		int pos = this.usuarios.indexOf(new Usuario(nombreUsuario));
		if(pos == -1) return null;
		//2 for() 
		for (Dispositivo disp : this.usuarios.get(pos)) {
			for (String p : disp) {
				if(result.contains(p)) continue;
				result.add(p);
			}
			
		}
		return result;
	}
	
	@Override
	public String toString() {
		return this.usuarios.toString();
	}
}