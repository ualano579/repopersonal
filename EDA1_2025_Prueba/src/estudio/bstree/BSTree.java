package estudio.bstree;

import java.util.*;

import practicas.auxiliar.BinarySearchTree;

/**
 * This class implements the Collection interface using a binary search tree as
 * the underlying storage structure.
 */

public class BSTree<T extends Comparable<T>> implements Iterable<T>, BinarySearchTree<T> {
	private static class BSTNode<T> {
		private T nodeValue;
		private BSTNode<T> left;
		private BSTNode<T> right;
		private BSTNode<T> parent;

		public BSTNode(T item, BSTNode<T> parentNode) {
			this.nodeValue = item;
			this.left = null;
			this.right = null;
			this.parent = parentNode;
		}
	}

	private BSTNode<T> root;
	private int treeSize;
	transient private int modCount;

	public BSTree() {
		this.root = null;
		this.modCount = 0;
		this.treeSize = 0;
	}

	private void removeNode(BSTNode<T> current) {
		if (current == null)
			return;
		BSTNode<T> pNode, rNode;
		pNode = current.parent;

		if (current.left == null || current.right == null) {
			if (current.right == null) {
				rNode = current.left;
			} else {
				rNode = current.right;
			}
			if (rNode != null) {
				rNode.parent = pNode;
			}

			if (pNode == null) {
				root = rNode;
			} else if (current.nodeValue.compareTo(pNode.nodeValue) < 0) {
				pNode.left = rNode;
			} else {
				pNode.right = rNode;
			}
		} else {
			BSTNode<T> pOfRNode = current;
			rNode = current.right;

			while (rNode.left != null) {
				pOfRNode = rNode;
				rNode = rNode.left;
			}
			current.nodeValue = rNode.nodeValue;
			if (pOfRNode == current) {
				current.right = rNode.right;
			} else {
				pOfRNode.left = rNode.right;
			}
			if (rNode.right != null) {
				rNode.right.parent = pOfRNode;
			}
			current = rNode;
		}
		current = null;
	}

	private BSTNode<T> findNode(T item) {
		BSTNode<T> current = this.root;
		while (current != null) {
			int cmp = item.compareTo(current.nodeValue);
			if (cmp == 0)
				break;
			current = (cmp < 0) ? current.left : current.right;
		}
		return current;
	}

	public String toStringBreadthFirstTraversal() {
		Deque<BSTNode<T>> queue = new ArrayDeque<>();
		BSTNode<T> current = this.root;
		String s = "";
		if (current == null)
			return " ";
		queue.offer(current);
		while (!queue.isEmpty()) {
			current = queue.poll();
			s += current.nodeValue + "  ";
			if (current.left != null)
				queue.offer(current.left);
			if (current.right != null)
				queue.offer(current.right);
		}
		return s;
	}

	public String toStringIterativePreorder() {
		Deque<BSTNode<T>> stack = new ArrayDeque<>();
		BSTNode<T> p;
		String s = "";
		if (root == null)
			return " ";
		stack.push(root);
		while (!stack.isEmpty()) {
			p = stack.pop();
			s += p.nodeValue + "  ";
			if (p.right != null)
				stack.push(p.right);
			if (p.left != null)
				stack.push(p.left);
		}
		return s;
	}

	@Override
	public boolean add(T item) {
		BSTNode<T> current = root, parent = null, newNode;
		int orderValue = 0;
		while (current != null) {
			parent = current;
			orderValue = item.compareTo(current.nodeValue);
			if (orderValue == 0)
				return false;
			current = (orderValue < 0) ? current.left : current.right;
		}
		newNode = new BSTNode<>(item, parent);

		if (parent == null) {
			this.root = newNode;
		} else if (orderValue < 0) {
			parent.left = newNode;
		} else {
			parent.right = newNode;
		}
		this.treeSize++;
		this.modCount++;
		return true;
	}

	@Override
	public void clear() {
		this.modCount++;
		this.treeSize = 0;
		this.root = null;
	}

	@Override
	public boolean isEmpty() {
		return this.treeSize == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new TreeIterator();
	}

