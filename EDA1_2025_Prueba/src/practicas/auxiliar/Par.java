package practicas.auxiliar;


public class Par <K extends Comparable<K>,V> implements Comparable<Par<K,V>>{
	
	private final K key;
	private V value;
	
	public Par(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return this.key;
	}

	public V getValue() {
		return this.value;
	}
	
	public V setValue(V value) {
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}
	
	@Override
	public String toString() {
		return key + " <" + value + ">";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Par)) return false;
		return this.key.equals(((Par<?,?>)o).key);
	}
	
	@Override
	public int compareTo(Par<K,V> other) {
		return this.key.compareTo(other.key);
	}
}
