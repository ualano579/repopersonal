package practicas.practica03.parte03;

public class Componente implements Comparable<Componente>{
	private String componenteId;
	
	public Componente(String componenteId) {
		this.componenteId = componenteId;
	}
	
	@Override
	public String toString() {
		return this.componenteId;
	}
	
	@Override
	public boolean equals(Object otro) {
		return this.compareTo((Componente)otro) == 0;
	}
	
	@Override
	public int compareTo(Componente otro) {
		return this.componenteId.compareTo(otro.componenteId);
	}

}
