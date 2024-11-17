package practicas.practica03.parte02;

import java.util.ArrayList;

public class MyMath {
	public static double calculaMedia(ArrayList<Double> notas) {
		double suma = .0;
		for (Double nota: notas) {
			suma += nota;
		}
		return suma / notas.size();
	}
}
