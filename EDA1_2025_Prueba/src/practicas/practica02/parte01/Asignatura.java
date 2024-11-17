package practicas.practica02.parte01;

import java.util.ArrayList;
import java.util.Comparator;

public class Asignatura implements Comparable<Asignatura>{
    private final String asignaturaId; //Atributo clave
    private final int cuatrimestre;
    private final ArrayList<String> docentesId;
    private final ArrayList<Integer> valoraciones;

    public Asignatura(String asignaturaId, int cuatrimestre) {
    	//Si el parámetro asignaturaId es null o es vacía, el atributo asignaturaId será "sinNombre".
    	//En caso contrario, atención a los espacios en blanco y cuidado con mayúsculas y minúsculas
    	//El valor del parámetro deberá ser >=1 y <=8 (0 en caso contrario)
    	//4 líneas
    	
    	this.asignaturaId = (asignaturaId == null || asignaturaId.trim().isEmpty()) ? "sinNombre" : asignaturaId.trim().toLowerCase();
    	this.cuatrimestre = (cuatrimestre>=1 && cuatrimestre <= 8) ? cuatrimestre : 0;
    	this.valoraciones = new ArrayList<>();
    	this.docentesId = new ArrayList<>();
    }

    public Asignatura(String asignaturaId) {
        this(asignaturaId, 0); //¿Qué hace esto? 
    }
    
    public int getCuatrimestre() {
        return this.cuatrimestre;
    }

    public String getAsignaturaId() {
        return this.asignaturaId;
    }

    public void addDocentes(String... docentesId) {
    	//1 único for()
    	for (String docente : docentesId) {
    		String d = docente.trim().toLowerCase();
    		if(this.docentesId.contains(d)) continue;
			this.docentesId.add(d);
		}
    }

    public void addValoraciones(Integer... valoraciones) {
    	//1 único for()
    	//Si, por casualidad, una valoración es nula, se añade un 0 en la colección valoraciones
    	//Si una valoración es menor que 0 o mayor que 10, se ignora
    	for(Integer val : valoraciones) {
    		val = (val ==null) ? 0 : val;
    		if (val < 0 || val > 10) continue;
    		this.valoraciones.add(val);
    	}
    }
    
    public int getValoracionMaxima() {
    	int result = Integer.MIN_VALUE;
    	if(this.valoraciones.isEmpty()) return 0;
    	//1 único for()
    	//for(int i= 0; i<valoraciones.size(); i++) {
    		//if(valoraciones.get(i)> result) result = valoraciones.get(i);
    	//}
    	
    	for (Integer val : valoraciones) {
    		if(val>result) result = val;//  ó result = (val > result) ? val : result;
    	}
    	return result;
    }
    
    public boolean esDocente(String docenteId) {
    	//Comprobamos que docenteId no sea nulo ni vacío; si pasa esto, se devuelve false
    	//2 líneas
    	if(this.docentesId == null || this.docentesId.isEmpty()) return false;
    	return this.docentesId.contains(docenteId.toLowerCase());
    }

    public ArrayList<String> getDocentes(Comparator<String> comp) {
    	//3 líneas
    	ArrayList<String> arr = new ArrayList<>(this.docentesId);//array copia de this.docentesId
    	arr.sort(comp);//Ordena según el comp
    	return arr;
    }
    
    public void clear() {
    	//2 líneas
        this.docentesId.clear();
        this.valoraciones.clear();
    }

    @Override
    public String toString() {
        String cuatrimestre;
        switch (this.cuatrimestre) {
            case 1: cuatrimestre = "1º-1C"; break;
            case 2: cuatrimestre = "1º-2C"; break;
            case 3: cuatrimestre = "2º-1C"; break;
            case 4: cuatrimestre = "2º-2C"; break;
            case 5: cuatrimestre = "3º-1C"; break;
            case 6: cuatrimestre = "3º-2C"; break;
            case 7: cuatrimestre = "4º-1C"; break;
            case 8: cuatrimestre = "4º-2C"; break;

            default: cuatrimestre = "?º-?C";
        }
        //Se pde cambiar lo que hay dentro del switch --> if (this.cuatrimestre<1 || this.cuatrimestre > 8) return "?º-?C"
        //cuatrimestre = (this.cuatrimestre /2 +1 ) + "º-" + ((this.cuatrimestre -1)%2 +1) + "C"
        
        //eda1 (2º-1C) <10>
        return this.asignaturaId + " ("+cuatrimestre+") <"+ getValoracionMaxima() +">";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true; //¿En qué casos ocurre esto? Cuando tiene la misma ref de memoria
        if (!(other instanceof Asignatura)) return false; //¿Y esto? Si no es de clase Asignatura
        return this.compareTo((Asignatura)other) == 0; //Observa que delegamos en el método compareTo()....
        //¿qué ventajas tiene esta implementación? 1º se lo delegamos a otro metodo y yo no tengo q escribir tanto, 2º sabriamos que el comportamiento
        //del equals y del compareTo seria el mismo, hacemos que tenga el mismo comportamiento
    }

    @Override
    public int compareTo(Asignatura other) {
    	//Recuerda que el atributo clave es asignaturaId (orden ascendente, es decir, de menor a mayor)
        return this.asignaturaId.compareTo(other.asignaturaId);//estoy usando el compareTo de la clase String
    }
}