/*
 * Graph.java
 */

package practicas.auxiliar;

import java.util.TreeSet;

/**
 * An interface that defines access and update methods for the vertices
 * and edges in a direct weighted graph.
 */

public interface Graph<T> {
	
	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph.
	 */
	int numberOfVertices();

	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph.
	 */
	int numberOfEdges();

	/**
	 * Returns <code>true</code> if this graph has vertices or edges.
	 * @return <code>false</code> if this graph has no vertices or edges.
	 */
	boolean isEmpty();

	/**
	 * Returns the weight of the edge connecting vertex v1 to v2.
	 * If the edge (v1,v2) does not exist, return -1.
	 * @param v1 source vertex of the edge.
	 * @param v2 destination vertex of the edge.
	 * @return the weight of the edge connecting vertex v1 to v2 or
	 *         <code>-1</code> if it does not exist.
	 * @throws IllegalArgumentException if v1 or v2 is not a vertex in this graph.
	 */
	double getWeight(T v1, T v2);

	/**
	 * If edge (v1, v2) is in the graph, update the weight of
	 * the edge and return the previous weight; otherwise, return
	 * <code>null</code>.
	 * @param v1 source vertex of the edge.
	 * @param v2 destination vertex of the edge.
	 * @param w weight assigned to the edge.
	 * @return the original weight of the edge or <code>-1</code> if the edge does not exist.
	 * @throws IllegalArgumentException if v1 or v2 is not a vertex in this graph.
	 */
	Double setWeight(T v1, T v2, double w);

	/**
	 * Returns the vertices that are adjacent to vertex v in a
	 * <code>Set</code> object.
	 * @param v vertex in the graph.
	 * @return the set of vertices that are adjacent to vertex v.
	 * @throws IllegalArgumentException if v is not a vertex in this graph.
	 */
	TreeSet<T> getNeighbors(T v);

	/**
	 * If edge (v1, v2) is not in the graph, add the edge with weight
	 * w and return <code>true</code>; return <code>false</code> if the edge
	 * is already in the graph.
	 * @param v1 source vertex of the new edge.
	 * @param v2 destination vertex of the new edge.
	 * @param w weight assigned to the edge.
	 * @return <code>true</code> if a new edge is added.
	 * @throws IllegalArgumentException if v1 or v2 is not a vertex in this graph.
	 */
	boolean addEdge(T v1, T v2, double w);

	/**
	 * If vertex v is not in the graph, add it to the graph and return
	 * <code>true</code>; otherwise return <code>false</code>.
	 * @param v vertex in the graph.
	 * @return <code>true</code> if a new vertex is added.
	 */
	boolean addVertex(T v);

	/**
	 * If edge (v1, v2) is in the graph, remove the edge and return
	 * <code>true</code>; otherwise return <code>false</code>
	 * @param v1 source vertex of the edge.
	 * @param v2 destination vertex of the edge.
	 * @return <code>true</code> if an edge is removed.
	 * @throws IllegalArgumentException if v1 or v2 is not a vertex in this graph.
	 */
	boolean removeEdge(T v1, T v2);

	/**
	 * If vertex v is in the graph, remove it and return
	 * <code>true</code>; otherwise return <code>false</code>.
	 * @param v vertex in the graph.
	 * @return <code>true</code> if a vertex is removed.
	 */
   boolean removeVertex(T v);

	/**
	 * Removes all the vertices and edges from the graph.
	 */
	void clear();

    /**
     * Returns a set view of the vertices in this graph.  The set is
     * backed by the graph, so changes to the graph are reflected in the set, and
     * vice-versa.  If the map is modified while an iteration over the set is
     * in progress
     *
     * @return a set view of the vertices in this graph.
     */
	TreeSet<T> vertexSet();

	/**
	 * Returns <code>true</code> if v is a vertex in this graph and
	 * <code>false</code> otherwise.
	 * @param v vertex in the graph.
	 * @return <code>true</code> if v is a vertex in this graph.
	 */
	boolean containsVertex(T v);

	/**
	 * Returns <code>true</code> if there is an edge from v1 to v2 and
	 * <code>false</code> otherwise.
	 * @param v1 source vertex of the edge.
	 * @param v2 destination vertex of the edge.
	 * @return <code>true</code> if there is an edge from v1 to v2.
	 * @throws IllegalArgumentException if v1 or v2 is not a vertex in this graph.
	 */
	boolean containsEdge(T v1, T v2);
}
