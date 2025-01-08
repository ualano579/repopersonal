package estudio.universidad;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;

public class UniversidadTestJUnit {
	
	private Universidad ual;
	//ATENCION: El paquete debe ser nombrado como: pruebas.universidad
	private String directorio=System.getProperty("user.dir")+File.separator+"src"
			+File.separator+"estudio"+File.separator+"universidad"+File.separator;
	private String archivo="datos.txt";

	@Before
	public void setUp() throws Exception {
		ual=new Universidad(new File(directorio+File.separator+archivo));
	}
	
	@Test
	@Order(1)
	//Este test comprobara la correcta implementacion de la clase Alumno
	public void testAlumno(){
		Alumno a = new Alumno("11111111A", "Juan", "Garcia", "Lopez");
		assertEquals("11111111A", a.getDni());
		assertEquals("Juan", a.getNombre());
		assertEquals("Garcia", a.getApellido1());
		assertEquals("Lopez", a.getApellido2());
		
		//Dos alumnos se consideraran iguales si tienen el mismo dni
		Alumno b = new Alumno("11111111A", "Roberto", "Rodriguez", "Ramirez");
		assertEquals(a, b);
		Alumno c = new Alumno("22222222B", "Juan", "Garcia", "Lopez");
		assertNotEquals(a, c);
		
		//La comparacion de dos alumnos se realizara por orden: nombre > apellido1 > apellido2 > dni
		assertTrue(a.compareTo(b)<0);
		assertTrue(a.compareTo(c)<0);
		assertTrue(c.compareTo(a)>0);
		
		//Formato esperado del metodo toString
		String expected="11111111A -> Juan Garcia Lopez";
		assertEquals(expected, a.toString());
	}
	
	@Test
	@Order(2)
	//Este test comprobarala correcta implementacion de la clase Curso
	public void testCurso(){
		Curso c = new Curso("Matematicas", 1, "Algebra Lineal");
		assertEquals("Matematicas", c.getCarrera());
		assertEquals(1, c.getCurso());
		assertEquals("Algebra Lineal", c.getAsignatura());
		
		//Dos cursos se consideraran iguales si pertenecen a la misma carrera y curso y tienen el mismo nombre
		Curso d = new Curso("Matematicas", 1, "Algebra Lineal");
		assertEquals(c,d);
		Curso e = new Curso("Matematicas", 1, "Calculo");
		assertNotEquals(c, e);
		Curso f = new Curso("Matematicas", 2, "Algebra Lineal");
		assertNotEquals(c, f);
		Curso g = new Curso("Informatica", 1, "Algebra Lineal");
		assertNotEquals(c, g);
		
		//La comparativa se realizara por preferencia en carrera, despues por curso y despues por nombre
		assertTrue(c.compareTo(d)==0);
		assertTrue(c.compareTo(e)<0);
		assertTrue(c.compareTo(f)<0);
		assertTrue(c.compareTo(g)>0);
		
		//Formato esperado del metodo toString
		String expected="Matematicas 1º (Algebra Lineal)";
		assertEquals(expected, c.toString());
	}
	
	@Test
	@Order(3)
	//Este test comprobara la correcta carga de datos
	public void testCargaDatos(){
		assertEquals("Universidad de Almeria", ual.getNombre());
	}

	@Test
	@Order(4)
	//Este test comprueba el numero de asignaturas existentes en la universidad
	public void testNumeroAsignaturas() {
		assertTrue(ual.numeroAsignaturas()==57);
		
		//Se intenta agregar una asignatura que no existe
		ual.addCurso("Quimica", 1, "Matematicas");
		assertTrue(ual.numeroAsignaturas()==58);
		
		//Se intenta agregar una asignatura que ya existe
		Curso c = new Curso("Informatica", 1, "Estadistica");
		ual.addCurso(c);
		assertTrue(ual.numeroAsignaturas()==58);
	}
	
	@Test
	@Order(5)
	//Este test comprueba el numero de alumnos distintos en la universidad
	public void testNumeroAlumnos(){
		assertTrue(ual.numeroAlumnos()==10);
		
		//Se intenta agregar un alumno nuevo que NO existe en esa asignatura
		Curso c = new Curso("Informatica", 1, "Metodolog a de la Programaci n");
		ual.addAlumno(c, "12345678A", "Juan", "Lopez", "Dimas");
		assertTrue(ual.numeroAlumnos()==11);
		
		//Se intenta agregar un alumno nuevo que SI existe en esa asignatura
		Alumno a = new Alumno("94215615W", "Luis", "Iribarne", "Perez");
		ual.addAlumno(c, a);
		assertTrue(ual.numeroAlumnos()==11);
	}
	
