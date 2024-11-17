package auxiliar;

public class AC_Ejercicio03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int tam = 10;
		int[] datos_int = {1, 2, 4, -3, 7, 9, 6, 8, -2, -40};
		char[] caracteres = "abcdefghij".toCharArray();

		for(int i = tam -1 ; i >= 0; i--){
		datos_int[i] += 15;
		System.out.print(datos_int[i]+ ", ");
		}
		
		System.out.println();
		for(int i = tam -1 ; i >= 0; i--){
		caracteres[i] = (char) ((char) caracteres[i] - 32);
		System.out.print(caracteres[i]+ ", ");
		}
		
		
	}

}
