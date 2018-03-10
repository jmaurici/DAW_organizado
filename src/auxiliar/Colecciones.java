package auxiliar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import modelo.Estudiante;

public class Colecciones {
	
	//**************** VISITANTES - ISLAS - MESES **************
	
	//Leemos un fichero de texto formateado de la siguiente manera:
	//Isla - Mes - Miles de Visitantes.
	//Tenemos que pasar esa información a un HashMap de salida donde su clave sea un número entero
	//que asociamos a las isla (siendo 1 Gran Canaria hasta 7 El Hierro) y el valor de dicho
	//HashMap por cada Clave es un ArrayList que contiene números de tipo Float que serían la cantidad
	//de visitantes por miles. Cada índice del ArrayList coincide con el número de mes, siendo el índice 0
	//el mes 1, o sea enero.
	public HashMap<Integer, ArrayList<Float>> leerFicheroTextoVisitantes() {
		HashMap<Integer, ArrayList<Float>> resultado = new HashMap<Integer, ArrayList<Float>>();
		ArrayList<Float> visitantesPorMes = null;
		for (int i = 0; i < 7; i++) {
			visitantesPorMes = new ArrayList<Float>();
			for (int j = 0; j < 12; j++) {
				visitantesPorMes.add(0.0f);
			}
			resultado.put(i, visitantesPorMes);
		}
			FileReader fr;
			BufferedReader br;
			try {
				fr = new FileReader("ficheros/DatosIslaMesValor.txt");
				br = new BufferedReader(fr);
				String strLine;
				while ((strLine = br.readLine()) != null) {
					System.out.println(strLine);
					String[] campos = strLine.split("@");
					Integer isla = Integer.parseInt(campos[0])-1;
					Integer mes = Integer.parseInt(campos[1])-1;
					Float milesVisitantes = Float.parseFloat(campos[2]);
					resultado.get(isla).set(mes, milesVisitantes);			
				}
				fr.close();
				br.close();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (NumberFormatException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}		
		System.out.println(resultado);
		return resultado;
	}
	
	//Una vez tenemos los resultados leídos del fichero de texto DatosIslaMesValor.txt con le método anterior
	//que nos devuevle un HashMap, lo usamos en este método para que nos imprima los resultados
	//de los visitantes por isla, dentro de cada isla nos muestras los visitantes recibidos en cada mes y la suma
	//total en el año de dicha isla.
	public void mostrarVisitantesTotalesPorIslaYParcialesPorMes(HashMap<Integer, ArrayList<Float>> datosVisitantes){		
		//Tenemos dos Arrays que contienen los nombres o iniciales de las Islas y los Meses, de manera
		//que aprovechando el índice de cada Array podamos después llamar a cada celda para que nos
		//muestre por pantalla el nombre de la isla o mes de la cual estamos imprimiendo los visitantes.
		String[] islas = { "GC", "LTE", "FTV", "TFE", "LPA", "GOM", "HIE" };
		String[] meses = { "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC" };
		//Usamos un for tradicional porque conocedemos de antemano la cantidad de de datos que hay para recorrer
		//7 islas en el primer for y 12 meses en el for que está dentro.
		for (int i = 0; i < 7; i++) {
			//Debemo crear la variable que va a acumular la cantidad total de visitantes de cada isla
			//y por eso lo declaramos y asignamos a cero en el for que recorre las islas
			float acumulador = 0.0f;
			System.out.println("ISLA de " + islas[i]);
			System.out.println("----------------");		
			for (int j = 0; j < 12; j++) {
				System.out.println(meses[j] +": " + datosVisitantes.get(i).get(j));
				//el acumulador va sumando todos los visitantes de los 12 meses para una vez
				//salimos de este for y antes de que el primer for cambie de isla lo imprima por consola.
				acumulador += datosVisitantes.get(i).get(j);
			}
			//aquí imprimimos el total de cada isla y por eso 
			System.out.print("TOTAL VISITANTES EN " + islas[i]);
			//aquí imprimimos el total de cada isla y antes de volver a reiniciar el valor del acumulador 
			System.out.printf(": %.2f", acumulador);
			System.out.println();
			System.out.println("----------------");
		}			
	}
	
	//Hace lo mismo que el método anterior pero como su nombre indica nos muestra cada mes con los 
	//Visitantes de las islas en dicho mes y al final suma los visitantes totales de enero, febrero, etc.
	public void mostrarVisitantesTotalesPorMesYParcialesPorIsla(HashMap<Integer, ArrayList<Float>> datosVisitantes){		
		String[] islas = { "GC", "LTE", "FTV", "TFE", "LPA", "GOM", "HIE" };
		String[] meses = { "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC" };
		for (int i = 0; i < 12; i++) {
			float acumulador = 0.0f;
			System.out.println("Mes de " + meses[i]);
			System.out.println("----------------");		
			for (int j = 0; j < 7; j++) {
				System.out.println(islas[j] +": " + datosVisitantes.get(j).get(i));
				acumulador += datosVisitantes.get(j).get(i);
			}
			System.out.print("TOTAL VISITANTES EN " + meses[i]); System.out.printf(": %.2f", acumulador);
			System.out.println();
			System.out.println("----------------");
		}			
	}
	
	//Este método también recoge los resultados del HashMap que nos devuelve el método leerFicheroTextoVisitantes()
	//con todos los datos de visitantes de las islas y los meses generamos una especie de tabla
	//que monstramos por pantalla siendo las columnas los 12 meses y las filas la isla correspondiente
	//al final de la última columna y despues de diciembre tenemos una columna de totales que nos muestra
	//la sumatoria de visistantes de todo el año para cada isla
	public void mostrarVisitantesIslaMes1(HashMap<Integer, ArrayList<Float>> listaVisitantes) {
		String[] meses = { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPT",
				"OCTUBRE", "NOV", "DIC" };
		String[] islas = { "GRAN CANARIA", "LANZAROTE", "FUERTEVENTURA", "TENERIFE", "LA PALMA", "LA GOMERA",
				"EL HIERRO" };
		//usamos una variable isla que va a ir apuntando como índica al Array de islas que tenemos encima
		//puesto que los for que usamos más abajo no son los tradicionales si no los for each los cuales
		//no avanzan con un índice que podríamos aprovechar para ello.
		int indiceIsla = 0;
		//Este totalAnual es el que va a ir sumando todos los valores que tenga la variablesubTotalPorIsla
		float totalAnual = 0;
		System.out.print("\t");
		//primero imprimimos en consola en la parte superior todos los nombres de los meses
		for (String mes : meses) {
			System.out.print("\t" + mes);
		}
		//añadimos una columna mas que será la del total de visistantes en el año por cada isla
		System.out.print("\t" + "TOTAL");
		System.out.println();
		//He querido usar este bucle for each para hacer el método de otra forma y ver las posibilidades que hay de
		//hacerlo recorriendo un Set en vez del propio HashMap. (El siguiente método más abajo uso for tradicional)
		//con el método entrySet() aplicado al HashMap listaVisitantes, obtengo un Set de clave valor y cada uno de ellos
		//va a estar en la variable visitasIslaYear.
		for (Entry<Integer, ArrayList<Float>> visitasIslaYear : listaVisitantes.entrySet()) {
			//imprimimos el nombre de la isla usando la variable indiceIsla que hemos creado más arriba
			//e incremetándola para que la siguiente vez que pasemos por aquí apunte a una isla nueva
			System.out.print(islas[indiceIsla++]);
			float subTotalPorIsla = 0.0f;
			//Tener en cuenta que la variable visitasIslaYear almacena un conjunto de clave-valor, donde la clave son cada una 
			//de las islas y el valor son un ArrayList de los meses, cada uno de los cuales almacen la cantidad de visitantes
			//en forma de Float.
			//cuando escribimos visitasIslaYear.getValue() en la condicion del for, es nos devuelve un ArrayList de uno de los vendedores
			//y como en realidad todos son del mismo tamaño, ya que todos contienen 12 meses con la línea de código
			//visitasIslaYear.getValue().size() obtenemos dicho tamaño, o sea 12 y es lo que recorrerá el bucle for.
			//por supuesto podríamos haber puesto 12 directamente y hubiese funcionado, pero sólo para este caso
			//si quisieramos aprovechar este código sin saber de antemano la longitud de ArrayList, de esta manera nos vale
			//para otros casos.
			for (Integer mes = 0; mes < visitasIslaYear.getValue().size(); mes++) {	
				System.out.print("\t" + visitasIslaYear.getValue().get(mes).floatValue() + "");
				//debemos restarle 1 al indiceIslas porque una líneas más arriba en el for anterior hemos incrementado
				//el índice para que a la siguiente vuelta leyera la siguiente isla.
				subTotalPorIsla += listaVisitantes.get(indiceIsla-1).get(mes);
			}
			totalAnual += subTotalPorIsla;
			System.out.printf("\t%.2f", subTotalPorIsla);
			System.out.println();
		}
		System.out.println("..............................................................Total de Visitantes en todo el Archipielago:\t" + totalAnual);
	}
	
	//Este método hace exáctamente los mismo que el anterior. La diferencia está que en la implementacion
	//uso for tradicional, de manera que es algo más fácil de ver porque las variables de los for actúan de
	//indices que aprovechamos para llamar a los arrays que tienen los nombres de las islas y los meses
	//y ademas esos índices nos valen para extraer los datos del HashMap de entrada.
	public void mostrarVisitantesIslaMes2(HashMap<Integer, ArrayList<Float>> listaVisitantes) {
		String[] meses = { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPT",
				"OCTUBRE", "NOV", "DIC" };
		String[] islas = { "GRAN CANARIA", "LANZAROTE", "FUERTEVENTURA", "TENERIFE", "LA PALMA", "LA GOMERA",
				"EL HIERRO" };
		float totalAnual = 0;
		float[] acumuladorVisitantesMes = new float[12];
		System.out.print("\t");
		for (String mes : meses) {
			System.out.print("\t" + mes);
		}
		System.out.print("\t" + "TOTAL");
		System.out.println();
		for (int i = 0; i < islas.length; i++) {
			System.out.print(islas[i]);
			float subTotalPorIsla = 0.0f;
			for (int j = 0; j < meses.length; j++) {
				System.out.printf("\t%.2f" , listaVisitantes.get(i).get(j));
				subTotalPorIsla += listaVisitantes.get(i).get(j);
				acumuladorVisitantesMes[j] += listaVisitantes.get(i).get(j);
			}
			totalAnual += subTotalPorIsla;
			System.out.printf("\t%.2f", subTotalPorIsla);
			System.out.println("");

		}
		System.out.print("TOTAL: \t");

		for (int j = 0; j < meses.length; j++) {
			System.out.printf("\t%.2f" , acumuladorVisitantesMes[j]);
		}
		System.out.print("\t" + totalAnual);
		
	}
	
	//**************** VENTAS - VENDEDOR **************
	
	//A partir de un fichero de texto preformateado como: fechaVentas - codigoVendedor - importeVentas
	//añadimos el codigo de vendedor al HashMap como su clave y cada uno de los importesVentas dentro de
	//un ArrayList que será el valor de ese HashMap para cada clave.
	public HashMap<String, ArrayList<Float>> resumenVentasParcialesVendedor1(String ficheroVentas) {
		HashMap<String, ArrayList<Float>> resultado = new HashMap<String, ArrayList<Float>>();
		try {
			// Abrir el fichero
			FileReader fr = new FileReader(ficheroVentas); //El fichero de ejemplo se llama ventasDeptoAlmacen.txt
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] campos = linea.split("%");
				//Si el HashMap resultado no contiene la clave del vendedor la creamos por primera vez con su ArrayList vacío
				if (resultado.get(campos[1]) == null) 
					resultado.put(campos[1], new ArrayList<Float>());
				//Si el HashMap resultado si contiene la clave del vendedor aunque la acabemos de crear en la línea anterior,
				//añadimos el importe de venta al ArrayList que almacena las ventas de ese vendedor.
				resultado.get(campos[1]).add(Float.parseFloat((campos[2])));
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}
	
	//este método hace lo mismo que el anterior (leer arriba), la diferencia está en la implementación,
	//el código interno en el método de arriba pregunta si la clave del HashMap es null, de esa manera sabe
	//que la primera vez no existe y por lo tanto no tiene vendedor asignado, entonces crea la clave para poder
	//añadir los importes de Venta.
	//Este otro método pregunta al HashMap a través del método containsKey() si la clave de vendedor que
	//acaba de leer del fichero de texto ya existe en el HashMap, si no existe, crea la clave para poder
	//después añadir los importes de venta.
	//El resultado final de los los dos métodos es devolver una HashMap que contiene como clave los identificadores
	//de los distintos vendedores y como valor un ArrayList con cada una de sus ventas.
	public HashMap<String,ArrayList<Float>> resumenVentasParcialesVendedor2(String rutaFichero){
		HashMap<String,ArrayList<Float>> resultado = new HashMap<String,ArrayList<Float>>();	
		try {
			FileReader fr = new FileReader(rutaFichero); //El fichero de ejemplo se llama ventasDeptoAlmacen.txt
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				//String fechaVenta = linea.split("%")[0]; la fechaVenta no nos hace falta para este método
				String idVendedor = linea.split("%")[1];
				float importeVenta = Float.parseFloat(linea.split("%")[2]);
				//Si el HashMap resultado no contiene la clave del vendedor la creamos por primera vez
				if (!resultado.containsKey(idVendedor)) {
					resultado.put(idVendedor, new ArrayList<Float>());
				}
				//Si el HashMap resultado si contiene la clave del vendedor añadimos el importe de venta
				resultado.get(linea.split("%")[1]).add(importeVenta);				
			}
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	//Este método está relacionado con los dos anteriores, ya que recoge el HashMap con las ventas parciales
	//de cada vendedor que generaron dichos métodos y devuelve un HashMap pero son la sumatoria de las ventas totales
	public HashMap<String, Float> resumenVentasTotalesPorVendedor1(HashMap<String, ArrayList<Float>> listaVendedoresVentas){
		HashMap<String, Float> resultado = new HashMap<String, Float>();
		//Necesitamos dos bucle for, este primero recorre cada clave del HashMap, la cual representa
		//un codigo de vendedor.
		for (String clave : listaVendedoresVentas.keySet()) {
			//una vez estamos en el codigo de vendedor, recorremos su valor que no es más que el ArrayList
			//de cada una de las ventas que ha tenido dicho vendedor. y vamos sumando cada uno de esos valores o ventas
			//para aladirlos a nuestro nuevo HashMap de salida que en este caso ya no es un ArrayList en la parte del valor
			//sino un Float que va almacenando el total de ventas.
			for (Float valor : listaVendedoresVentas.get(clave)) {
				//La primera vez que vamos añadir a nuestro HashMap de salida un importe de venta este debería al menos tener
				//el valor de cero porque ahora mismo es null y no nos va a dejar acumular valores partiendo de null.
				//es por eso que preguntamos si el valor que corresponde a la clave del vendedor es null (este caso se dará la primera vez)
				//entonces inciamos el valor asociado a ese vendedor con su primera venta y así la segunda vez que accedemos
				//a la clave del vendedor, en la parte de valor ya habrá un primera venta y podrá seguir acumulando ventas.
				//otra manera de hacerlo y no poner este if, sería antes de entrar en estos dos bucles for que recorren el HashMap que nos
				//viene por parámetros. Tendríamso que haber recorrido nuestro HashMap de salida llamado resultado y en el valor Float
				//haberle asignado cero y así, al no estar a null la primera vez que metemos el primero importe de venta nos hubiese funcinonado
				if (resultado.get(clave) == null) {
					resultado.put(clave, valor);
				} else {
					resultado.put(clave, resultado.get(clave) + valor);
				}			
			}		
		}
	
		return resultado;
	}

	//Este método nos devuelve exáctamente lo mismo que el anterior, lo único que en vez de hacerlo de una manera más directa
	//creamos la variable acumuladoVentas para que se vea algo más claro
	public HashMap<String, Float> resumenVentasTotalesPorVendedor2(HashMap<String, ArrayList<Float>> ventas) {
		HashMap<String, Float> resultado = new HashMap<String, Float>();
		// recorremos el HashMap de entrada creando el de salida
		//para ello añadimos las claves del HashMap de entrada a un Set con todas las claves
		Set<String> claves = ventas.keySet();
		for (String clave : claves) {
			//Las claves del HM de entrada representan a los vendedores, una vez dentro de este for obtenemos el valor
			//de cada clave que no es más que el ArrayList de cada una de sus ventas y los vamos recogiendo en la 
			//variable listaVentas.
			ArrayList<Float> listaVentas = ventas.get(clave);
			float acumuladoVentas = 0;
			//Ahora con todas las ventas de un vendedor concreto dentro de la variable listaVentas
			//recorremos dicho ArrayList para ir sumando cada uno de los importes y asignarlos al acumulador acumuladoVentas
			for (Float importe : listaVentas)
				acumuladoVentas += importe;
			//Una vez hemos tenemos el acumulado de todas las ventas de un vendedor concreto
			//la añadimos al HashMap de salida donde la clave es el vendedor y el valor es el total de ventas
			resultado.put(clave, acumuladoVentas);
		}
		return resultado;
	}
	
	//************************* ESTUDIANTES **************************
	
	public ArrayList<Estudiante> leerObjetoEnficheroConDevolucion(String rutaFichero) {
		ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
			try {
				FileInputStream fis = new FileInputStream(rutaFichero);
				ObjectInputStream ois = new ObjectInputStream(fis);
				while (fis.available() > 0) {
					
					Estudiante obj = (Estudiante) ois.readObject();
					estudiantes.add(obj);
					System.out.println("---->" + obj.toString());
				}
				fis.close();
				ois.close();
				
				
			} catch (FileNotFoundException e) {
				System.out.println("Fichero no encontrado");
			} catch (IOException e) {
				System.out.println("Error de entrada salida");
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFound");
			}
			return estudiantes;
	}
	
	
	//************************* PERSONAS **************************
	
	//Lee un fichero de texto con informacion de Personas, formateado de la siguiente manera:
	//DNI, Nombre, Fecha Nacimiento y Sexo.
	//Una vez leído metemos los datos, los añadimos a un HashMap donde la clave es el DNI y el valor toda
	//la línea String de datos leída en el fichero de texto.
	public HashMap<String,String> leerFicheroTextoPersonasToHashMap(String rutaFichero){
		HashMap<String,String> resultado = new HashMap<String,String>();
		try {
			FileReader fr = new FileReader(rutaFichero);
			BufferedReader br = new BufferedReader(fr);
			String strLine;
			while ((strLine = br.readLine()) != null) {			
				resultado.put(strLine.split("&&")[0], strLine);
				System.out.println(strLine);
			}
			fr.close();
			br.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}	
		return resultado;	
	}
	
	//Leemos el fichero personas.txt como en el anterior método, pero en este caso vamos añadiendo cada línea
	//a un ArrayList, en cada posición o índice del ArrayList hay una línea de texto del fichero
	public ArrayList<String> leerFicheroTextoPersonasToArrayList(String rutaFichero){
		ArrayList<String> resultado = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(rutaFichero);
			BufferedReader br = new BufferedReader(fr);
			String strLine;
			while ((strLine = br.readLine()) != null) {				
				resultado.add(strLine);
				System.out.println(strLine);
			}
			fr.close();
			br.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}	
		return resultado;	
	}
	
	
	//*********************** COMUNIDADES AUTONOMAS ********************************
	
	
	public String[] leerComunidadesAutonomasTxt() {
		String[] comunidades = new String[19];
		int index = 0;
		try {
			// Abrir el fichero
			FileReader fr = new FileReader("ficheros/comunidades.txt");
			BufferedReader br = new BufferedReader(fr);
			String linea;
			
			// Leer el fichero linea a linea
			while ((linea = br.readLine()) != null) {
				comunidades[index] = linea.split("%")[1];
				index++;
			}
			fr.close();//cierra el fichero
			br.close();//cierra el buffer
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}return comunidades;
	}
	
	
	public String[] leerComunidadesAutonomasTxt2() {
		String[] comunidades = new String[19];
		int index = 0;
		try {
			// Abrir el fichero
			FileReader fr = new FileReader("ficheros/comunidades.txt");
			BufferedReader br = new BufferedReader(fr);
			String linea;
			// Leer el fichero linea a linea
			linea = br.readLine();
			//Este while sería con la condición tradicional sin usar una vaiarable dentro
			//a la que asignamos el valor de la linea.
			while (linea != null) {
				comunidades[index] = linea.split("%")[1];
				index++;
				linea = br.readLine();
			}
			fr.close();//cierra el fichero
			br.close();//cierra el buffer
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}return comunidades;
	}
	
	
	public String[] leerProvinciasTxt() {
		String[] provincias = new String[52];
		int index = 0;
		try {
			// Abrir el fichero
			FileReader fr = new FileReader("ficheros/provincias.txt");
			BufferedReader br = new BufferedReader(fr);
			String linea;
			
			// Leer el fichero linea a linea
			while ((linea = br.readLine()) != null) {
				/*String provincia = strLine.split("%")[0];
				String comunidadAutonomaDeLaProvincia = strLine.split("%")[1];
				String habitantesDeLaProvincia = strLine.split("%")[2];
				resultado[index] = provincia + "%" + comunidadAutonomaDeLaProvincia + "%" + habitantesDeLaProvincia;*/
				provincias[index] = linea;
				index++;
			}
			fr.close();//cierra el fichero
			br.close();//cierra el buffer
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return provincias;
	}
	
	
	public void mostrarDatosComAutonomasProvinciasHabitantes(HashMap<Integer,ArrayList<Integer>> habitantes, HashMap<Integer,ArrayList<String>> nombresProvincias, String[] nombreComunidadesAutonomas ) {
		int subTotal = 0;
		int total = 0;
		System.out.println("***************************************************");
		System.out.println("******Comunidades Autónomas y sus Provincias*******");
		System.out.println("***************************************************");
		for (int indexComunidad = 0; indexComunidad < habitantes.size(); indexComunidad++) {
			System.out.println("----  " + nombreComunidadesAutonomas[indexComunidad] + "  ----");
			for (int indexProvincia = 0; indexProvincia < habitantes.get(indexComunidad).size(); indexProvincia++) {			
				System.out.print(nombresProvincias.get(indexComunidad).get(indexProvincia) + ":   \t");
				int numHabitantes = habitantes.get(indexComunidad).get(indexProvincia);
				subTotal += numHabitantes;
				System.out.println(numHabitantes);
					
			}
			System.out.println("TOTAL de habitantes de " + nombreComunidadesAutonomas[indexComunidad] + " = " + subTotal);
			total += subTotal;
			subTotal = 0;
			System.out.println();
		}
		System.out.println("TOTAL de habitantes de España = " + total);
	}
	
	
	public void mostrarDatosComAutonomasProvinciasHabitantes2(HashMap<Integer,ArrayList<Integer>> habitantes, HashMap<Integer,ArrayList<String>> nombresProvincias, String[] nombreComunidadesAutonomas ) {
		int subTotal = 0;
		int total = 0;
		
		System.out.println("***************************************************");
		System.out.println("******Comunidades Autónomas y sus Provincias*******");
		System.out.println("***************************************************");
		
		for ( Integer indexComunidad : habitantes.keySet()) {
			int indexProvincia =0;
			System.out.println("----  " + nombreComunidadesAutonomas[indexComunidad] + "  ----");
			ArrayList<Integer> provincias = habitantes.get(indexComunidad);
			for (Integer cantidadHabitantes : provincias) {				
				System.out.print(nombresProvincias.get(indexComunidad).get(indexProvincia) + ":   \t");
				subTotal += cantidadHabitantes;
				System.out.println(cantidadHabitantes);
				indexProvincia++;
			}
			System.out.println("TOTAL de habitantes de " + nombreComunidadesAutonomas[indexComunidad] + " = " + subTotal);
			total += subTotal;
			subTotal = 0;
			System.out.println();
		}
		
		System.out.println("TOTAL de habitantes de España = " + total);
	}
	
	
	
	//Leemos dos ficheros de textos llamados provincias.txt y comunidades.txt
	//debemos sacar los datos de los habitantes empadronados de cada provincia pero
	//agrupadas cada una por su comunidad autonoma, con el total de habitantes por
	//Comunidad autonoma
	public void leerFicheroTextoProvinciasComAutoYListarDatos() {
		String[] comunidadesAutonomas = leerComunidadesAutonomasTxt();
		String[] datosTodasLasProvincias = leerProvinciasTxt();
		HashMap<Integer,ArrayList<Integer>> comunidadesProvinciasHabitantes = new HashMap<Integer,ArrayList<Integer>>();
		for (int i = 0; i < comunidadesAutonomas.length; i++) {
				ArrayList<Integer> listadoProvincias = new ArrayList<Integer>();
				comunidadesProvinciasHabitantes.put(i, listadoProvincias);
		}

		//El siguiente HashMap llamado nombresProvincias almacena en su clave el indice de la comunidad
		//y en el valor un ArrayList de String donde cada String es el nombre de a provincia
		HashMap<Integer,ArrayList<String>> nombresProvincias = new HashMap<Integer,ArrayList<String>>();

			//Recorremos el Array de String que nos devolvió el método leerProvinciasTxt()
			
			for (String datosUnaProvincia : datosTodasLasProvincias) {
				String[] datosUnaProvinciaSeparados = datosUnaProvincia.split("%");				
				int indiceComunidadAutonoma = Integer.parseInt(datosUnaProvinciaSeparados[2])-1;			
				int habitantesUnaProvincia = Integer.parseInt(datosUnaProvinciaSeparados[3]);				
				//añadimos un condicional que solo cree un ArrayList la primera vez
				//cuando estamos en una comunidad autonoma y no nos cree ninguno mas
				//cuando volvamos a tener que añadir datos a la misma comunidad autonoma.
				if (nombresProvincias.get(indiceComunidadAutonoma) == null) {
					ArrayList<String> provincias = new ArrayList<String>();
					nombresProvincias.put(indiceComunidadAutonoma,provincias);
				}
				//añadimos al HashMap de provincias, el nombre que le corresponde en la comunidad atonoma
				//a la que pertence.
				nombresProvincias.get(indiceComunidadAutonoma).add(datosUnaProvinciaSeparados[1]);				
				//añadimos el número de habitantes a la provincia en la comunidad autonoma que corresponde
				comunidadesProvinciasHabitantes.get(indiceComunidadAutonoma).add(habitantesUnaProvincia);
			}
			
		//mostrarDatosComAutonomasProvinciasHabitantes(comunidadesProvinciasHabitantes,nombresProvincias, comunidadesAutonomas);
		mostrarDatosComAutonomasProvinciasHabitantes2(comunidadesProvinciasHabitantes,nombresProvincias, comunidadesAutonomas);
		System.out.println("BreakPoint");
		
	}
	
	
	public void mostrarDatosComAutonomasProvinciasHabitantes3( HashMap<Integer,ArrayList<String>> datosProvincias, String[] nombreComunidadesAutonomas ) {
		int subTotal = 0;
		int total = 0;
		
		System.out.println("***************************************************");
		System.out.println("******Comunidades Autónomas y sus Provincias*******");
		System.out.println("***************************************************");
		
		for ( Integer indexComunidad : datosProvincias.keySet()) {
			@SuppressWarnings("unused")
			int indexProvincia = 0;
			System.out.println("\u001B[47m\u001B[32m----------   \t" + nombreComunidadesAutonomas[indexComunidad] + "   \t----------\u001b[0m");
			ArrayList<String> provincias = datosProvincias.get(indexComunidad);
			for (String datosProvinciaHabitantes : provincias) {
				String[] datos = datosProvinciaHabitantes.split("%");
				String nombreProvincia = datos[0];
				int cantidadHabitantes = Integer.parseInt(datos[1]);
				System.out.print(nombreProvincia + ":   \t");
				subTotal += cantidadHabitantes;
				System.out.println(cantidadHabitantes);
				indexProvincia++;
			}
			
			System.out.println("\u001B[36mTOTAL de habitantes de " + nombreComunidadesAutonomas[indexComunidad] + " = \u001B[41m\u001B[37m" + subTotal + "\u001b[0m");
			total += subTotal;
			subTotal = 0;
			System.out.println();
		}
		
		System.out.println("\u001B[33mTOTAL de habitantes de España = \u001b[0m" + total);

	}
	
	
	public void leerFicheroTextoProvinciasComAutoYListarDatos2() {
		String[] comunidadesAutonomas = leerComunidadesAutonomasTxt();
		String[] datosTodasLasProvincias = leerProvinciasTxt();
		/*HashMap<Integer,ArrayList<Integer>> comunidadesProvinciasHabitantes = new HashMap<Integer,ArrayList<Integer>>();
		for (int i = 0; i < comunidadesAutonomas.length; i++) {
				ArrayList<Integer> listadoProvincias = new ArrayList<Integer>();
				comunidadesProvinciasHabitantes.put(i, listadoProvincias);
		}*/

		//El siguiente HashMap llamado nombresProvincias almacena en su clave el indice de la comunidad
		//y en el valor un ArrayList de String donde cada String es el nombre de a provincia
		HashMap<Integer,ArrayList<String>> datosProvincias = new HashMap<Integer,ArrayList<String>>();

			//Recorremos el Array de String que nos devolvió el método leerProvinciasTxt()
			
			for (String datosUnaProvincia : datosTodasLasProvincias) {
				String[] datosUnaProvinciaSeparados = datosUnaProvincia.split("%");
				String nombreProvincia = datosUnaProvinciaSeparados[1];
				int indiceComunidadAutonoma = Integer.parseInt(datosUnaProvinciaSeparados[2])-1;			
				int habitantesUnaProvincia = Integer.parseInt(datosUnaProvinciaSeparados[3]);				
				//añadimos un condicional que solo cree un ArrayList la primera vez
				//cuando estamos en una comunidad autonoma y no nos cree ninguno mas
				//cuando volvamos a tener que añadir datos a la misma comunidad autonoma.
				if (datosProvincias.get(indiceComunidadAutonoma) == null) {
					ArrayList<String> provincias = new ArrayList<String>();
					datosProvincias.put(indiceComunidadAutonoma,provincias);
				}
				//añadimos al HashMap de provincias, el nombre que le corresponde en la comunidad atonoma
				//a la que pertence.
				datosProvincias.get(indiceComunidadAutonoma).add(nombreProvincia + "%" + habitantesUnaProvincia);				
				//añadimos el número de habitantes a la provincia en la comunidad autonoma que corresponde
				//comunidadesProvinciasHabitantes.get(indiceComunidadAutonoma).add(habitantesUnaProvincia);
			}
			
		mostrarDatosComAutonomasProvinciasHabitantes3(datosProvincias, comunidadesAutonomas);
		System.out.println("BreakPoint");
		
	}
	
}
