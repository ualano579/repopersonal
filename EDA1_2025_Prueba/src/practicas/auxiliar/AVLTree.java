package practicas.auxiliar;

import java.util.*;

/**
 * This class is a balanced binary tree that implements the Collection interface
 * AVL tree single and double rotation methods.
 */

public class AVLTree<T extends Comparable<T>> implements Iterable<T>, BinarySearchTree<T> {
	
	private static class AVLNode<T> {
		private T nodeValue;
		private AVLNode<T> left, right;
		private int height;

		public AVLNode(T item) {
			nodeValue = item;
			left = null;
			right = null;
			height = 0;
		}
	}
	
	private AVLNode<T> root;
	private int treeSize;
	private int modCount;

	
	public AVLTree() {
		this.root = null;
		this.modCount = 0;
		this.treeSize = 0;
	}
	
	public AVLTree(T[] arr) {
		for (T v : arr) {
			this.add(v);
		}
	}

	private AVLNode<T> findNode(T item) {
		AVLNode<T> current = this.root;
		while (current != null) {
			int cmp = item.compareTo(current.nodeValue);
			if (cmp == 0) return current;
			current = (cmp < 0) ? current.left : current.right;
		}
		return null;
	}

	public boolean add(T item) {
		try {
			this.root = addNode(this.root, item);
		} catch (IllegalStateException ise) {
			return false;
		}
		this.treeSize++;
		this.modCount++;
		return true;
	}

	private AVLNode<T> addNode(AVLNode<T> current, T item) {
		if (current == null) {
			return new AVLNode<>(item);
		}
		int cmp = item.compareTo(current.nodeValue);
		if (cmp < 0) {
			current.left = addNode(current.left, item);
			if (height(current.left) - height(current.right) == 2) {
				current = (item.compareTo(current.left.nodeValue) < 0) ? singleRotateRight(current) : doubleRotateRight(current);
			}
			current.height = Math.max(height(current.left), height(current.right)) + 1;
			return current;
		}
		if (cmp > 0) {
			current.right = addNode(current.right, item);
			if (height(current.left) - height(current.right) == -2) {
				current = (item.compareTo(current.right.nodeValue) > 0) ? singleRotateLeft(current) : doubleRotateLeft(current);
			}
			current.height = Math.max(height(current.left), height(current.right)) + 1;
			return current;
		}
		throw new IllegalStateException(); //item duplicated
	}