	@Test
	@Order(6)
	//Este test comprueba el numero de matriculas por asignatura en la universidad
	public void testNumeroMatriculas(){
		assertTrue(ual.numeroMatriculas()==95);
		
		//Se intenta agregar un alumno nuevo que NO existe en esa asignatura
		Curso c = new Curso("Informatica", 1, "Metodologia de la Programacion");
		ual.addAlumno(c, "12345678A", "Juan", "Lopez", "Dimas");
		assertTrue(ual.numeroMatriculas()==96);
		
		//Se intenta agregar un alumno nuevo que SI existe en esa asignatura
		Alumno a = new Alumno("94215615W", "Luis", "Iribarne", "Perez");
		ual.addAlumno(c, a);
		assertTrue(ual.numeroMatriculas()==96);
	}
	
	@Test
	@Order(7)
	//Este test comprueba el numero de alumnos distintos en la carrera pasada por parametro
	public void testNumeroAlumnosDeCarrera(){
		assertTrue(ual.numeroAlumnosDeCarrera("Informatica")==10);
		assertTrue(ual.numeroAlumnosDeCarrera("ADE")==8);
		assertTrue(ual.numeroAlumnosDeCarrera("Mecanica")==8);
		assertTrue(ual.numeroAlumnosDeCarrera("Humanidades")==0);
	}
	
	@Test
	@Order(8)
	//Este test comprueba el numero de matriculas por la carrera pasada por parametro
	public void testNumeroMatriculasDeCarrera(){
		assertTrue(ual.numeroMatriculasDeCarrera("Informatica")==78);
		assertTrue(ual.numeroMatriculasDeCarrera("ADE")==9);
		assertTrue(ual.numeroMatriculasDeCarrera("Mecanica")==8);
		assertTrue(ual.numeroMatriculasDeCarrera("Humanidades")==0);
	}
	
	@Test
	@Order(9)
	//Este test comprueba el numero de alumnos distintos cursando una asignatura
	public void testNumeroAlumnosDeAsignatura(){
		assertTrue(ual.numeroAlumnosDeAsignatura("Trabajo Fin de Grado")==4);
		assertTrue(ual.numeroAlumnosDeAsignatura("Desarrollo Rapido de Aplicaciones")==1);
		assertTrue(ual.numeroAlumnosDeAsignatura("Estadistica")==6);
		assertTrue(ual.numeroAlumnosDeAsignatura("Humanidades")==0);
	}
	
	@Test
	@Order(10)
	//Este test comprueba el numero de matriculas de una asignatura
	public void testNumeroMatriculasDeAsignatura(){
		assertTrue(ual.numeroMatriculasDeAsignatura("Trabajo Fin de Grado")==4);
		assertTrue(ual.numeroMatriculasDeAsignatura("Desarrollo Rapido de Aplicaciones")==1);
		assertTrue(ual.numeroMatriculasDeAsignatura("Estadistica")==7);
		assertTrue(ual.numeroMatriculasDeAsignatura("Humanidades")==0);
	}
	
	@Test
	@Order(11)
	//Este test comprueba la correcta implementacion del metodo clear
	public void testClear(){
		assertTrue(ual.numeroMatriculas()!=0);
		ual.clear();
		assertTrue(ual.numeroMatriculas()==0);
	}
	
	@Test
	@Order(12)
	//Este test comprueba la correcta implementacion del metodo remove
	public void testRemove(){
		assertTrue(ual.numeroMatriculas()==95);
		Curso c = new Curso("Informatica", 1, "Estadistica");
		ual.remove(c);
		assertTrue(ual.numeroMatriculas()==93);
		
		c = new Curso("ADE", 1, "Estadistica");
		ual.remove(c);
		assertTrue(ual.numeroMatriculas()==88);
	}
	
