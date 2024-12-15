package practicas.practica04.parte02;

import java.util.Map.Entry;
import java.util.TreeMap;

import practicas.practica03.parte02.Estudiante;
import practicas.practica03.parte02.GestionEstudiantes;
import practicas.practica04.Network;

public class EstudiantesNetwork extends Network<Estudiante>{

	public boolean load(String fileName, String centroId) {
		GestionEstudiantes gestion = new GestionEstudiantes(centroId);
		if (!gestion.load(fileName)) return false; //Cargamos el archivo de entrada correspondiente a la práctica 03 - parte 02
		
		this.setDirected(false); //Grafo valorado simétrico (no dirigido)
		
		//Vamos a construir el grafo con la información que tenemos sobre estudiantes y número de asignaturas compartidas
		//En primer lugar, tenemos que permitir que se pueda iterar sobre un objeto de la clase GestionEstudiantes
		//En cada iteración obtenemos el par (estudiante, {estudiante_i, numAsignaturasCompartidas(estudiante, estudiante_i)})
		//Cada 3-upla (estudiante, estudiante_i, numAsignaturasCompartidas) dará lugar a una arista en el grafo
		//¿Por qué el peso de cada arista es 1/númAsignaturasCompartidas? --> leer guion
		//Al ser un grafo simétrico, este doble bucle se podría optimizar, ¿verdad? ¿Cómo lo ves? 
		for(Entry<Estudiante, TreeMap<Estudiante, Integer>> parMain: gestion)  {
			this.addVertex(parMain.getKey());
			for(Entry<Estudiante, Integer> parSecond: parMain.getValue().entrySet()) {
				this.addVertex(parSecond.getKey());
				this.addEdge(parMain.getKey(), parSecond.getKey(), 1.0/parSecond.getValue());
			}
		}
		return true;
	}
}
