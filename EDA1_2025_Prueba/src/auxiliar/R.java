package auxiliar;

import java.util.Map.Entry;
import java.util.TreeMap;

public class R {
	static TreeMap <String, TreeMap <String, Double>> datos;
	
	public static boolean check (String id) {
		double n = 0;
		TreeMap<String, Double> aux = datos.get(id);
		if (aux == null) return false;
		for (Double i : aux.values()) {
			n += i;
		}
		return n == 1;
	}
	
	public static void repare(String id) {
		if(check(id)) return;
		TreeMap<String, Double> aux = datos.get(id);
		if (aux == null) return;
		double n = 0;
		for (Double i : aux.values()) {
			n += i;
		}
		for (Entry<String, Double> par : aux.entrySet()) {
			aux.put(par.getKey(), par.getValue()/n);
		}
	}
	
	public static double distancia(String idOrigen, String idDestino) {
		TreeMap<String, Double> aux = datos.get(idOrigen);
		if (aux == null) return 0;
		TreeMap<String, Double> aux2 = datos.get(idDestino);
		if (aux2 == null) return 0;

        double suma = 0;
        // Calcula la suma de las diferencias cuadradas entre todos los valores posibles.
        for (Double i : aux.values()) {
            for (Double i2 : aux2.values()) {
                suma += Math.pow(i - i2, 2);
            }
        }
        return Math.sqrt(suma); // Devuelve la raíz cuadrada de la suma.
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Inicialización del mapa principal
        datos = new TreeMap<>();

        // Creación de dos claves principales y asignación de mapas vacíos
        datos.put("id00", new TreeMap<>());
        datos.put("id01", new TreeMap<>());

        // Asignación de atributos y valores a las claves
        datos.get("id00").put("at00", 0.008);
        datos.get("id00").put("at03", 0.04);
        datos.get("id00").put("at08", 0.05);

        datos.get("id01").put("at01", 0.03);
        datos.get("id01").put("at03", 0.001);
        datos.get("id01").put("at05", 0.09);
        datos.get("id01").put("at07", 0.05);

        // Verificación inicial de si las sumas de los valores son iguales a 1
        System.out.println(check("id00")); // Resultado esperado: false
        System.out.println(check("id01")); // Resultado esperado: false

        // Reparación de las sumas para que sean iguales a 1
        repare("id00");
        repare("id01");

        // Verificación posterior a la reparación
        System.out.println(check("id00")); // Resultado esperado: true
        System.out.println(check("id01")); // Resultado esperado: true

        // Cálculo de la distancia entre los valores de dos claves
        System.out.println(distancia("id00", "id01"));
	}
}
