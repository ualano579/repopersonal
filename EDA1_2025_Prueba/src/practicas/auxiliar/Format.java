package practicas.auxiliar;

import java.util.Locale;

public class Format {
	public static String formatDouble(double v) {
		return String.format(Locale.US, "%.2f", v);
	}

	public static String formatInt(int v) {
		return formatInt(v,3);
	}

	public static String formatInt(int v, int len) {
		return String.format(Locale.US, "%0" + len + "d", v);
	}
	
	public static String formatDouble(double v, int len) {
		return String.format(Locale.US, "%." + len + "f", v);
	}

}
