package practicas.practica04;

import java.util.Iterator;

public class NetworkIterator<Vertex extends Comparable<Vertex>> implements Iterable<Vertex> {
	private enum iterateModes {Normal, Breadth, Depth};
	private Iterator<Vertex> result;

	public NetworkIterator(Network<Vertex> red, Vertex start, iterateModes mode) {
		switch(mode) {
			case Normal:{
							result = red.vertexSet().iterator(); 
							break;
			}
			case Breadth:{
							result = red.toArrayBreadthFirst(start).iterator(); 
							break;
			}
			case Depth:{ 
							result = red.toArrayDepthFirst(start).iterator(); 
							break;
			}
		}
	}
	
	public static <Vertex extends Comparable<Vertex>> NetworkIterator<Vertex> normalIterator(Network<Vertex> red) {
		return new NetworkIterator<>(red, null, iterateModes.Normal); 
	}
	
	public static <Vertex extends Comparable<Vertex>> NetworkIterator<Vertex> depthFirstIterator(Network<Vertex> red, Vertex start) {
		return new NetworkIterator<>(red, start, iterateModes.Depth); 
	}

	public static <Vertex extends Comparable<Vertex>> NetworkIterator<Vertex> breadthFirstIterator(Network<Vertex> red, Vertex start) {
		return new NetworkIterator<>(red, start, iterateModes.Breadth); 
	}

	@Override
	public Iterator<Vertex> iterator() {
		return result;
	}
}
