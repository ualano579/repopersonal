package practicas.practica03.parte01;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

public class Auxiliar {
	private static ArrayList<String> stopWords = new ArrayList<>(Arrays.asList("a", "al", "con", "de", "del", "el", "en", "es", "si", "la",
            																  "las", "los", "las", "se", "y", "o", "por", "han", "ya", "lo",
            																  "para", "que", "un", "este", "esta", "aquel", "aquella", "otro", "otra", "y"));

	public static boolean isStopWord(String word) {
		return stopWords.contains(word);
	}
	
	public static String cleanWord(String word) {
		word = word.replaceAll("ñ", "\001");
		return Normalizer.normalize(word, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").replaceAll("\001", "ñ");
	}
}
