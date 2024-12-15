package practicas.practica04;

import java.util.TreeMap;
import java.util.TreeSet;

import practicas.auxiliar.Graph;
import practicas.auxiliar.Par;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Network<Vertex extends Comparable<Vertex>> implements Graph<Vertex> {

	private boolean directed; 	// directed = false (unDirected), directed = true (DiGraph)
	
	protected HashMap<Vertex, HashMap<Vertex, Double>> adjacencyList;
	
	public Network(){
		this.directed = true;
		this.adjacencyList = new HashMap<>();
	}

  	public void setDirected(boolean esDirected) {
  		this.directed = esDirected;
  	}

  	public boolean isDirected() {
  		return this.directed;
  	}

  	@Override
  	public boolean isEmpty() {
    	return this.adjacencyList.isEmpty();
  	} 

  	@Override
  	public void clear() {
		this.adjacencyList.clear();
	}

  	@Override
  	public int numberOfVertices() {
    	return this.adjacencyList.size();
  	} 

  	@Override
  	public int numberOfEdges() {
  		int count = 0;
  		for (HashMap<Vertex, Double> mapValues : this.adjacencyList.values())
  			count += mapValues.size();
  		return count;
  	} 

  	@Override
  	public boolean containsVertex(Vertex vertex) {
    	return this.adjacencyList.containsKey(vertex);
  	} 
  	
  	@Override
  	public boolean containsEdge(Vertex v1, Vertex v2) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		return neighbors == null ? false : neighbors.containsKey(v2);
  	} 

  	@Override
  	public double getWeight (Vertex v1, Vertex v2) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		if (neighbors == null) return -1;
  		Double weight = neighbors.get(v2);
  		return weight == null ? -1 : weight;
   	} 

  	@Override
  	public Double setWeight (Vertex v1, Vertex v2, double w) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		if (neighbors == null) return -1.;
		return neighbors.put(v2, w);
	}

  	public boolean isAdjacent (Vertex v1, Vertex v2) {
  		HashMap<Vertex, Double> neighbors = this.adjacencyList.get(v1);
  		return neighbors != null && neighbors.containsKey(v2);
	}

  	public boolean addVertex(Vertex vertex) {
        if (this.adjacencyList.containsKey(vertex)) return false;
        this.adjacencyList.put(vertex, new HashMap<>());
        return true;
  	} 

  	public boolean addEdge(Vertex v1, Vertex v2, double w) {
  		if (!containsVertex(v1) || !containsVertex(v2)) return false;
  		this.adjacencyList.get(v1).put(v2, w);
       	if (!this.directed) {
        	this.adjacencyList.get(v2).put(v1, w);
       	}
    	return true;
  	} 

  	public boolean removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) return false;

        for (HashMap<Vertex, Double> itMap: this.adjacencyList.values()) {
        	itMap.remove(vertex);
        } 
        this.adjacencyList.remove(vertex);
        return true;
   	} 

  	public boolean removeEdge (Vertex v1, Vertex v2) {
    	if (!containsEdge(v1,v2)) return false;

    	this.adjacencyList.get(v1).remove(v2);
    	
    	if (!this.directed) {
        	this.adjacencyList.get(v2).remove(v1);
    	}
    	
    	return true;
  	} 
  	
	@Override
  	public TreeSet<Vertex> vertexSet() {
		//1 única línea (1 new y 1 keySet())
    	return new TreeSet<>(this.adjacencyList.keySet());
  	}

  	/**
  	 *  Returns a TreeSet<T> object reference of the neighbors of a specified Vertex object.
  	 *
  	 *  @param v - the Vertex object whose neighbors are returned.
  	 *
  	 *  @return a TreeSet<T> of the vertices that are neighbors of v.
   	 */

  	public TreeSet<Vertex> getNeighbors(Vertex v) {
  		//2 líneas
  		//1 get() 1 new  y 1 keySet()
  		HashMap<Vertex, Double> aux = adjacencyList.get(v);
  		return aux == null ? null : new TreeSet<>(aux.keySet());
  	}

  	@Override
  	public String toString() {
  		TreeMap<Vertex, TreeMap<Vertex, Double>> resultado = new TreeMap<>();
  		//1 for()
  		for (Entry<Vertex, HashMap<Vertex, Double>> par : adjacencyList.entrySet()) {
  			resultado.put(par.getKey(), new TreeMap<>(par.getValue()));
		}
    	return resultado.toString();
  	} 

  	/////////Dijkstra
	
	private TreeMap<Vertex, Par<Vertex, Double>> Dijkstra(Vertex source, Vertex destination) {
  		final double INFINITY = Double.MAX_VALUE;
      	TreeMap<Vertex, Par<Vertex, Double>> DS = new TreeMap<>();    	
    	TreeSet<Vertex> V_minus_S = new TreeSet<>();
     	
    	for (Vertex e : this.adjacencyList.keySet()) {
    		if (source.equals(e)) continue;
    		V_minus_S.add(e);
    	}
    	
    	for (Vertex v : V_minus_S){
    		DS.put(v, new Par<>(isAdjacent(source,v) ? source: null, isAdjacent(source,v) ? getWeight(source, v) : INFINITY));
    	}
    	
		DS.put(source,  new Par<>(source, 0.0));
		while (!V_minus_S.isEmpty()) {
		    double minWeight = INFINITY;
		    Vertex from = null;
		    
		    for (Vertex v : V_minus_S){
		    	if (DS.get(v).getValue() < minWeight) {
		    		minWeight = DS.get(v).getValue();
		    		from = v;
		    	}
		    }
	    	if (from == null) break;
	    	
			V_minus_S.remove(from);
				
		    for (Vertex v : V_minus_S){
		    	if (isAdjacent(from,v)){
		    		if (DS.get(from).getValue() + getWeight(from,v) < DS.get(v).getValue()){
		    			DS.put(v, new Par<>(from, DS.get(from).getValue() + getWeight(from,v)));
		    		}
		    	}
		    }
		}
		if (DS.get(destination) == null) {
			throw new RuntimeException("The vertex " + destination + " is not reachable from " + source);
		}
		return DS;
	}
	
	public ArrayList<Par<Vertex, Double>> getDijkstra(Vertex source, Vertex destination) {
		ArrayList<Par<Vertex, Double>> path = null;
    	Stack<Vertex> pila = null;
		TreeMap<Vertex, Par<Vertex, Double>> salidaDijkstra = null;
		
		if (source == null || destination == null) return null;
    	
    	if (source.equals(destination))	return null;
    	
    	//*Aquí debe ir más código ya que debemos encontrar las referencias a los objetos "current" de source y destination
    	//TODO
    	source = new TreeMap<>(this.adjacencyList).ceilingKey(source);
    	destination = new TreeMap<>(this.adjacencyList).ceilingKey(destination);
    	
    	try {
    		salidaDijkstra = Dijkstra(source, destination);	
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
    	path = new ArrayList<>();
    	pila = new Stack<>();
    	
		pila.push(destination);
		while (!pila.peek().equals(source)) {
			pila.push(salidaDijkstra.get(pila.peek()).getKey());
		}
		while (!pila.isEmpty()) {
			Vertex current = pila.pop();
			path.add(new Par<>(current, salidaDijkstra.get(current).getValue()));
		}
		return path;
	}
	
	
  	//toArray() methods
	
	//Depth First (en profundidad)
	//Breadth First (en anchura)
	//Ambos códigos son iguales y solo cambian enq una tiene una pila(DepthFirst) y la otra una cola(BreadthFirst)
	
	//A la hora de modificar este método ten en cuenta que no vas a añadir ni quitar ni una sola línea de código
	//Simplemente analiza el método y determina en qué lugar se ha de añadir el criterio de orden que exige el test
	public ArrayList<Vertex> toArrayDepthFirst(Vertex start) {
		if (!this.adjacencyList.containsKey(start)) return null;
		ArrayList<Vertex> resultado = new ArrayList<>();
		Stack<Vertex> stack = new Stack<>();
		HashMap<Vertex, Boolean> visited = new  HashMap<>();
		for (Vertex vertex : this.adjacencyList.keySet()) {
			visited.put(vertex, false);
		}
	
		stack.push(start);
		while (!stack.isEmpty()) {
			Vertex current = stack.pop();
			if (visited.get(current)) continue;
			visited.put(current, true);
			resultado.add(current);
			for (Vertex to : new TreeMap<>(this.adjacencyList.get(current)).keySet()) {
    			if (visited.get(to)) continue;
				stack.push(to);
			}
		}
		return resultado;
	}
	
	//A la hora de modificar este método ten en cuenta que no vas a añadir ni quitar ni una sola línea de código
	//Simplemente analiza el método y determina en qué lugar se ha de añadir el criterio de orden que exige el test
	public ArrayList<Vertex> toArrayBreadthFirst(Vertex start) {
		if (!this.adjacencyList.containsKey(start)) return null;
		ArrayList<Vertex> resultado = new ArrayList<>();
		LinkedList<Vertex> queue = new LinkedList<>();
		TreeMap<Vertex, Boolean> visited = new TreeMap<>();
		for (Vertex vertex: this.adjacencyList.keySet()) {
			visited.put(vertex, false);
		}
		queue.add(start);
		while (!queue.isEmpty()) {
			Vertex current = queue.poll();
			if (visited.get(current)) continue;
			visited.put(current, true);
			resultado.add(current);
        	for (Vertex to : new TreeMap<>(this.adjacencyList.get(current)).keySet()) {
    			if (visited.get(to)) continue;
				queue.add(to);
        	}
		}
		return resultado;
	}
 } 