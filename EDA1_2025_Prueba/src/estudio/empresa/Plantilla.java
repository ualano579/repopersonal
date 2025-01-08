package estudio.empresa;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class Plantilla implements Iterable<Entry<String, Double>>{
	
	//El mapa guarda el nombre de la persona en minúsculas junto con su sueldo
	/*=========EJEMPLO==============
	|  juan  |  95.000  |
	|  luis  |  80.000  |
	|  maria |  70.000  |
	|  pedro |  60.000  |
	================================*/
    private TreeMap<String, Double> empleados;

    public Plantilla() {
    	empleados = new TreeMap<>();
    }

    public boolean agregarPersona(String nombre, double sueldo) {
    	//Si el nombre ya existe en el mapa, no se puede agregar (devuelve false)
    	//Si no existe, se agrega (devuelve true)
    	//El nombre se guarda en minúsculas
    	Double d = empleados.get(nombre.toLowerCase());
    	if(d != null) return false;
    	empleados.put(nombre.toLowerCase(), sueldo);
        return true;
    }
    
	public boolean eliminarPersona(String nombre) {
		//Si el nombre no existe en el mapa, no se puede eliminar (devuelve false)
		//Si existe, se elimina (devuelve true)
		//El nombre debe estar en minúsculas
        Double d = empleados.get(nombre.toLowerCase());
        if(d != null) empleados.remove(nombre.toLowerCase());
        return d != null;
    }
	
	public Double buscarPersona(String nombre) {
		//Devuelve el sueldo de la persona con el nombre indicado en minusculas
		//Si no existe, devuelve null
		Double d = empleados.get(nombre.toLowerCase());
        return d == null ? null : d;
    }
	
	public TreeSet<String> obtenerListadoPersonas() {
		//Obtiene un listado ordenado de las personas en la empresa
		TreeSet<String> result = new TreeSet<>();
		result.addAll(empleados.keySet());
        return result;
    }

    public double obtenerSueldo(String nombre) {
    	//Obtiene el sueldo de la persona con el nombre indicado en minusculas
    	//Si no existe, devuelve 0.0
    	Double d = empleados.get(nombre.toLowerCase());
        return d == null ? 0.0 : d;
    }
    
    public boolean incrementarSueldo(String nombre, double cantidad) {
    	//Incrementa el sueldo de la persona con el nombre indicado en minusculas
    	//es decir, el sueldo actualizado  = sueldo actual + cantidad
    	//Si no existe, devuelve false
        Double sueldoActual = empleados.get(nombre.toLowerCase());
        if(sueldoActual != null) empleados.put(nombre.toLowerCase(), sueldoActual + cantidad);
        return sueldoActual != null;
    }
    
    public void incrementoPorcentualSueldos(double porcentaje) {
    	//Incrementa todos los sueldos en un porcentaje indicado
    	//Este porcentaje es un valor entero positivo
    	//Por ejemplo, si porcentaje = 10, se incrementa un 10% el sueldo
        for (Entry<String, Double> par : empleados.entrySet()) {
			empleados.put(par.getKey(), par.getValue() + par.getValue()*(porcentaje)/100);
		}
    }
    
    public TreeSet<String> obtenerPersonasConMayorSueldo() {
    	//Obtiene el conjunto de personas con el mayor sueldo
        double maxSueldo = Double.MIN_VALUE;
        TreeSet<String> personaConMaxSueldo = new TreeSet<>();
        for (Entry<String, Double> par : empleados.entrySet()) {
			if(par.getValue() > maxSueldo) {
				personaConMaxSueldo.clear();
				maxSueldo = par.getValue();
			}
			if(par.getValue() == maxSueldo) personaConMaxSueldo.add(par.getKey());
		}
        return personaConMaxSueldo;
    }
    
    public double obtenerSueldoMedio() {
    	//Obtiene el sueldo medio de todas las personas de la empresa
    	//Si no hay personas, devuelve 0.0
        double suma = 0.0;
        for (Double d : empleados.values()) {
			suma += d;
		}
        return empleados.size() == 0 ? 0.0 : suma/empleados.size();
    }
    
    public boolean actualizarSueldo(String nombre, double nuevoSueldo) {
    	//Actualiza el sueldo de la persona con el nombre indicado en minusculas
    	//Si no existe, devuelve false
    	Double d = empleados.get(nombre.toLowerCase());
    	if(d != null) {
    		empleados.put(nombre.toLowerCase(), nuevoSueldo);
    		return true;
    	}
    	return false;
    }

	@Override
	public Iterator<Entry<String, Double>> iterator() {
		//Iterar sobre una empresa es iterar sobre el mapa de personas
		return empleados.entrySet().iterator();
	}

	@Override
	public String toString() {
		return empleados.toString();
	}

}
