package auxiliar;

import java.util.ArrayList;

public class Estilo {
	//private ArrayList<String> arr = new ArrayList<String>();
	
	public static void main(String[] args) {
		/*Estilo ejemplo = new Estilo();
		ejemplo.arr.add("3");
		System.out.println(ejemplo.arr.toString());*/
		System.out.println("\001");
		ArrayList<String> arr = new ArrayList<String>();
		
		for(int i=1; i<=50;i++) {
			/*if(Math.random()<0.5) {
				arr.add(String.valueOf(i));
				current++;
			}else {
				arr.add(String.valueOf(-i));
			}*/
			
			//cosa_común( o = (condicion_booleana) ? accion_True : accion_False
			arr.add(String.valueOf(Math.random()<0.5 ? i : -i));
		}
		int numNegativos3 = 0;
		
		/*for(int i=0; i < arr.size(); i++) {
			if(Integer.valueOf(arr.get(i))< 0) numNegativos1++;
		}*/
		
		//NUNCA
		/*Iterator<String> it = arr.iterator();
		while(it.hasNext()) {
			if (Integer.valueOf(it.next())< 0) numNegativos2++;
		}*/
		
		//SIEMPRE EXCEPTO EXCEPCIONES
		for(String valorStr : arr) {
			if(Integer.valueOf(valorStr)< 0) numNegativos3++;
		}
		
		System.out.println("El número de elementos negativos según forma 3 es : " + numNegativos3);
		System.out.println(arr.toString());
		System.out.println();
		//EJERCICIO: SI EL NÚMERO ES NEGATIVO HAZLO POSITIVO
		System.out.println("Ejercicio 2");
		ArrayList<String> arr1 = new ArrayList<>(arr);//COPIA PARA QUE ESTE EJERCICIO NO INFLUYA EN LOS DEMÁS
		System.out.println("Antes: "+ arr1.toString());
		/* EL FOR EACH NO MODIFICA EL ARRAY
		    for(String valorStr : arr) {
			if(Integer.valueOf(valorStr)< 0) valorStr = String.valueOf(-Integer.valueOf(valorStr));
		}*/
		
;		for(int i = 0; i< arr1.size(); i++) {
			if(Integer.valueOf(arr1.get(i))< 0) arr1.set(i, String.valueOf(-Integer.valueOf(arr1.get(i))));
		}
		
		System.out.println("Después: "+ arr1.toString());
		System.out.println();
		
		//ENCUENTRO EL PRIMER VALOR NEGATIVO, INFORMO Y SALGO
		System.out.println("Ejercicio 3");
		
		//BREAK
		for(String valorStr : arr) {
			if(Integer.valueOf(valorStr) < 0) {
				System.out.println("He encontrado el primer valor negativo: " + valorStr);
				break;
			}
			System.out.println("valor positivo hallado");
		}
		
		//CONTINUE
		for(String valorStr : arr) {
			if(Integer.valueOf(valorStr) >= 0) continue; //Va otra vez al for
			System.out.println("He encontrado el primer valor negativo: " + valorStr);
			break;
		}
		
		//BUCLE CON WHILE(TRUE) 
		
		int i = 0;
		while(true) {
			if(i == arr.size()) {
				System.out.println("No se ha encontrado ningún valor negativo");
				break;	
			}
			if(Integer.valueOf(arr.get(i)) < 0) {
				System.out.println("He encontrado el primer valor negativo: " + arr.get(i));
				break;
			}
			i++;
		}
		
		
		int j = 0;
		while(true) {
			if(j == arr.size()) {
				System.out.println("No se ha encontrado ningún valor negativo");
				break;	
			}
			if(Integer.valueOf(arr.get(j)) >= 0) {
				j++;
				continue;
			}
			System.out.println("He encontrado el primer valor negativo: " + arr.get(j));
			break;
		}
	}
}
