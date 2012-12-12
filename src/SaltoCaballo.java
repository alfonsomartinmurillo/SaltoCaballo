
import java.util.ArrayList;

import java.io.*;
import java.util.Date;;

public class SaltoCaballo {

	/**
	 * @param args
	 */
	
	//Variables de tipo miembro público
	static Integer N; //Dimensión del tablero de Ajedrez.
	static Integer xInicio; //Posición x inicial
	static Integer yInicio; //Posición y inicial
	static Boolean modoTraza; //Establece si tenemos que imprimir todos los estados por los que pasa el tablero.
	static Boolean modoTodasSoluciones; //Establece si debemos de obtener todas las soluciones o únicamente la primera.
	static ArrayList<EstadoPartida> listaSoluciones;
	static int longCamino;
	
	//Otras variables
	static PrintStream miConsola;
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException 
	{
		
		//variables temporales
		int x=0;
		
		//CONFIGURACIONES POR DEFECTO DE LA APLICACIÓN
		N=8; 
		xInicio=1; 
		yInicio=1; 
		modoTraza=false;
		modoTodasSoluciones=false; 
		longCamino=0;
		
		//FICHERO PARA GRABAR LA SALIDA
		File file = new File("salida.txt");
		FileOutputStream fos = new FileOutputStream(file);
		miConsola=new PrintStream(fos,true,"UTF-8");
		//miConsola=new PrintStream(System.out,true,"UTF-8");
		Date fechaHora=new Date();
					
		// 1 - Evaluar que los parámetros enviados son los correctos.
		if (evalParametros(args)==false)
			{
				miConsola.println("Los parámetros suministrados no son correctos");
				miConsola.println("El programa finalizará su ejecución");
				System.exit(0);
			}
		
		// 2 - configuración temporal de parámetros de entrada
				
		//Creación de la partida
		listaSoluciones= new ArrayList<EstadoPartida>();
		EstadoPartida estadoInicial=new EstadoPartida(xInicio,yInicio,N,modoTraza,modoTodasSoluciones,miConsola);
		miConsola.println(fechaHora.toString() + " Comienza el Proceso de generación de soluciones: ");
		estadoInicial.evalPartida(listaSoluciones);
		miConsola.println(fechaHora.toString() + " Finaliza el Proceso de generación de soluciones");
		
		//FINALIZAR EL PROGRAMA SI NO HEMOS ENCONTRADO SOLUCIONES
		if (listaSoluciones.size()==0)
		{
			miConsola.println(fechaHora.toString() + " No se han encontrado soluciones. El programa finalizará su ejecución.");
			System.exit(0);
		
		}
		
		miConsola.println( fechaHora.toString() + " Comienza el proceso de listado de soluciones ");
		
		for (x=0;x<listaSoluciones.size();x++)
		{
			miConsola.println(fechaHora.toString() +  " Solución Nº : " + Integer.toString(x+1) );
			miConsola.println("----------------------------------------------------------");
			
			if (modoTraza)
			{
				miConsola.println(fechaHora.toString() +  " Detalle de la traza");
				miConsola.println("----------------------------------------------------------");
				PintarSolucion(listaSoluciones.get(x));
			}
			else
			{
				
				
				listaSoluciones.get(x).PintarTablero();
				miConsola.println();
				listaSoluciones.get(x).PintarCamino();
				
			}
			
			miConsola.println();
			
		}
		
		miConsola.println(fechaHora.toString() +  " Finaliza el proceso de listado de soluciones ");
		


	}
	
	public static void PintarSolucion(EstadoPartida solPartida)
	{
		
		//variables temporales;
		int xcamino,xcaminotmp, x,y;
		boolean encontrado;
		
		//RECORRO TO-DO EL CAMINO QUE REPRESENTA LA SOLUCIÓN
		for (xcamino=0;xcamino<solPartida.Camino.length;xcamino++)
		{
			xcaminotmp=0;
			miConsola.println("----------------------------------------------------------");
			
			for (x=N-1;x>=0;x--) //lo recorro al revés para representar que el eje y crece de abajo a arriba y no de arriba a abajo 
			{
				for (y=0;y<N;y++)
				{
					encontrado=false;
					
					for (xcaminotmp=0;xcaminotmp<=xcamino;xcaminotmp++) //CADA VEZ RECORREMOS EL CAMINO DESDE EL PRINCIPIO HASTA LA POSICIÓN EVALUADA
					{
						
						
						
						if (solPartida.Camino[xcaminotmp].xPos==x && solPartida.Camino[xcaminotmp].yPos==y)
						{
							encontrado=true;
							if ((xcaminotmp+1)>9)
							{
								miConsola.print(Integer.toString(xcaminotmp+1));
							}
							else
							{
								miConsola.print(" " + Integer.toString(xcaminotmp+1));
							}
							//miConsola.print(Integer.toString(xcamino+1));
						}
								
						
					}
					
					if (encontrado==false) // si no lo he encontrado genero espacio en dicha posición
					{
						miConsola.print("  " );
					}
					if (y!=N)
					{
						miConsola.print("\t");
						
					}
				}
				
				miConsola.println(); //Salto de Linea
			
			}
		}
		
	}
	