	@Override
	public boolean remove(T item) {
		BSTNode<T> dNode = findNode(item);

		if (dNode == null)
			return false;
		removeNode(dNode);
		this.treeSize--;
		this.modCount++;
		return true;
	}

	@Override
	public int size() {
		return this.treeSize;
	}

	public Object[] toArray() {
		ArrayList<T> arr = new ArrayList<>();
		Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			arr.add(iter.next());
		}
		return arr.toArray();
	}

	@Override
	public String toString() {
		String returnStr = "[";
		Iterator<T> iter = this.iterator();
		for (int i = 0; i < treeSize; i++) {
			returnStr += iter.next();
			if (i < treeSize - 1)
				returnStr += ", ";
		}
		return returnStr + "]";
	}

	@Override
	public T find(T item) {
		BSTNode<T> t = findNode(item);
		return t != null ? t.nodeValue : null;
	}

	public BSTree<T> clone() {
		BSTree<T> copy = new BSTree<>();
		copy.modCount = this.modCount;
		copy.treeSize = this.treeSize;
		copy.root = copyTree(this.root);
		return copy;
	}

	private BSTNode<T> copyTree(BSTNode<T> t) {
		BSTNode<T> newLeft, newRight, newNode;

		if (t == null) return null;
		newLeft = copyTree(t.left);
		newRight = copyTree(t.right);

		newNode = new BSTNode<>(t.nodeValue, null);
		newNode.left = newLeft;
		newNode.right = newRight;
		if (newLeft != null)  newLeft.parent = newNode;
		if (newRight != null) newRight.parent = newNode;
		return newNode;
	}

	private String preorderDisplay(BSTNode<T> current) {
		String s = "";
		if (current == null) return "";
		s += current.nodeValue + "  ";
		s += preorderDisplay(current.left);
		s += preorderDisplay(current.right);
		return s;
	}

	public String preorderDisplay() {
		return preorderDisplay(root);
	}

	@Override
	public boolean contains(T o) {
		return this.find(o) != null;
	}

	private class TreeIterator implements Iterator<T> {
		private int expectedModCount = modCount;
		private BSTNode<T> lastReturned = null;
		private BSTNode<T> nextNode;

		TreeIterator() {
			nextNode = root;
			if (nextNode != null) {
				while (nextNode.left != null) {
					nextNode = nextNode.left;
				}
			}
		}

		public boolean hasNext() {
			return nextNode != null;
		}

		public T next() {
			checkIteratorState();
			if (nextNode == null)
				throw new NoSuchElementException("Iteration has no more elements");
			lastReturned = nextNode;
			BSTNode<T> p;

			if (nextNode.right != null) {
				nextNode = nextNode.right;
				while (nextNode.left != null)
					nextNode = nextNode.left;
			} else {
				p = nextNode.parent;
				while (p != null && nextNode == p.right) {
					nextNode = p;
					p = p.parent;
				}
				nextNode = p;
			}
			return lastReturned.nodeValue;
		}

		public void remove() {
			if (lastReturned == null)
				throw new IllegalStateException("Iterator call to next() " + "required before calling remove()");
			checkIteratorState();
			if (lastReturned.left != null && lastReturned.right != null)
				nextNode = lastReturned;
			removeNode(lastReturned);
			modCount++;
			expectedModCount = modCount;

			lastReturned = null;
			treeSize--;
		}

		private void checkIteratorState() {
			if (expectedModCount != modCount)
				throw new ConcurrentModificationException("Inconsistent iterator");
		}
	}
	
	///METODOS ADICIONALES DE ESTUDIO///
	public String displayTree() {
		String s = displayTree(root, 0);
		return s;
	}
	
	private String displayTree(BSTNode<T> t, int level) {
		String s = "";
		if (t != null) {
			if (t.right != null) s += displayTree(t.right, level + 1);
			for (int i = 0; i < level; i++) {
				s += "     ";
			}
			s += "(" + t.nodeValue.toString() + ")[" + level + "]\n";
			if (t.left != null) s += displayTree(t.left, level + 1);
		}
		return s;
	}
	
	public String displayTreeAsterisk(int nivel) {
		return displayTreeAsterisk(root, 0, nivel);
	}
	
	/**
	 * Display the tree with an asterisk in the node of the specified level.
	 * 
	 * @param t     the root of the tree.
	 * @param level the level of the current node.
	 * @param nivel the level of the node to display with an asterisk.
	 * @return a string representation of the tree with an asterisk in the node of
	 *         the specified level.
	 */
	private String displayTreeAsterisk(BSTNode<T> t, int level, int nivel) {
		String s = "";
		//TODO: Igual que el método displayTree, pero si el nivel es igual al nivel especificado, 
		//se debe mostrar un asterisco en lugar del valor del nodo.
		if (t != null) {
			if (t.right != null) s += displayTreeAsterisk(t.right, level + 1, nivel);
			for (int i = 0; i < level; i++) {
				s += "     ";
			}
			s += "(" + (level == nivel ? "*" : t.nodeValue.toString())  + ")[" + level + "]\n";
			if (t.left != null) s += displayTreeAsterisk(t.left, level + 1, nivel);
		}
		return s;
	}
	
	public int pathHeightIterative(T item) {
		int nivel = 0;
		int comparacion = 0;
		BSTNode<T> node = this.root;
		while (node != null) {
			comparacion = ((Comparable<T>) node.nodeValue).compareTo(item);
			if (comparacion == 0) {
				return nivel;
			} else if (comparacion > 0) {
				node = node.left;
			} else {
				node = node.right;
			}
			nivel++;
		}
		return -1;
	}
	
	public int pathHeightRecursive(T t) {
		return pathHeightRecursive(this.root, t, 0);
	}
	
	private int pathHeightRecursive(BSTNode<T> nodo, T t, int nivel) {
		//TODO: Implementar un método recursivo que devuelva la altura del camino 
		// desde la raíz hasta el nodo con el valor t.
		if(nodo != null) {
			int comparacion = (nodo.nodeValue).compareTo(t);
			if(comparacion == 0) {
				return nivel;
			}else if(comparacion > 0) {
				return pathHeightRecursive(nodo.left, t, nivel + 1);
			}else {
				return pathHeightRecursive(nodo.right, t, nivel + 1);
			}
		}
		return -1;
	}
	
	public String toStringLevel(int level) {
		return toStringLevel(this.root, level);
	}
	
	private String toStringLevel(BSTNode<T> t, int level) {
		String s = "";
		//TODO: Implementar un método que devuelva una cadena con los valores de los nodos en el nivel especificado.
		if(t== null || level < 0) return s;
		
		if(level == 0) return t.nodeValue + " ";
		s += toStringLevel(t.left, level - 1);
		s += toStringLevel(t.right, level - 1);
		return s;
	}
	
	public int countNodes() {
		return countNodes(this.root);
	}

	private int countNodes(BSTNode<T> t) {
		//TODO: Implementar un método que devuelva la cantidad de nodos en el árbol.
		if(t == null) return 0;
		if(t.left == null && t.right == null) return 1;
		return countNodes(t.left) + countNodes(t.right) + 1;
	}

	public int countNodesOfLevel(int level) {
		return countNodesOfLevel(this.root, level);
	}

	private int countNodesOfLevel(BSTNode<T> t, int level) {
		//TODO: Implementar un método que devuelva la cantidad de nodos en el nivel especificado.
		if(t == null) return 0;
		if(level == 0) return 1;
		return countNodesOfLevel(t.left, level - 1) + countNodesOfLevel(t.right, level - 1);
	}
	
	public int height() {
		return height(this.root);
	}

	private int height(BSTNode<T> t) {
		//TODO: Implementar un método que devuelva la altura del árbol.
		if(t == null) return -1;
		//Si h= 0 --> 1 + Max(-1(t.left == null), -1(t.right))
		return 1 + Math.max(height(t.left), height(t.right));
	}
	
	public int numberOfLeaves() {
		return numberOfLeaves(this.root);
	}

	private int numberOfLeaves(BSTNode<T> t) {
		if (t == null) return 0;
		if ((t.right == null) && (t.left == null)) return 1;
		return numberOfLeaves(t.left) + numberOfLeaves(t.right);
	}
	
	public T findMin() {
		if(this.root == null) return null;
		return findMin(this.root).nodeValue;
	}

	private BSTNode<T> findMin(BSTNode<T> t) {
		//TODO: Implementar un método que devuelva el valor mínimo del árbol.
		if(t == null) return null;
		if(t.left == null) return t;
		return findMin(t.left);
	}
	
	public T findMax() {
		if(this.root == null) return null;
		return findMax(this.root).nodeValue;
	}

	private BSTNode<T> findMax(BSTNode<T> t) {
		//TODO: Implementar un método que devuelva el valor máximo del árbol.
		if(t == null) return null;
		if(t.right == null) return t;
		return findMax(t.right);
	}
	
	public void removeLeaves() {
		if (root != null) {
			root = removeLeaves(this.root);
		}
	}

	private BSTNode<T> removeLeaves(BSTNode<T> t) {
		if (t.left == null && t.right == null) {
			treeSize--;
			return null;
		} else {
			if (t.left != null) t.left = removeLeaves(t.left);
			if (t.right != null) t.right = removeLeaves(t.right);
			return t;
		}
	}
	
	public int findLevel(T x) {
		return findLevel(this.root, x, 0);
	}

	private int findLevel(BSTNode<T> t, T x, int level) {
		//TODO: Implementar un método que devuelva el nivel del nodo con el valor x. Si no está presente, debe devolver -1.
		if(t == null) return -1;
		int cmp = t.nodeValue.compareTo(x);
		if(cmp == 0) {
			return level;
		}else if (cmp < 0) {
			return findLevel(t.right, x, level + 1);
		}else {
			return findLevel(t.left, x, level + 1);
		}
		
	}
	
	public int numberOfIntermediateNodes() {
		return numberOfIntermediateNodes(this.root);
	}

	private int numberOfIntermediateNodes(BSTNode<T> t) {
		//TODO: Implementar un método que devuelva la cantidad de nodos intermedios en el árbol.
		if(t == null) return 0;
		if(t.left == null && t.right == null) return 0;
		return 1 + numberOfIntermediateNodes(t.left) + numberOfIntermediateNodes(t.right);
	}

	public int numberOfNodesWithTwoChildren() {
		return numberOfNodesWithTwoChildren(this.root);
	}

	private int numberOfNodesWithTwoChildren(BSTNode<T> t) {
		//TODO: Implementar un método que devuelva la cantidad de nodos con exactamente dos hijos en el árbol.
		if(t == null) return 0;
		if(t.left == null || t.right == null) return 0;
		return 1 + numberOfNodesWithTwoChildren(t.left) + numberOfNodesWithTwoChildren(t.right);
	}

	public int numberOfNodesWithOneChild() {
		return numberOfNodesWithOneChild(this.root);
	}

	private int numberOfNodesWithOneChild(BSTNode<T> t) {
		//TODO: Implementar un método que devuelva la cantidad de nodos con exactamente un hijo en el árbol.
		if(t == null) return 0;
		if(t.left == null && t.right == null) return 0;
		if(t.left != null && t.right != null) return numberOfNodesWithOneChild(t.left) + numberOfNodesWithOneChild(t.right);
		return 1 + numberOfNodesWithOneChild(t.left) + numberOfNodesWithOneChild(t.right);
	}
	
	public void removeMin() {
		root = removeMin(root);
	}

	private BSTNode<T> removeMin(BSTNode<T> curr) {
		if (curr == null) return null;
		if (curr.left == null) {
			treeSize--;
			return curr.right;
		}
		curr.left = removeMin(curr.left);
		return curr;
	}

	public void removeMax() {
		root = removeMax(root);
	}

	private BSTNode<T> removeMax(BSTNode<T> curr) {
		if (curr == null) return null;
		if (curr.right == null) {
			treeSize--;
			return curr.left;
		}
		curr.right = removeMax(curr.right);
		return curr;
	}
	
	public int nodeGrade(T x) {
		BSTNode<T> t = findNode(x);
		if (t == null) {
			return -1;
		}
		int grado = 0;
		if (t.left != null) grado++;
		if (t.right != null) grado++;
		return grado;
	}
}