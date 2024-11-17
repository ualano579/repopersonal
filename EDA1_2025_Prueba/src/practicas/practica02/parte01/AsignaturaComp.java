package practicas.practica02.parte01;

import java.util.Comparator;

public class AsignaturaComp implements Comparator<AsignaturaNotas> {
    @Override
    public int compare(AsignaturaNotas o1, AsignaturaNotas o2) {
    	//Primer criterio de orden: cuatrimestre (de menor a mayor)
    	//Segundo criterio de orden: asignaturaId (de menor a mayor)
    	//Si no sabes cómo implementario, mira las diapositivas del Tema 02...
    	int cmp = Integer.compare(o1.getCuatrimestre(), o2.getCuatrimestre());
        return cmp == 0 ? o1.compareTo(o2) : cmp; //El compare de AsignaturaNotas compara por asignaturaId
    }
}

