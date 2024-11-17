/*
 * @(#)BSTree.java
 */

package practicas.auxiliar;

import java.util.*;

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
}