	@Test
	@Order(13)
	//Este test extrae un listado de alumnos cursando las asignaturas que se imparten con el formato adecuado
	public void testListadoCursoAlumnos(){
		String expected="ADE 1º (Estadistica)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "ADE 2º (Macroeconomia)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "ADE 3º (Economia Mundial)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "Informatica 1º (Algebra Lineal y Matematica Discreta)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "Informatica 1º (Calculo)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "Informatica 1º (Estadistica)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "Informatica 1º (Estructura y Tecnologia de Computadores)\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "Informatica 1º (Fisica para Informatica)\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "Informatica 1º (Fundamentos de Electronica)\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "Informatica 1º (Introduccion a la Programacion)\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "Informatica 1º (Metodologia de la Programacion)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "Informatica 1º (Organizacion y Gestion de Empresas)\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "Informatica 2º (Bases de Datos)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "Informatica 2º (Estructura de Datos y Algoritmos I)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "Informatica 2º (Estructura de Datos y Algoritmos II)\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "Informatica 2º (Fundamentos de Redes de Computadores)\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "	88392554N -> Silvia Sanchez Comendador\n"
				+ "Informatica 2º (Planificacion y Gestion de Proyectos Informaticos)\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "Informatica 2º (Programacion de Servicios Software)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "	88392554N -> Silvia Sanchez Comendador\n"
				+ "Informatica 3º (Administracion de Bases de Datos)\n"
				+ "	88392554N -> Silvia Sanchez Comendador\n"
				+ "Informatica 3º (Desarrollo de Interfaces de Usuario)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "Informatica 3º (Herramientas y Metodos de Ingenieria del Software)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "Informatica 3º (Informatica Industrial y Robotica)\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "Informatica 3º (Ingenieria de Requisitos)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "Informatica 3º (Ingenieria de Sistemas de Informacion)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "Informatica 3º (Integracion de las Tecnologias de la Informacion en las Organizaciones)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "Informatica 3º (Modelado y Diseño del Software 2)\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "Informatica 3º (Multiprocesadores)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "Informatica 3º (Negocio Electronico)\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "Informatica 3º (Perifericos e Interfaces)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "Informatica 3º (Seguridad y Cumplimiento Normativo)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "Informatica 3º (Tecnologias Web)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "Informatica 3º (Transmision de Datos y Redes de Computadores)\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "Informatica 3º (Tratamiento Digital de Imagenes)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "Informatica 4º (Administracion de Sistemas TIC)\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "Informatica 4º (Analisis y Planificacion de las TI)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "Informatica 4º (Auditoria y Control Normativo en los Sistemas de Informacion)\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "Informatica 4º (Desarrollo Rapido de Aplicaciones)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "Informatica 4º (Desarrollo de Soluciones TIC)\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "Informatica 4º (Desarrollo de Soluciones en Ingenieria del Software)\n"
				+ "	88392554N -> Silvia Sanchez Comendador\n"
				+ "Informatica 4º (Fiabilidad y Gestion de Riesgos)\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "Informatica 4º (Fundamentos de los Sistemas Inteligentes)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "Informatica 4º (Gestion de Datos en Sistemas de Informacion Web)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "Informatica 4º (Inteligencia del Negocio)\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "Informatica 4º (Normativa y Regulacion Informatica)\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "	88392554N -> Silvia Sanchez Comendador\n"
				+ "Informatica 4º (Practicas Externas en Empresa 1)\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "Informatica 4º (Principios de Evaluacion de Riesgos en los Sistemas de Informacion)\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "Informatica 4º (Procesos de Ingenieria del Software 2)\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "Informatica 4º (Programacion Distribuida y en Tiempo Real)\n"
				+ "	70931519W -> Guillermo Gisbert Carranza\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "Informatica 4º (Seguridad Informatica)\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "Informatica 4º (Seguridad TIC)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "Informatica 4º (Tecnologias Multimedia)\n"
				+ "	88392554N -> Silvia Sanchez Comendador\n"
				+ "Informatica 4º (Trabajo Fin de Grado)\n"
				+ "	84248003S -> Jordi Bru Escobar\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "Mecanica 1º (Quimica)\n"
				+ "	73031578T -> Luis Gomez Rodriguez\n"
				+ "	56764319C -> Enrique Mata Galan\n"
				+ "Mecanica 2º (Termotecnia)\n"
				+ "	94215615W -> Luis Iribarne Perez\n"
				+ "	48654797E -> Francisco Lorente Estevez\n"
				+ "Mecanica 3º (Diseño Asistido por Ordenador)\n"
				+ "	54894803J -> Alfonso Trejo Pozo\n"
				+ "Mecanica 4º (Fabricacion Industrial)\n"
				+ "	64646466K -> Iribarne Perez Roberto\n"
				+ "Mecanica 4º (Trabajo Fin de Grado)\n"
				+ "	37606876J -> Juan Perez Lopez\n"
				+ "	88392554N -> Silvia Sanchez Comendador";
		assertEquals(expected, ual.listadoCursoAlumnos());
	}
	
