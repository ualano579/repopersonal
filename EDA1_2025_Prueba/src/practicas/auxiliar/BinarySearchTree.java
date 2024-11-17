package practicas.auxiliar;

import java.util.Iterator;

public interface BinarySearchTree<T> {

	/**
	 * Adds the specified item to this tree if it is not already present.
	 *
	 * @param item element to be added to this tree.
	 * @return <code>true</code> if the tree did not already contain the specified
	 *         element.
	 */
	boolean add(T item);

	/**
	 * Removes all the elements from this tree. The resulting tree is empty
	 * after the method executes.
	 */
	void clear();

	/**
	 * Returns <code>true</code> if this tree contains the specified element.
	 *
	 * @param item the object to be checked for containment in this tree.
	 * @return <code>true</code> if this tree contains the specified element.
	 */
	boolean contains(T item);

	/**
	 * Returns <code>true</code> if this tree contains no elements.
	 *
	 * @return <code>true</code> if this tree contains no elements.
	 */
	boolean isEmpty();

	/**
	 * Returns an iterator over the elements in this tree.  The elements
	 * are returned in ascending order.
	 *
	 * @return an iterator over the elements in this tree.
	 */
	Iterator<T> iterator();

	/**
	 * Removes the specified item from this tree if it is present.
	 *
	 * @param item object to be removed from this tree, if present.
	 * @return <code>true</code> if the tree contained the specified element.
	 */
	boolean remove(T item);

	/**
	 * Returns the number of elements in this tree.
	 *
	 * @return the number of elements in this tree.
	 */
	int size();
	
	/**
	 * Searches for the specified item in the tree and returns
	 * the value of the node that matches item as a key.
	 *
	 * @param   item   serves as a key to locate an element in the tree.
	 * @return  the value of the node that corresponds to item as a key
	 *          or <code>null</code> if the element is not found.
	 */
	T find(T item);
}