package practicas.practica01;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;

public class Usuario implements Iterable<Dispositivo> {
	private static int numUsuarios = 0; //atributo estático...¿qué significa esto? que es compartido por todos
	private String nombre; //atributo clave
	private int usuarioId;
	private ArrayList<Dispositivo> dispositivos;
	
	public static void inicializarNumUsuarios() {
		numUsuarios = 0;//¿Sería posible escribir this.numUsuarios = 0? Como la variable es estatica no es posible pq no es tuya
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public Usuario(String nombre) {
		//Si el parámetro nombre es nulo o está vacío se lanza excepción
		//Al atributo this.usuarioId se le asigna el número de usuarios creados hasta el momento (identificador autoincremental)
		//4 líneas
		
		if(nombre == null || nombre.trim().isEmpty()) throw new RuntimeException("El atributo nombre no puede ser ni nulo ni vacío");//isBlank() hace lo mismo que trim().isEmpty()
		this.nombre = nombre.trim();
		this.usuarioId = ++numUsuarios;//Los ++ van antes pq primero se incrementa y lgo se asigna
		this.dispositivos = new ArrayList<>();
	}
	
	public void vaciarDispositivos() {
		//1 for()

		for (Dispositivo dispositivo : dispositivos) {
			dispositivo.vaciarDispositivo();
		}
		this.dispositivos.clear();
	}
	
	public void addDispositivos(Dispositivo... dispositivos) { /// ... significa que desde dentro lo tratamos con array, no se pone pq entonces si o si tendremos q pasar un array y asi no
		//La realización de este método es esencial para el correcto funcionamiento de la práctica
		//Atención al uso del constructor copia a la hora de añadir un dispositivo a la lista de dispositivos del usuario
		//En la colección this.dispositivos se añade una copia de los dispositivos especificados en el array de parámetros de entrada (no copian las referencias)
		//No se permite almacenar dispositivos repetidos
		for (Dispositivo dispositivo : dispositivos) {
			//2 líneas
			if(!this.dispositivos.contains(dispositivo)) {
				this.dispositivos.add(new Dispositivo(dispositivo));//hace uso del constructor copia
			}
		}
	}
	
	public int getNumDispositivos() { 
		return this.dispositivos.size();
	}

	public boolean enviarMensaje(String nombreDispositivo, String mensaje) {
		//En primer lugar comprobamos que el usuario tenga el dispositivo especificado como parámetro de entrada (uso del método indexOf())
		//Si no existe el dispositivo especificado se devuvelve false
		//En caso contrario se hace uso del método enviarMensaje() del dispositivo correspondiente
		///3 líneas
		//...
		int pos = this.dispositivos.indexOf(new Dispositivo(nombreDispositivo));//le estas diciendo buscame un dispositivo cn ese nombre
		if (pos == -1) return false;
		this.dispositivos.get(pos).enviarMensaje(mensaje);
		return true;
	}
	
	public int getNumPalabras(String nombreDispositivo) {
		//Si el usuario no tiene el dispositivo especificado como parámetro de entrada, se devuelve el valor 0
		//En caso contrario se hará uso del método getNumPalabras() del dispositivo correspondiente
		//¿A qué nos referimos con lo del "dispositivo correspondiente"? 
		//3 líneas
		int pos = this.dispositivos.indexOf(new Dispositivo(nombreDispositivo));
		if(pos == -1) return 0;
		return this.dispositivos.get(pos).getNumPalabras();
	}
	
	public ArrayList<String> getPalabras() {
		ArrayList<String> result = new ArrayList<>();
		//Queremos el conjunto completo de palabras (sin repetir) que han sido enviadas por el usuario, independientemente del dispositivo utilizado
		//Ojo a la forma de iterar sobre la colección this.dispositivos y, sobre todo, cómo iteramos sobre la colección this.registroPalabras que tiene cada uno de los dispositivos
		//Prohibido esto de de Iterator<?> iter = new Iterar... En su lugar, forEach...   
		//2 for()
		for (Dispositivo dispositivo : dispositivos) {
			for(String palabra : dispositivo) {
				if(result.contains(palabra)) continue;
				result.add(palabra);
			}
		}
		//Hay que ordenar el resultado, ¿verdad? si
		result.sort(null);//null esq no le estamos pasando ninguna forma expecifica de ordenar y por defecto lo ordena en el orden alfanumerico
		return result;
	}
	
	public BitSet getInterseccion(Usuario otro) {
		BitSet result = new BitSet(Parametros.numCaracteristicas);
		//Intersecar dos usuarios implica realizar la operación de intersección de todos los dispositivos que poseen (de dos en dos)
		//2 for()
		for(Dispositivo thisdisp : this.dispositivos) {
			for(Dispositivo otrodisp : otro.dispositivos) {
				result.or(thisdisp.getInterseccion(otrodisp));
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return this.usuarioId + ".- "+ this.nombre + " -> "+this.dispositivos;
	}
	
	@Override
	public boolean equals(Object otro) {
		//Recuerda que la clave de un usuario es su nombre
		return this.nombre.equals(((Usuario) otro).nombre);
	}

	@Override
	public Iterator<Dispositivo> iterator() {
		//Iterar sobre un usuario implica iterar sobre su colección de dispositivos
		return this.dispositivos.iterator();
	}
}