	@Test
	@Order(14)
	//Este test extrae un listado de alumnos distintos cursando una carrera
	public void testListadoAlumnosCarreraOrdenados(){
		String expected="[73031578T -> Luis Gomez Rodriguez, 94215615W -> Luis Iribarne Perez, "
				+ "48654797E -> Francisco Lorente Estevez, 56764319C -> Enrique Mata Galan, "
				+ "37606876J -> Juan Perez Lopez, 64646466K -> Iribarne Perez Roberto, "
				+ "88392554N -> Silvia Sanchez Comendador, 54894803J -> Alfonso Trejo Pozo]";
		assertEquals(expected, ual.listadoAlumnosCarreraOrdenados("Mecanica"));
	}
	
	@Test
	@Order(15)
	//Este test extrae un listado de alumnos distintos cursando un curso (numero)
	public void testListadoAlumnosCursoOrdenados(){
		String expected="[84248003S -> Jordi Bru Escobar, 70931519W -> Guillermo Gisbert Carranza, "
				+ "73031578T -> Luis Gomez Rodriguez, 94215615W -> Luis Iribarne Perez, "
				+ "48654797E -> Francisco Lorente Estevez, 56764319C -> Enrique Mata Galan, "
				+ "37606876J -> Juan Perez Lopez, 64646466K -> Iribarne Perez Roberto, "
				+ "88392554N -> Silvia Sanchez Comendador, 54894803J -> Alfonso Trejo Pozo]";
		assertEquals(expected, ual.listadoAlumnosCursoOrdenados(4));
	}
	
	@Test
	@Order(16)
	//Este test extrae un listado de alumnos matriculados en una carrera, curso y asignatura espec fico
	public void testListadoAlumnosCarreraCursoAsignatura(){
		String expected="[56764319C -> Enrique Mata Galan, 37606876J -> Juan Perez Lopez]";
		assertEquals(expected, ual.listadoAlumnosCarreraCursoAsignatura("Informatica", 1, "Introduccion a la Programacion"));
		
		expected="[84248003S -> Jordi Bru Escobar, 94215615W -> Luis Iribarne Perez]";
		assertEquals(expected, ual.listadoAlumnosCarreraCursoAsignatura("Informatica", 2, "Estructura de Datos y Algoritmos I"));
		
		expected="[]";
		assertEquals(expected, ual.listadoAlumnosCarreraCursoAsignatura("Desconocida", 99, "Desconocida"));
	}
	
	@Test
	@Order(17)
	//Este test extrae un listado de cursos con mas de K alumnos matriculados en dicho curso
	public void testListadoCursosConMasDeKAlumnosMatriculados(){
		String expected="[ADE 1º (Estadistica), ADE 3º (Economia Mundial), Informatica 2º (Bases de Datos), Informatica 3º (Multiprocesadores), "
				+ "Informatica 3º (Transmision de Datos y Redes de Computadores), Informatica 3º (Tratamiento Digital de Imagenes), "
				+ "Informatica 4º (Fundamentos de los Sistemas Inteligentes), Informatica 4º (Seguridad TIC)]";
		assertEquals(expected, ual.listadoCursosConMasDeKAlumnosMatriculados(2));
		
		expected="[ADE 1º (Estadistica), Informatica 3º (Tratamiento Digital de Imagenes)]";
		assertEquals(expected, ual.listadoCursosConMasDeKAlumnosMatriculados(3));
		
		expected="[ADE 1º (Estadistica)]";
		assertEquals(expected, ual.listadoCursosConMasDeKAlumnosMatriculados(4));
		
		expected="[]";
		assertEquals(expected, ual.listadoCursosConMasDeKAlumnosMatriculados(1000));
	}
	
