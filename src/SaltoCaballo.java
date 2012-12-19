
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
		//listaSoluciones= new ArrayList<EstadoPartida>();
		//COMIENZA MI PARTIDA
		EstadoPartida estadoInicial=new EstadoPartida(xInicio,yInicio,N,modoTraza,modoTodasSoluciones,miConsola);
		
		miConsola.println(fechaHora.toString() + " Inicio del Proceso de generación de soluciones: ");
		estadoInicial.evalPartida();
		miConsola.println(fechaHora.toString() + " Finalización del Proceso de generación de soluciones");
		
		
		
		
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
	

	

}
