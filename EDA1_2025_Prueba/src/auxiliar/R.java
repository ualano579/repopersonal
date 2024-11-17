package auxiliar;

import java.util.Map.Entry;
import java.util.TreeMap;

public class R {
	TreeMap <String, TreeMap <String, Double>> datos;
	
	public R(){
		datos = new TreeMap<>();
	}
	
	public boolean check (String id) {
		int n = 0;
		TreeMap<String, Double> aux = this.datos.get(id);
		if (aux == null) return false;
		for (Double i : aux.values()) {
			n += i;
		}
		System.out.println(n);
		return n == 1;
	}
	
	public void repare(String id) {
		if(check(id)) return;
		TreeMap<String, Double> aux = this.datos.get(id);
		if (aux == null) return;
		int n = 0;
		for (Double i : aux.values()) {
			n += i;
		}
		for (Entry<String, Double> par : aux.entrySet()) {
			aux.put(par.getKey(), par.getValue()/n);
		}
	}
	
	public void distancia(String idOrigen, String idDestino) {
		TreeMap<String, Double> aux = this.datos.get(idOrigen);
		if (aux == null) return;
		TreeMap<String, Double> aux2 = this.datos.get(idDestino);
		if (aux2 == null) return;
		
	}
//	public double distancia(String idOrigen, String idDestino) {
//	    // Verificar si ambos nodos existen
//	    if (!datos.containsKey(idOrigen) || !datos.containsKey(idDestino)) {
//	        throw new IllegalArgumentException("Uno de los nodos no existe");
//	    }
//
//	    // Inicializar estructuras
//	    PriorityQueue<Entry<String, Double>> cola = new PriorityQueue<>(Map.Entry.comparingByValue());
//	    Map<String, Double> distancias = new TreeMap<>();
//	    Set<String> visitados = new HashSet<>();
//
//	    // Inicializar la distancia del nodo origen como 0 y el resto como infinito
//	    for (String nodo : datos.keySet()) {
//	        distancias.put(nodo, Double.POSITIVE_INFINITY);
//	    }
//	    distancias.put(idOrigen, 0.0);
//	    cola.add(new AbstractMap.SimpleEntry<>(idOrigen, 0.0));
//
//	    // Algoritmo de Dijkstra
//	    while (!cola.isEmpty()) {
//	        Entry<String, Double> actual = cola.poll();
//	        String nodoActual = actual.getKey();
//	        double distanciaActual = actual.getValue();
//
//	        if (visitados.contains(nodoActual)) continue;
//	        visitados.add(nodoActual);
//
//	        // Explorar vecinos del nodo actual
//	        for (Map.Entry<String, Double> vecino : datos.get(nodoActual).entrySet()) {
//	            String nodoVecino = vecino.getKey();
//	            double pesoArista = vecino.getValue();
//
//	            // Relajación
//	            if (distanciaActual + pesoArista < distancias.get(nodoVecino)) {
//	                distancias.put(nodoVecino, distanciaActual + pesoArista);
//	                cola.add(new AbstractMap.SimpleEntry<>(nodoVecino, distancias.get(nodoVecino)));
//	            }
//	        }
//	    }
//
//	    // Retornar la distancia al nodo destino
//	    return distancias.getOrDefault(idDestino, Double.POSITIVE_INFINITY);
//	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		R r = new R();
		
		TreeMap <String, Double> x1 = new TreeMap<>();
		TreeMap <String, Double> x2 = new TreeMap<>();
		TreeMap <String, Double> x3 = new TreeMap<>();
		x1.put("atrib00", 0.08);
		x1.put("atrib01", 0.1);
		x1.put("atrib03", 0.28);
		r.datos.put("id00", x1);
		
		System.out.println(r.datos);
		System.out.println(r.check("id00"));
		r.repare("id00");
		System.out.println(r.datos);
	}
}