	public static Boolean evalParametros(String[] args)
	{
		
		//FIXME -n 5 -x 3 y 3 no funciona. controlarlo.
		//FIXME -p 5 -x 3 y 3 no funciona. controlarlo.
		//TODO Implementar la gestión de parámetros
		//TODO Implementar un Try Catch que ante un error devuelva el false. Ej: pongo -n al final y no pongo valor, dará un error.
		
		//VARIABLES TEMPORALES
		int x=0;
		
		//RECORREMOS EL ARRAY DE PARÁMETROS Y EVALUAMOS
		
		try
		{
		
			while (x<args.length)
			{
								
				
				if (args[x].toString().toLowerCase().equals("-n")) //CAPTO LA DIMENSIÓN DEL TABLERO QUE SERÁ LA SIGUIENTE POSICIÓN 
				{
					N=Integer.parseInt(args[x+1]);
					x++;
				}
				else
				{
					if (args[x].toString().toLowerCase().equals("-x")) //CAPTO LA X 
					{
						xInicio=Integer.parseInt(args[x+1]);
						x++;
					}
					else
					{
						if (args[x].toString().toLowerCase().equals("-y")) //CAPTO LA y 
						{
							yInicio=Integer.parseInt(args[x+1]);
							x++;
						}
						else
						{
							if (args[x].toString().toLowerCase().equals("-t")) 
								modoTraza=true;
							else
							{
								if (args[x].toString().toLowerCase().equals("-a")) modoTodasSoluciones=true;
							}
								
						}
						
					}
					
					
				}
				
				x++;
			}
			
			//SALIDA CORRECTA
			return true;
		} 
		catch(Exception miExcepcion)
		{
			//SALIDA INCORRECTA
			return false; //ha habido algún tipo de error, por lo que debemos devolver false
		}
		
	}
	
	public EstadoPartida genCamino()
	{
		//TODO Implementar la función recursiva principal de BackTracking
		return new EstadoPartida();
		
	}
	
	//TODO generar tratamiento de excepciones para ver que está pasando
	public static ArrayList<EstadoPartida> genCompleciones(EstadoPartida EstActual)
	{

		//Variables locales
		int x,y,z,umbral=8,idxUmbral=0;
		ArrayList<EstadoPartida> ListaEstadosSiguientes = new ArrayList<EstadoPartida>();
		ArrayList<EstadoPartida> ListaEstadosSiguientesOrden = new ArrayList<EstadoPartida>();
		
		try
		{
			//Lista de Estados siguientes
			
			x=EstActual.xActual;
			y=EstActual.yActual;
			
			//Realizo la comprobación y llamada de las 8 posibles posiciones 
			
			genNuevoJuego(x-2,y-1,ListaEstadosSiguientes,EstActual);
			genNuevoJuego(x-1,y-2,ListaEstadosSiguientes,EstActual);
			genNuevoJuego(x-2,y+1,ListaEstadosSiguientes,EstActual);
			genNuevoJuego(x-1,y+2,ListaEstadosSiguientes,EstActual);
			genNuevoJuego(x+2,y-1,ListaEstadosSiguientes,EstActual);
			genNuevoJuego(x+1,y-2,ListaEstadosSiguientes,EstActual);
			genNuevoJuego(x+2,y+1,ListaEstadosSiguientes,EstActual);
			genNuevoJuego(x+1,y+2,ListaEstadosSiguientes,EstActual);
			
			//ORDENAR LA LISTA DE ESTADOS SIGUIENTES EN FUNCIÓN DEL NÚMERO DE POSICIONES VÁLIDAS
			while (ListaEstadosSiguientes.size()!=0)
			{
				for (z=0;z<ListaEstadosSiguientes.size();z++)
				{
					umbral=8;
					idxUmbral=0;
					if (ListaEstadosSiguientes.get(z).numPosValidas<=umbral) 
					{
						//almaceno el índice y el nuevo umbral
						umbral=ListaEstadosSiguientes.get(z).numPosValidas;
						idxUmbral=z;	
					}
				}
				//una vez finaliza el bucle añado el nuevo elemento, que será el menor, y lo borro de la lista original
				ListaEstadosSiguientesOrden.add(ListaEstadosSiguientes.get(idxUmbral));
				ListaEstadosSiguientes.remove(idxUmbral);
				
			}
			
			
			
			
			
		} //FIN TRY
		catch (Exception ex)
		{
			miConsola.println(ex.toString());
		}
		finally
		{
			return ListaEstadosSiguientesOrden; //Temporal
		}
		
		}
	
