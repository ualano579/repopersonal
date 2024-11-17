package auxiliar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeSet;
import practicas.auxiliar.*;

public class Prueba {
	private ArrayList<Par<String, TreeSet<Integer>>> datosOriginal;
	private AVLTree<Par<String,Double>> datosDestino;
	
	
	public Prueba() {
		this.datosOriginal = new ArrayList<>();
		this.datosDestino = new AVLTree<>();
	}
	
	public void transform() {
		for (Par<String,TreeSet<Integer>> parCurr : datosOriginal) {
			Par<String, Double> par = datosDestino.find(new Par<>(parCurr.getKey(), null));
			int sum = 0;
			for (Integer num : parCurr.getValue()) {
				sum += num;
			}
			if (par == null) {
				datosDestino.add(par = new Par<>(parCurr.getKey(), (double) (sum/parCurr.getValue().size())));
			}else {
				par.setValue((double)(sum/parCurr.getValue().size() + par.getValue())/2.0);
			}
		}
	}
	
//	private AVLTree<Par<String,AVLTree<Par<String,AVLTree<Par<String, Integer>>>>>> dO;
//	private ArrayList<Par<Integer, String>> dD;
//	
//	public ArrayList<Par<Integer, String>> transform2(String autor) {
//		ArrayList<Par<Integer, String>> result = new ArrayList<>();
//		
//		return result;
//	}
	
	ArrayList<Par<String, AVLTree<Integer>>> datos_Original = new ArrayList<>();
	AVLTree<Par<String, Double>> datos_Destino = new AVLTree<>();
	
	public void reduce() {
		AVLTree<Par<String,ArrayList<Integer>>> aux = new AVLTree<>();
		for (Par<String, AVLTree<Integer>> par : datos_Original) {
			Par<String, ArrayList<Integer>> p1 = aux.find(new Par<>(par.getKey(), null));
			if(p1 == null) aux.add(p1 = new Par<>(par.getKey(), new ArrayList<>()));
			for (Integer i : par.getValue()) {
				p1.getValue().add(i);
			}
//			p1.getValue().addAll(Arrays.asList(par.getValue()));
		}
		for (Par<String, ArrayList<Integer>> par2 : aux) {
			datos_Destino.add(new Par<>(par2.getKey(), mediaArritmetica(par2.getValue())));
		}
	}
	
	
	private Double mediaArritmetica(ArrayList<Integer> value) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Prueba p = new Prueba();
		TreeSet<Integer> t01 = new TreeSet<>();
		TreeSet<Integer> t02 = new TreeSet<>();
		TreeSet<Integer> t03 = new TreeSet<>();
		TreeSet<Integer> t04 = new TreeSet<>();
		TreeSet<Integer> t05 = new TreeSet<>();
		
		t01.addAll(Arrays.asList(1,2,3,4,5));
		t02.addAll(Arrays.asList(2,3,8));
		t03.addAll(Arrays.asList(9));
		t04.addAll(Arrays.asList(4));
		t05.addAll(Arrays.asList(10));
		
		p.datosOriginal.add(new Par<>("Pepe", t01));
		p.datosOriginal.add(new Par<>("María", t02));
		p.datosOriginal.add(new Par<>("Manu", t04));
		p.datosOriginal.add(new Par<>("Juan", t03));
		p.datosOriginal.add(new Par<>("Pepe", t05));
		
		p.transform();
		
		System.out.println(p.datosDestino);
	}

}
