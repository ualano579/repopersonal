package practicas.practica01;

import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Dispositivo implements Iterable<String> {
	private String modelo; //atributo clave
	private BitSet caracteristicas;
	protected LinkedList<String> registroPalabras; 
		
	public Dispositivo(String modelo) {
		//Si el parámetro modelo es vacío, el modelo del dispositivo será "noModel"; en caso contrario, asignamos como atributo modelo el valor del parámetro 
		//eliminando los posibles espacios en blanco que tenga delante y detrás (haz uso del método trim())
		//Asignamos a las diez características (haz uso de la constante Parametros.numCaracteristicas) el valor false
		//Inicializamos la estructura registroPalabras
		
		this.modelo =modelo.trim().isEmpty() ? "noModel" : modelo.trim();
		this.caracteristicas= new BitSet(Parametros.numCaracteristicas);
		this.registroPalabras = new LinkedList<>();
	}
	
	public Dispositivo(String modelo, String caracteristicas) {
		//Si el número de características es distinto de la constante Parametros.numCaracteristicas se ha de lanzar una 
		//excepción (throw new RuntimeException(...))
		//El resto exactamente igual que el constructor anterior salvo la asignación de valores a las características del dispositivo
		//Recuerda que si el valor es '0', la característica se asigna como false; en caso contrario, true (sea el carácter que sea)
		//El único for() del método deberá estar compuesto por una única línea (uso del operador ternario ... vídeo del Ejercicio01)
		this(modelo);//Llama al constructor padre Dispositivo(String modelo)
		if(caracteristicas.length() != Parametros.numCaracteristicas) {
			throw new RuntimeException("El número de características debe ser, exactamente, igual a "+ Parametros.numCaracteristicas);
		}
		for(int i = 0; i < caracteristicas.length(); i++) {
			/*if(i == '0') {
				this.caracteristicas.set(i, false);
			}else {
				this.caracteristicas.set(i, true);
			}*/
			
			this.caracteristicas.set(i, caracteristicas.charAt(i) == '0' ? false : true);
			//Se podría poner .... , caracteristicas.charAt(i) != '0')
		}
	}
	
	public Dispositivo(Dispositivo otro) {
		//Se trata de implementar el constructor copia de Dispositivo
		//3 líneas
		//Para el atributo this.características haremos uso del método clone()
		//Para el atributo this.registroPalabras haremos uso del constructor copia de la 
		//colección LinkedList<>
		
		this.modelo = otro.modelo;
		this.caracteristicas = (BitSet) otro.caracteristicas.clone();
		this.registroPalabras = new LinkedList<>(otro.registroPalabras);
	}
	
	public BitSet getInterseccion(Dispositivo otro) {
		//Devolvemos un nuevo BitSet con la intersección (AND) de this.caracteristicas y otro.caracteristicas
		//Vamos a echar un vistazo a los métodos de la clase BitSet que implementan operaciones lógicas
		BitSet result = (BitSet) this.caracteristicas.clone();
		//1 única línea
		result.and(otro.caracteristicas); //Se hace una copia pq la operación and modifica el primer Bitset
		return result;
	}
	
	public int getNumPalabras() {
		return this.registroPalabras.size();
	} 
	
	public void vaciarDispositivo() {
		this.registroPalabras.clear();
	}
	
	public void enviarMensaje(String mensaje) {
		//Para "trocear" la cadena vamos a hacer uso del método split() 
		//Como tenemos que eliminar todas las ocurrencias de los espacios en blanco, comas y puntos, utilizaremos el patrón "[ ,.]+"
		//Ten en cuenta que las palabras se guardan en minúsculas y que no se permiten palabras repetidas
		//1 único for()
		//.toLowerCase() hacer minuscula
		String[] palabras = mensaje.toLowerCase().split("[ ,.]+"); //Que corte por cualquiera de lo que hay dentro del corchete y el + significa que cualquier combinación de ellas
		//for each solo si se va a recorrer entero
		for(String palabra : palabras) {
			if(!this.registroPalabras.contains(palabra)) {
				this.registroPalabras.add(palabra);
			}
		}
	}
	
	@Override
	public String toString() {
		return this.modelo+ " "+this.caracteristicas; //No se pone toString pq al estar en una cadena se hace un toString implicito
	}
	
	@Override
	public boolean equals(Object otro) {
		//Como la clave de Dispositivo es el atributo this.modelo, dos dispositivos serán iguales sii (si y solo si) sus modelos lo son
		//Determinar si dos dispositivos son iguales implicará comparar las cadenas this.modelo y otro.modelo
		//La clase String ya tiene su método compareTo(), ¿verdad?
		//1 única línea
		return this.modelo.compareTo(((Dispositivo)otro).modelo) == 0;
	}

	@Override
	public Iterator<String> iterator() {
		//Itrerar sobre un dispositivo equivale a iterar sobre la colección this.registroPalabras
		//1 única línea
		return this.registroPalabras.iterator();
	}
}