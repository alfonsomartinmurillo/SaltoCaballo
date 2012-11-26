
import java.lang.*;
import java.util.ArrayList;
import java.io.*;

public class SaltoCaballo {

	/**
	 * @param args
	 */
	
	//Variables de tipo miembro pœblico
	static Integer N; //Dimensi—n del tablero de Ajedrez.
	Integer xInicio; //Posici—n x inicial
	Integer yInicio; //Posici—n y inicial
	Boolean modoTraza; //Establece si tenemos que imprimir todos los estados por los que pasa el tablero.
	Boolean modoTodasSoluciones; //Establece si debemos de obtener todas las soluciones o œnicamente la primera.
	static ArrayList<EstadoPartida> listaSoluciones;
	
	//Otras variables
	static PrintStream miConsola;
	
	public static void main(String[] args) throws UnsupportedEncodingException 
	{
		
		//variables temporales
		int x=0;
		
		miConsola=new PrintStream(System.out,true,"UTF-8");
				
		// 1 - Evaluar que los par‡metros enviados son los correctos.
		if (evalParametros(args)==false)
			{
				miConsola.println("Los par‡metros suministrados no son correctos");
				miConsola.println("El programa finalizar‡ su ejecuci—n");
				System.exit(0);
			}
		
		// 2 - configuraci—n temporal de par‡metros de entrada
				
		//Creaci—n de la partida
		N=5;
		listaSoluciones= new ArrayList<EstadoPartida>();
		EstadoPartida estadoInicial=new EstadoPartida(1,1,6);
		EstadoPartida estadoFinal=estadoInicial.evalPartida(listaSoluciones);
		miConsola.println("Nœmero de Soluciones: " + Integer.toString(listaSoluciones.size()));
		
		for (x=0;x<listaSoluciones.size();x++)
		{
			miConsola.println("Soluci—n " + Integer.toString(x+1));
			listaSoluciones.get(x).PintarTablero();
		}
		


	}
	
	public static Boolean evalParametros(String[] args)
	{
		//TODO Implementar la gesti—n de par‡metros
		return true;
	}
	
	public EstadoPartida genCamino()
	{
		//TODO Implementar la funci—n recursiva principal de BackTracking
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
		//Realizo la comprobaci—n y llamada de las 8 posibles posiciones 
		
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
		
		//Comprobar que las nuevas posiciones est‡n dentro del tablero
		if ((x>=0) && (y>=0) && (x<=EstActual.dimTablero-1) && (y<=EstActual.dimTablero-1) && (EstActual.Tablero[x][y] == 0))  
		{
			nuevoEstadoPartida=new EstadoPartida(x+1,y+1,EstActual.dimTablero);
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
