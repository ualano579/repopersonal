package practicas.practica02.parte03;

import java.util.Iterator;

import practicas.auxiliar.AVLTree;
import practicas.auxiliar.Par;


public class Usuario implements Iterable<Par<String,AVLTree<String>>> {
	private final String usuarioId;
	private final MyTreeMap<String, AVLTree<String>> dispositivos; // par<dispositivoId, conjunto de palabras>
		
	public Usuario(String userID) {
		//2 líneas
		this.usuarioId = userID;
		this.dispositivos = new MyTreeMap<>();
	}
	
	public void clear() {
		this.dispositivos.clear();
	}
	
	public void add(String dispositivoId, String...palabras) {
		AVLTree<String> dispositivoCurr = this.dispositivos.get(dispositivoId);
		//La idea es sencilla: si no existe el dispositivo con clave dispositivoId, insertamos el par (dispositivoId, nuevo AVLTree)
		//A través de la referencia al árbol asociado con dispositivoId, insertamos las palabras
		//Vamos a implementarlo de la manera más compacta y eficiente posible...
		if(dispositivoCurr == null) this.dispositivos.put(dispositivoId, dispositivoCurr = new AVLTree<>());
		for (String str : palabras) {
			dispositivoCurr.add(str);
		}
	}

	@Override
	public String toString() {
		//1 única línea
		//Cuidado con la s: 0 dispositivos; 1 dispositivo; 2 dispositivos....
		return this.usuarioId + "=<"+this.dispositivos.size() + " dispositivo" + (this.dispositivos.size() == 1 ?  "" :  "s")+ ">" ;
	}

	@Override
	public Iterator<Par<String,AVLTree<String>>> iterator() {
		return dispositivos.pairSet().iterator();
	}
}