	public static void genNuevoJuego(int x,int y,ArrayList<EstadoPartida> ListaEstadosSiguientes, EstadoPartida EstActual)
	{
		//Variables Locales
		EstadoPartida nuevoEstadoPartida;
		
		//Comprobar que las nuevas posiciones están dentro del tablero
		if ((x>=0) && (y>=0) && (x<=EstActual.dimTablero-1) && (y<=EstActual.dimTablero-1) && (EstActual.Tablero[x][y] == 0))  
		{
			nuevoEstadoPartida=new EstadoPartida(x+1,y+1,EstActual.dimTablero,modoTraza,modoTodasSoluciones,miConsola);
			nuevoEstadoPartida.Tablero=EstActual.copiaTablero();
			nuevoEstadoPartida.Camino=EstActual.copiaCamino();
			nuevoEstadoPartida.lCamino=EstActual.lCamino+1;
			nuevoEstadoPartida.Camino[nuevoEstadoPartida.lCamino-1].xPos=x;
			nuevoEstadoPartida.Camino[nuevoEstadoPartida.lCamino-1].yPos=y;
			nuevoEstadoPartida.Tablero[x][y]=nuevoEstadoPartida.lCamino;
			ListaEstadosSiguientes.add(nuevoEstadoPartida);
			nuevoEstadoPartida.numPosValidas=getNumPosValidas(nuevoEstadoPartida);
			nuevoEstadoPartida=null;
			
		}
		return;
		
	}
	
	public static int getNumPosValidas(EstadoPartida estPartida)
	
	{
		int numPosValidas=0;
		int x,y;
		x=estPartida.xActual;
		y=estPartida.yActual;
		
		
		if ((x-2>=0) && (y-1>=0) && (x-2<=estPartida.dimTablero-1) && (y-1<=estPartida.dimTablero-1) && (estPartida.Tablero[x-2][y-1] == 0)) numPosValidas++;
		if ((x-1>=0) && (y-2>=0) && (x-1<=estPartida.dimTablero-1) && (y-2<=estPartida.dimTablero-1) && (estPartida.Tablero[x-1][y-2] == 0)) numPosValidas++;
		if ((x-2>=0) && (y+1>=0) && (x-2<=estPartida.dimTablero-1) && (y+1<=estPartida.dimTablero-1) && (estPartida.Tablero[x-2][y+1] == 0)) numPosValidas++;
		if ((x-1>=0) && (y+2>=0) && (x-1<=estPartida.dimTablero-1) && (y+2<=estPartida.dimTablero-1) && (estPartida.Tablero[x-1][y+2] == 0)) numPosValidas++;
		if ((x+2>=0) && (y-1>=0) && (x+2<=estPartida.dimTablero-1) && (y-1<=estPartida.dimTablero-1) && (estPartida.Tablero[x+2][y-1] == 0)) numPosValidas++;
		if ((x+1>=0) && (y-2>=0) && (x+1<=estPartida.dimTablero-1) && (y-2<=estPartida.dimTablero-1) && (estPartida.Tablero[x+1][y-2] == 0)) numPosValidas++;
		if ((x+2>=0) && (y+1>=0) && (x+2<=estPartida.dimTablero-1) && (y+1<=estPartida.dimTablero-1) && (estPartida.Tablero[x+2][y+1] == 0)) numPosValidas++;
		if ((x+1>=0) && (y+2>=0) && (x+1<=estPartida.dimTablero-1) && (y+2<=estPartida.dimTablero-1) && (estPartida.Tablero[x+1][y+2] == 0)) numPosValidas++;

		return numPosValidas;
	}
	
	public boolean evalCondicionesPoda()
	{
		//
		return true;
		
	}

	
	

}