	@Test
	@Order(18)
	//Este test extrae un listado de alumnos y las asignaturas en las que esta matriculado
	public void testListadoAlumnosAsignaturasMatriculado(){
		String expected="37606876J -> Juan Perez Lopez:\n"
				+ "	ADE 3º (Economia Mundial)\n"
				+ "	Informatica 1º (Introduccion a la Programacion)\n"
				+ "	Informatica 1º (Metodologia de la Programacion)\n"
				+ "	Informatica 2º (Bases de Datos)\n"
				+ "	Informatica 2º (Planificacion y Gestion de Proyectos Informaticos)\n"
				+ "	Informatica 3º (Transmision de Datos y Redes de Computadores)\n"
				+ "	Informatica 4º (Practicas Externas en Empresa 1)\n"
				+ "	Mecanica 4º (Trabajo Fin de Grado)\n"
				+ "48654797E -> Francisco Lorente Estevez:\n"
				+ "	ADE 1º (Estadistica)\n"
				+ "	Informatica 1º (Organizacion y Gestion de Empresas)\n"
				+ "	Informatica 3º (Informatica Industrial y Robotica)\n"
				+ "	Informatica 4º (Administracion de Sistemas TIC)\n"
				+ "	Informatica 4º (Auditoria y Control Normativo en los Sistemas de Informacion)\n"
				+ "	Informatica 4º (Fundamentos de los Sistemas Inteligentes)\n"
				+ "	Informatica 4º (Normativa y Regulacion Informatica)\n"
				+ "	Informatica 4º (Procesos de Ingenieria del Software 2)\n"
				+ "	Informatica 4º (Seguridad Informatica)\n"
				+ "	Informatica 4º (Trabajo Fin de Grado)\n"
				+ "	Mecanica 2º (Termotecnia)\n"
				+ "54894803J -> Alfonso Trejo Pozo:\n"
				+ "	ADE 1º (Estadistica)\n"
				+ "	Informatica 1º (Estadistica)\n"
				+ "	Informatica 1º (Fundamentos de Electronica)\n"
				+ "	Informatica 3º (Informatica Industrial y Robotica)\n"
				+ "	Informatica 3º (Integracion de las Tecnologias de la Informacion en las Organizaciones)\n"
				+ "	Informatica 4º (Desarrollo de Soluciones TIC)\n"
				+ "	Informatica 4º (Seguridad TIC)\n"
				+ "	Mecanica 3º (Diseño Asistido por Ordenador)\n"
				+ "56764319C -> Enrique Mata Galan:\n"
				+ "	ADE 1º (Estadistica)\n"
				+ "	ADE 3º (Economia Mundial)\n"
				+ "	Informatica 1º (Calculo)\n"
				+ "	Informatica 1º (Estructura y Tecnologia de Computadores)\n"
				+ "	Informatica 1º (Introduccion a la Programacion)\n"
				+ "	Informatica 2º (Bases de Datos)\n"
				+ "	Informatica 3º (Multiprocesadores)\n"
				+ "	Informatica 3º (Tratamiento Digital de Imagenes)\n"
				+ "	Informatica 4º (Fundamentos de los Sistemas Inteligentes)\n"
				+ "	Informatica 4º (Gestion de Datos en Sistemas de Informacion Web)\n"
				+ "	Mecanica 1º (Quimica)\n"
				+ "64646466K -> Iribarne Perez Roberto:\n"
				+ "	ADE 1º (Estadistica)\n"
				+ "	Informatica 2º (Fundamentos de Redes de Computadores)\n"
				+ "	Informatica 3º (Tratamiento Digital de Imagenes)\n"
				+ "	Informatica 4º (Auditoria y Control Normativo en los Sistemas de Informacion)\n"
				+ "	Informatica 4º (Inteligencia del Negocio)\n"
				+ "	Informatica 4º (Principios de Evaluacion de Riesgos en los Sistemas de Informacion)\n"
				+ "	Informatica 4º (Procesos de Ingenieria del Software 2)\n"
				+ "	Informatica 4º (Programacion Distribuida y en Tiempo Real)\n"
				+ "	Mecanica 4º (Fabricacion Industrial)\n"
				+ "70931519W -> Guillermo Gisbert Carranza:\n"
				+ "	ADE 3º (Economia Mundial)\n"
				+ "	Informatica 1º (Algebra Lineal y Matematica Discreta)\n"
				+ "	Informatica 2º (Programacion de Servicios Software)\n"
				+ "	Informatica 3º (Desarrollo de Interfaces de Usuario)\n"
				+ "	Informatica 3º (Ingenieria de Requisitos)\n"
				+ "	Informatica 3º (Multiprocesadores)\n"
				+ "	Informatica 3º (Seguridad y Cumplimiento Normativo)\n"
				+ "	Informatica 3º (Tecnologias Web)\n"
				+ "	Informatica 4º (Analisis y Planificacion de las TI)\n"
				+ "	Informatica 4º (Programacion Distribuida y en Tiempo Real)\n"
				+ "73031578T -> Luis Gomez Rodriguez:\n"
				+ "	Informatica 1º (Fisica para Informatica)\n"
				+ "	Informatica 2º (Estructura de Datos y Algoritmos II)\n"
				+ "	Informatica 3º (Modelado y Diseño del Software 2)\n"
				+ "	Informatica 3º (Negocio Electronico)\n"
				+ "	Informatica 3º (Tecnologias Web)\n"
				+ "	Informatica 3º (Transmision de Datos y Redes de Computadores)\n"
				+ "	Informatica 3º (Tratamiento Digital de Imagenes)\n"
				+ "	Informatica 4º (Fiabilidad y Gestion de Riesgos)\n"
				+ "	Mecanica 1º (Quimica)\n"
				+ "84248003S -> Jordi Bru Escobar:\n"
				+ "	ADE 2º (Macroeconomia)\n"
				+ "	Informatica 1º (Calculo)\n"
				+ "	Informatica 1º (Estadistica)\n"
				+ "	Informatica 2º (Estructura de Datos y Algoritmos I)\n"
				+ "	Informatica 3º (Herramientas y Metodos de Ingenieria del Software)\n"
				+ "	Informatica 3º (Ingenieria de Sistemas de Informacion)\n"
				+ "	Informatica 3º (Multiprocesadores)\n"
				+ "	Informatica 3º (Perifericos e Interfaces)\n"
				+ "	Informatica 3º (Tratamiento Digital de Imagenes)\n"
				+ "	Informatica 4º (Seguridad TIC)\n"
				+ "	Informatica 4º (Trabajo Fin de Grado)\n"
				+ "88392554N -> Silvia Sanchez Comendador:\n"
				+ "	Informatica 2º (Fundamentos de Redes de Computadores)\n"
				+ "	Informatica 2º (Programacion de Servicios Software)\n"
				+ "	Informatica 3º (Administracion de Bases de Datos)\n"
				+ "	Informatica 4º (Desarrollo de Soluciones en Ingenieria del Software)\n"
				+ "	Informatica 4º (Normativa y Regulacion Informatica)\n"
				+ "	Informatica 4º (Tecnologias Multimedia)\n"
				+ "	Mecanica 4º (Trabajo Fin de Grado)\n"
				+ "94215615W -> Luis Iribarne Perez:\n"
				+ "	ADE 1º (Estadistica)\n"
				+ "	Informatica 1º (Metodologia de la Programacion)\n"
				+ "	Informatica 2º (Bases de Datos)\n"
				+ "	Informatica 2º (Estructura de Datos y Algoritmos I)\n"
				+ "	Informatica 3º (Integracion de las Tecnologias de la Informacion en las Organizaciones)\n"
				+ "	Informatica 3º (Transmision de Datos y Redes de Computadores)\n"
				+ "	Informatica 4º (Desarrollo Rapido de Aplicaciones)\n"
				+ "	Informatica 4º (Fundamentos de los Sistemas Inteligentes)\n"
				+ "	Informatica 4º (Gestion de Datos en Sistemas de Informacion Web)\n"
				+ "	Informatica 4º (Seguridad TIC)\n"
				+ "	Mecanica 2º (Termotecnia)\n";
		assertEquals(expected, ual.listadoAlumnosAsignaturasMatriculado());
	}
	
	@Test
	@Order(19)
	//Este test extrae un listado con los alumnos con el mayor numero de asignaturas matriculadas
	public void testAlumnosConMasMatriculas(){
		String expected="11 asignaturas: [84248003S -> Jordi Bru Escobar, 94215615W -> Luis Iribarne Perez, "
				+ "48654797E -> Francisco Lorente Estevez, 56764319C -> Enrique Mata Galan]";
		assertEquals(expected, ual.alumnosConMasMatriculas());
	}
}
