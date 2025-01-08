package estudio.empresa;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class GestionEmpresa {
	
	//El mapa guarda el nombre de la empresa en mayúsculas junto con la plantilla de empleados
	/*=========EJEMPLO==============
	|  empresa1  |  plantilla1  |
	|  empresa2  |  plantilla2  |
	|  empresa3  |  plantilla3  |
	|  empresa4  |  plantilla4  |
	================================*/
    private TreeMap<String, Plantilla> empresas;

    public GestionEmpresa() {
        empresas = new TreeMap<>();
    }

    public boolean agregarEmpresa(String nombreEmpresa) {
    	//Si la empresa (en minusculas) no existe, la añade al mapa
    	//Devuelve true si la empresa (en minusculas) se añade correctamente
    	//Devuelve false si la empresa ya existe
    	Plantilla p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) {
    		empresas.put(nombreEmpresa.toLowerCase(), p = new Plantilla());
    		return true;
    	}
    	return false;
    }

    public boolean agregarEmpleado(String nombreEmpresa, String nombreEmpleado, double sueldo) {
    	//Si la empresa (en minusculas) no existe, devuelve false
    	//Si la empresa (en minusculas) existe, añade el empleado a la plantilla
    	//Plantilla tenia un metodo para agregar empleados, ¿verdad?
        Plantilla p = null;
        p = empresas.get(nombreEmpresa.toLowerCase());
        if(p == null) {
        	empresas.put(nombreEmpresa.toLowerCase(), p = new Plantilla());
        	return false;
        }
        return p.agregarPersona(nombreEmpleado.toLowerCase(), sueldo);
    }
    
    public boolean eliminarEmpleado(String nombreEmpresa, String nombreEmpleado) {
    	//Si la empresa (en minusculas) no existe, devuelve false
    	//Si la empresa (en minusculas) existe, elimina el empleado de la plantilla y devuelve true
    	//Si el empleado no existe en la plantilla, devuelve false
    	Plantilla p = null;
    	p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return false;
		return p.eliminarPersona(nombreEmpleado.toLowerCase());
	}
    
    public Double buscarEmpleado(String nombreEmpresa, String nombreEmpleado) {
    	//Si la empresa (en minusculas) no existe, devuelve null
    	//Si la empresa (en minusculas) existe, busca el empleado en la plantilla y devuelve su sueldo
    	//Si el empleado no existe en la plantilla, devuelve null
    	Plantilla p = null;
    	p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return null;
		return p.buscarPersona(nombreEmpleado);
	}
    
    public TreeSet<String> obtenerListadoPersonas(String nombreEmpresa) {
    	//Si la empresa (en minusculas) no existe, devuelve null
    	//Si la empresa (en minusculas) existe, devuelve el listado de empleados de la plantilla
    	Plantilla p = null;
    	p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return null;
        return p.obtenerListadoPersonas();
    }

    public double obtenerMediaSueldosDeEmpresa(String nombreEmpresa) {
    	//Si la empresa (en minusculas) no existe, devuelve 0.0
    	//Si la empresa (en minusculas) existe, devuelve la media de sueldos de la plantilla
        Plantilla p = null;
        p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return 0.0;
        return p.obtenerSueldoMedio();
    }
    
    public Double obtenerSueldo(String nombreEmpresa, String nombreEmpleado) {
    	//Si la empresa (en minusculas) no existe, devuelve 0.0
    	//Si la empresa (en minusculas) existe, devuelve el sueldo del empleado de la plantilla
    	Plantilla p = null;
    	p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return 0.0;
        return p.obtenerSueldo(nombreEmpleado);	
	}
    
    public boolean incrementarSueldo(String nombreEmpresa, String nombreEmpleado, double incremento) {
    	//Si la empresa (en minusculas) no existe, devuelve false
    	//Si la empresa (en minusculas) existe, incrementa el sueldo del empleado de la plantilla
    	Plantilla p = null;
    	p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return false;
        return p.incrementarSueldo(nombreEmpleado, incremento);
	}
    
    public void incrementoPorcentualSueldos(String nombreEmpresa, double porcentaje) {
    	//Si la empresa (en minusculas) no existe, no hace nada
    	//Si la empresa (en minusculas) existe, incrementa los sueldos de la plantilla
		Plantilla p = null;
		p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return;
    	p.incrementoPorcentualSueldos(porcentaje);
	}
    
    public void incrementoPorcentualSueldos(double porcentaje) {
    	//Incrementa los sueldos de todas las empresas en el porcentaje indicado
    	//1 for
    	for (Plantilla p : empresas.values()) {
			p.incrementoPorcentualSueldos(porcentaje);
		}
    }

    public void actualizarSueldoEmpleado(String nombreEmpresa, String nombreEmpleado, double nuevoSueldo) {
    	//Si la empresa (en minusculas) no existe, no hace nada
    	//Si la empresa (en minusculas) existe, actualiza el sueldo del empleado de la plantilla
        Plantilla p = null;
        p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return;
    	p.actualizarSueldo(nombreEmpleado, nuevoSueldo);
    }

	public TreeSet<String> obtenerPersonasConMayorSueldo() {
		//Devuelve el conjunto de personas con mayor sueldo 
		//(puede haber más de una persona con el mismo sueldo maximo)
		double maxSueldo = Double.MIN_VALUE;
        TreeSet<String> personasConMaxSueldo = new TreeSet<>();
		for (Plantilla p : empresas.values()) {
			for (Entry<String, Double> par : p) {
				if(par.getValue() > maxSueldo) {
					personasConMaxSueldo.clear();
					maxSueldo = par.getValue();
				}
				if(par.getValue() == maxSueldo) personasConMaxSueldo.add(par.getKey());
			}
			
		}
		return personasConMaxSueldo;
	}
	
	public double obtenerSueldoMedio(String nombreEmpresa) {
		//Devuelve el sueldo medio de la empresa indicada (en minusculas)
		Plantilla p = null;
		p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return 0.0;
		return p.obtenerSueldoMedio();
	}
	
	public double obtenerSueldoMedioTodasEmpresas() {
		//Devuelve el sueldo medio de todas las empresas
		//Si no hay empresas, devuelve 0.0
		//2 for
		if(empresas.size() == 0) return 0.0;
		double sumaSueldos = 0;
		int cont = 0;
		for (Plantilla p : empresas.values()) {
			for (Entry<String, Double> par : p) {
				sumaSueldos += par.getValue();
				cont++;
			}
		}
		return sumaSueldos / cont;
	}
	
	public boolean actualizarSueldo(String nombreEmpresa, String nombreEmpleado, double nuevoSueldo) {
		//Si la empresa (en minusculas) no existe, devuelve false
		//Si la empresa (en minusculas) existe, actualiza el sueldo del empleado de la plantilla
		Plantilla p = null;
		p = empresas.get(nombreEmpresa.toLowerCase());
    	if(p == null) return false;
		return p.actualizarSueldo(nombreEmpleado, nuevoSueldo);
	}

	@Override
	public String toString() {
		return empresas.toString();
	}	
}
