package practicas.practica04.parte01;

import java.io.File;
import java.util.Scanner;

import practicas.practica04.Network;

public class CarreterasNetwork extends Network<String> {
	
	private enum Secciones {
			Type, Vertex, Edge;
			void load(CarreterasNetwork net, String linea){
				switch (this){
				case Type: 
					//1 única línea
					net.setDirected(!linea.equals("Not Directed"));
					break;
				case Vertex:
					//1 única línea
					net.addVertex(linea);
					break;
				case Edge:
					String[] words = linea.split("[ \t]+");
					//1 única línea
					net.addEdge(words[0], words[1], Double.parseDouble(words[2]));
					break;
				}
				
			}
	}
	//Lo improtante es entender este método y la ventaja que tiene la utilización del enumerado
	public boolean load(String filename){
		Scanner scan = null;
		Secciones seccion = null; 
		this.adjacencyList.clear();
		try{
			scan = new Scanner(new File(filename));
		}catch(Exception e){
			return false;
		}
		while(scan.hasNextLine()){
			String line = scan.nextLine().trim();
			if (line.isEmpty()) continue;
			if (line.startsWith("%")) continue;
			
			if (line.toLowerCase().equals("@type")){
				seccion = Secciones.Type;
				continue;
			}
			if (line.toLowerCase().equals("@vertex")){
				seccion = Secciones.Vertex;
				continue;
			}
			if (line.toLowerCase().equals("@edges")){
				seccion = Secciones.Edge;
				continue;
			}
			seccion.load(this, line);
		}
		scan.close();
		return true;
	}
}
