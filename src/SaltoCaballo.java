
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
	static String nomFicheroSalida;
	static int paramSalida;
	
	//Otras variables
	static PrintStream miConsola;
	
	//TODO Limpiar y Comentar
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
		nomFicheroSalida=new String("salida.txt");
		paramSalida=0; //POR DEFECTO LA SALIDA ES POR CONSOLA. 0 - CONSOLA 1 - FICHERO.
		
		//OBJETO FECHAHORA PARA IR GRABANDO LOS EVENTOS DE LA APLICACIÓN
		Date fechaHora=new Date();
						
		// 1 - Evaluar que los parámetros enviados son los correctos.
		if (evalParametros(args)==false)
			{
				
				System.out.println("Los parametros suministrados no son correctos");
				System.out.println("El programa finalizará su ejecución");
				System.exit(0);
			}
		
		//CONFIGURACIÓN DE CONSOLA DE SALIDA
		if (paramSalida==0)
		{
			miConsola=new PrintStream(System.out,true,"UTF-8");
			
		}
		else
		{
			File file = new File(nomFicheroSalida);
			FileOutputStream fos = new FileOutputStream(file);
			miConsola=new PrintStream(fos,true,"UTF-8");	
		}
				
				
		//CREACIÓN DE LA PARTIDA
		listaSoluciones= new ArrayList<EstadoPartida>();
		//COMIENZA MI PARTIDA
		EstadoPartida estadoInicial=new EstadoPartida(xInicio,yInicio,N,modoTraza,modoTodasSoluciones,miConsola);
		
		miConsola.println(fechaHora.toString() + " Inicio del Proceso de generación de soluciones: ");
		estadoInicial.evalPartida();
		miConsola.println(fechaHora.toString() + " Finalización del Proceso de generación de soluciones");
		
		//FINALIZAR EL PROGRAMA SI NO HEMOS ENCONTRADO SOLUCIONES
		if (listaSoluciones.size()==0)
		{
			miConsola.println(fechaHora.toString() + " No se han encontrado soluciones. El programa finalizará su ejecución.");
			System.exit(0);
		
		}
		
		
	}
	
	//TODO Limpiar y Comentar
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
							//OBTENDO SI TRABAJO O NO EN MODO TRAZA
							if (args[x].toString().toLowerCase().equals("-t")) modoTraza=true;
							else
							{
								//OBTENGO SI DESEO TODAS LAS SOLUCIONES O NO
								if (args[x].toString().toLowerCase().equals("-a")) modoTodasSoluciones=true;
								else
								{
									if (args[x].toString().toLowerCase().equals("-f")) //CAPTO EL NOMBRE DEL FICHERO 
									{
										paramSalida=1; //DESEO LA SALIDA POR FICHERO
										nomFicheroSalida=args[x+1];
										x++;
									}
									
								}
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
	
	//TODO Limpiar y Comentar
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
			//LIMPIO Y DEVUELVO
			ListaEstadosSiguientes=null;
			return ListaEstadosSiguientesOrden; //Temporal
		}
		
		}
	
	//TODO Limpiar y Comentar
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
			//INTENTO ACORTAR EL NÚMERO DE ESTADOS SIGUIENTES A EVALUAR
			nuevoEstadoPartida.numPosValidas=getNumPosValidas(nuevoEstadoPartida);
			//SI NO HAY POSICIONES SIGUIENTES Y NO ESTOY EN UNA SOLUCIÓN AGREGO. 
			if (((nuevoEstadoPartida.numPosValidas==0) && (nuevoEstadoPartida.lCamino<nuevoEstadoPartida.dimTablero))==false)
			{
				ListaEstadosSiguientes.add(nuevoEstadoPartida);
			}
			
				
			nuevoEstadoPartida=null;
			
		}
		return;
		
	}
	
	//TODO Limpiar y Comentar
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
	
	
	//TODO Limpiar y Comentar
	public boolean evalCondicionesPoda()
	{
		//
		return true;
		
	}

	
	

}
