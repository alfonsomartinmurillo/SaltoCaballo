
import java.lang.*;
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
	
	//Otras variables
	static PrintStream miConsola;
	
	public static void main(String[] args) throws UnsupportedEncodingException 
	{
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
		N=16;
		EstadoPartida Partida=new EstadoPartida(3,3,16);
		Partida.PintarTablero();

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
	
	public EstadoPartida[] getCompleciones()
	{
		//TODO Establecer la lista de Compleciones
		return new EstadoPartida[1]; //Temporal
		
	}
	
	public boolean evalCondicionesPoda()
	{
		//
		return true;
		
	}

	
	

}