	public boolean remove(T item) {
		if (item == null) return false;
		try {
			this.root = remove(this.root, item);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	private AVLNode<T> remove(AVLNode<T> current, T item) {

		if (current == null)  throw new IllegalArgumentException("Element " + item + " is not present.");
		int cmp = item.compareTo(current.nodeValue);

		if (cmp < 0) {
			current.left = remove(current.left, item);
			if (height(current.right) - height(current.left) == 2) {
				current = (height(current.right.right) >= height(current.right.left)) ? singleRotateLeft(current) : doubleRotateLeft(current);
			}
		} else if (cmp > 0) {
			current.right = remove(current.right, item);
			if (height(current.left) - height(current.right) == 2) {
				current = (height(current.left.left) >= height(current.left.right)) ? singleRotateRight(current) : doubleRotateRight(current);
			}
		} else {
			return removeNode(current);
		}
		current.height = Math.max(height(current.left), height(current.right)) + 1;
		return current;
	}

	private AVLNode<T> removeNode(AVLNode<T> removalNode) {
		AVLNode<T> replacementNode;
		if (removalNode.left != null && removalNode.right != null) {
			replacementNode = findMin(removalNode.right);
			removalNode.right = removeMin(removalNode.right);

			replacementNode.left = removalNode.left;
			replacementNode.right = removalNode.right;

			if (height(replacementNode.left) - height(replacementNode.right) == 2) {
				if (height(replacementNode.left.left) >= height(replacementNode.left.right)) {
					replacementNode = singleRotateRight(replacementNode);
				} else {
					replacementNode = doubleRotateRight(replacementNode);
				}
			}
			replacementNode.height = Math.max(height(replacementNode.left), height(replacementNode.right)) + 1;
		} else {
			replacementNode = (removalNode.left != null) ? removalNode.left : removalNode.right;
			treeSize--;
		}
		removalNode.left = null;
		removalNode.right = null;
		return replacementNode;
	}

	private AVLNode<T> removeMin(AVLNode<T> current) {
		if (current == null) return null;
		if (current.left == null) {
			treeSize--;
			return current.right;
		}
		current.left = removeMin(current.left);
		if (height(current.right) - height(current.left) == 2) {
			current = (height(current.right.right) >= height(current.right.left)) ? singleRotateLeft(current) : doubleRotateLeft(current);
		}
		current.height = Math.max(height(current.left), height(current.right)) + 1;
		return current;
	}

	private AVLNode<T> findMin(AVLNode<T> current) {
		if (current.left == null) return current;
		return findMin(current.left);
	}

	private static <T extends Comparable<T>> int height(AVLNode<T> current) {
		if (current == null) return -1;
		return current.height;
	}

	private static <T extends Comparable<T>> AVLNode<T> singleRotateRight(AVLNode<T> current) {
		AVLNode<T> lc = current.left;

		current.left = lc.right;
		lc.right = current;
		current.height = Math.max(height(current.left), height(current.right)) + 1;
		lc.height = Math.max(height(lc.left), lc.height) + 1;

		return lc;
	}

	private static <T extends Comparable<T>> AVLNode<T> singleRotateLeft(AVLNode<T> p) {
		AVLNode<T> rc = p.right;

		p.right = rc.left;
		rc.left = p;
		p.height = Math.max(height(p.left), height(p.right)) + 1;
		rc.height = Math.max(height(rc.right), rc.height) + 1;

		return rc;
	}

	private static <T extends Comparable<T>> AVLNode<T> doubleRotateRight(AVLNode<T> p) {
		p.left = singleRotateLeft(p.left);
		return singleRotateRight(p);
	}

	private static <T extends Comparable<T>> AVLNode<T> doubleRotateLeft(AVLNode<T> p) {
		p.right = singleRotateRight(p.right);
		return singleRotateLeft(p);
	}

	public void clear() {
		this.root = null;
		this.treeSize = 0;
		this.modCount++;
	}

	@Override
	public boolean contains(T item) {
		AVLNode<T> t = findNode(item);
		return t != null;
	}

	public boolean isEmpty() {
		return this.treeSize == 0;
	}

	public int size() {
		return this.treeSize;
	}

	public ArrayList<T> toArray() {
		ArrayList<T> arr = new ArrayList<>(this.treeSize);
		for(T item : this) {
			arr.add(item);
		}
		return arr;
	}
	
	public T find(T item) {
		AVLNode<T> t = findNode(item);
		return t == null ? null : t.nodeValue;
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
	public Iterator<T> iterator() {
		return new TreeIterator(root);
	}

	private class TreeIterator implements Iterator<T> {
		private Deque<AVLNode<T>> stack;
		private AVLNode<T> curr;
		
		private final int expectedModCount = modCount;

		private AVLNode<T> goFarLeft(AVLNode<T> node) {
			if (node == null) return null;
			while (node.left != null) {
				stack.push(node);
				node = node.left;
			}
			return node;
		}

		public TreeIterator(AVLNode<T> root) {
			stack = new ArrayDeque<>();
			curr = goFarLeft(root);
		}

		public boolean hasNext() {
			return curr != null;
		}

		public T next() {
		
			checkIteratorState();

			if (curr == null)
				throw new NoSuchElementException("No elements remaining");

			T returnValue = curr.nodeValue;

			if (curr.right != null) {
				curr = goFarLeft(curr.right);
			} else if (!stack.isEmpty()) {
				curr = stack.pop();
			} else {
				curr = null; 
			}
			return returnValue;
		}

		public void remove() {
			// no implementation
		}

		private void checkIteratorState() {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException("Inconsistent iterator");
			}
				
		}
	}
}