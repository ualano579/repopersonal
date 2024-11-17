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
