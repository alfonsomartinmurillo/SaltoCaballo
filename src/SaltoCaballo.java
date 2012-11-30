
import java.lang.*;
import java.util.ArrayList;
import java.io.*;

public class SaltoCaballo {

	/**
	 * @param args
	 */
	
	//Variables de tipo miembro p�blico
	static Integer N; //Dimensi�n del tablero de Ajedrez.
	static Integer xInicio; //Posici�n x inicial
	static Integer yInicio; //Posici�n y inicial
	static Boolean modoTraza; //Establece si tenemos que imprimir todos los estados por los que pasa el tablero.
	static Boolean modoTodasSoluciones; //Establece si debemos de obtener todas las soluciones o �nicamente la primera.
	static ArrayList<EstadoPartida> listaSoluciones;
	
	//Otras variables
	static PrintStream miConsola;
	
	public static void main(String[] args) throws UnsupportedEncodingException 
	{
		
		//variables temporales
		int x=0;
		
		//CONFIGURACIONES POR DEFECTO DE LA APLICACI�N
		N=8; 
		xInicio=1; 
		yInicio=1; 
		modoTraza=false;
		modoTodasSoluciones=false; 
		
		
		miConsola=new PrintStream(System.out,true,"UTF-8");
				
		// 1 - Evaluar que los par�metros enviados son los correctos.
		if (evalParametros(args)==false)
			{
				miConsola.println("Los par�metros suministrados no son correctos");
				miConsola.println("El programa finalizar� su ejecuci�n");
				System.exit(0);
			}
		
		// 2 - configuraci�n temporal de par�metros de entrada
				
		//Creaci�n de la partida
		listaSoluciones= new ArrayList<EstadoPartida>();
		EstadoPartida estadoInicial=new EstadoPartida(xInicio,yInicio,N,modoTraza,modoTodasSoluciones);
		estadoInicial.evalPartida(listaSoluciones);
		
		//FINALIZAR EL PROGRAMA SI NO HEMOS ENCONTRADO SOLUCIONES
		if (listaSoluciones.size()==0)
		{
			miConsola.println("No se han encontrado soluciones. El programa finalizar� su ejecuci�n.");
			System.exit(0);
		
		}
		
		if (modoTodasSoluciones) //TENGO QUE IMPRIMIR TO-DO EL ARRAY DE SOLUCIONES
		{
		
			for (x=0;x<listaSoluciones.size();x++)
			{
				if (modoTraza)
				{
					
				}
				else
				{
					listaSoluciones.get(x).PintarTablero();	
				}
				
			}
		}
		else
		{
			if (listaSoluciones.size()!=0) //QUE POR LO MENOS HAYA UNA SOLUCI�N
			{
				if (modoTraza)
				{
					
				}
				else
				{
					listaSoluciones.get(0).PintarTablero();
				}
			}
		}
		


	}
	
	public static void PintarSolucion(EstadoPartida solPartida)
	{
		
		//variables temporales;
		int xcamino,x,y;
		
		//RECORRO TO-DO EL CAMINO QUE REPRESENTA LA SOLUCI�N
		for (xcamino=0;xcamino<solPartida.Camino.length;xcamino++)
		{
			
			for (x=N-1;x>=0;x--) //lo recorro al rev�s para representar que el eje y crece de abajo a arriba y no de arriba a abajo 
			{
				for (y=0;y<N;y++)
				{
					if (solPartida.Camino[xcamino].xPos==x && solPartida.Camino[xcamino].yPos==y)
					{
						if ((xcamino++)>9)
						{
							miConsola.print(Integer.toString(xcamino++));
						}
						else
						{
							miConsola.print(" " + Integer.toString(xcamino++));
						}
						miConsola.print(Integer.toString(xcamino++));
					}
					else
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
		//TODO Implementar la gesti�n de par�metros
		//TODO Implementar un Try Catch que ante un error devuelva el false. Ej: pongo -n al final y no pongo valor, dar� un error.
		
		//VARIABLES TEMPORALES
		int x=0;
		
		//RECORREMOS EL ARRAY DE PAR�METROS Y EVALUAMOS
		
		try
		{
		
			while (x<args.length)
			{
								
				
				if (args[x].toString().toLowerCase().equals("-n")) //CAPTO LA DIMENSI�N DEL TABLERO QUE SER� LA SIGUIENTE POSICI�N 
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
			return false; //ha habido alg�n tipo de error, por lo que debemos devolver false
		}
		
	}
	
	public EstadoPartida genCamino()
	{
		//TODO Implementar la funci�n recursiva principal de BackTracking
		return new EstadoPartida();
		
	}
	
	public static ArrayList<EstadoPartida> genCompleciones(EstadoPartida EstActual)
	{

		//Variables locales
		int x,y;
		//Lista de Estados siguientes
		ArrayList<EstadoPartida> ListaEstadosSiguientes = new ArrayList<EstadoPartida>();
		x=EstActual.xActual;
		y=EstActual.yActual;
		//Realizo la comprobaci�n y llamada de las 8 posibles posiciones 
		
		genNuevoJuego(x-2,y-1,ListaEstadosSiguientes,EstActual);
		genNuevoJuego(x-1,y-2,ListaEstadosSiguientes,EstActual);
		genNuevoJuego(x-2,y+1,ListaEstadosSiguientes,EstActual);
		genNuevoJuego(x-1,y+2,ListaEstadosSiguientes,EstActual);
		genNuevoJuego(x+2,y-1,ListaEstadosSiguientes,EstActual);
		genNuevoJuego(x+1,y-2,ListaEstadosSiguientes,EstActual);
		genNuevoJuego(x+2,y+1,ListaEstadosSiguientes,EstActual);
		genNuevoJuego(x+1,y+2,ListaEstadosSiguientes,EstActual);
		
		return ListaEstadosSiguientes; //Temporal
	}
	
	public static void genNuevoJuego(int x,int y,ArrayList<EstadoPartida> ListaEstadosSiguientes, EstadoPartida EstActual)
	{
		//Variables Locales
		EstadoPartida nuevoEstadoPartida;
		
		//Comprobar que las nuevas posiciones est�n dentro del tablero
		if ((x>=0) && (y>=0) && (x<=EstActual.dimTablero-1) && (y<=EstActual.dimTablero-1) && (EstActual.Tablero[x][y] == 0))  
		{
			nuevoEstadoPartida=new EstadoPartida(x+1,y+1,EstActual.dimTablero,modoTraza,modoTodasSoluciones);
			nuevoEstadoPartida.Tablero=EstActual.copiaTablero();
			nuevoEstadoPartida.Camino=EstActual.copiaCamino();
			nuevoEstadoPartida.lCamino=EstActual.lCamino+1;
			nuevoEstadoPartida.Camino[nuevoEstadoPartida.lCamino-1].xPos=x;
			nuevoEstadoPartida.Camino[nuevoEstadoPartida.lCamino-1].yPos=y;
			nuevoEstadoPartida.Tablero[x][y]=nuevoEstadoPartida.lCamino;
			ListaEstadosSiguientes.add(nuevoEstadoPartida);
			//System.out.println();
			//nuevoEstadoPartida.PintarTablero();
			nuevoEstadoPartida=null;
			
		}
		return;
		
	}
	
	public boolean evalCondicionesPoda()
	{
		//
		return true;
		
	}

	
	

}
