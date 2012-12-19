
import java.io.FileNotFoundException;
import java.io.PrintStream;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import sun.management.counter.LongCounter;

public class EstadoPartida

{

	public int Tablero[][];
	public Casilla Camino[];
	public int lCamino;
	public int xActual;
	public int yActual;
	public boolean modoTraza;
	public boolean modotodasSoluciones;
	public int dimTablero;
	public int dimTableroidx0;
	public boolean esSolucion;
	public  PrintStream miConsola;
	public int xDesp[];
	public int numSoluciones;
	public int yDesp[];
	public long numLlamadas;
	
	
	//CONSTRUCTOR POR DEFECTO.
	public EstadoPartida()	
	{
		try
		{
			initEstadoPartida();
		}
		catch (UnsupportedEncodingException exc)
		{
			System.out.println(exc.toString());
		}
		catch (FileNotFoundException exc)
		{
			
			System.out.println(exc.toString());
		}
		
	}
	
	//CONSTRUCTOR DE LA CLASE DE TIPO ESTADOPARTIDA EN BASE A PARÁMETROS NECESARIOS
	public EstadoPartida(int xIni,int yIni, int dim, Boolean mTraza, Boolean tSoluciones,PrintStream miConsola)	
	{
		//VARIABLES TEMPORALES
		int idx;
		int idy;
		
		//CUERPO
		
		//CONVERSIÓN DE COORDENADAS A ÍNDICE 0
		xActual=xIni-1; //Posición x de partida. Resto -1 para adaptarlo a matriz con índice que empieza en 0.  
		yActual=yIni-1; //Posición y de partida. Resto -1 para adaptarlo a matriz con índice que empieza en 0.
		
		modoTraza=mTraza;
		modotodasSoluciones=tSoluciones;
		this.miConsola=miConsola;
		Tablero=new int[dim][dim]; //Creación de la matriz que representa el tablero;
		
		//Establezco la primera posición del caballo en el tablero
		Tablero[xActual][yActual]=1;
		dimTablero=dim; //Establezco la dimensión del Tablero
		
		lCamino=1;
		//llamada al constructor por defecto
		try
		{
			initEstadoPartida();
		}
		catch (UnsupportedEncodingException exc)
		{
			miConsola.println(exc.toString());
		} 
		catch (FileNotFoundException exc)
		{
			
			miConsola.println(exc.toString());
		}
		
		
	}
	
	
	//Función que focaliza todo lo común a los diferentes constructores
	public void initEstadoPartida()	throws UnsupportedEncodingException, FileNotFoundException
	{
		//Función que focaliza todo lo común a los diferentes constructores
		esSolucion=false;
		numSoluciones=0;
		xDesp=new int[8];
		yDesp=new int[8];
		xDesp[0]=-2;xDesp[1]=-1;xDesp[2]=-2;xDesp[3]=-1;xDesp[4]=2;xDesp[5]=1;xDesp[6]=2;xDesp[7]=1;
		yDesp[0]=-1;yDesp[1]=-2;yDesp[2]=1;yDesp[3]=2;yDesp[4]=-1;yDesp[5]=-2;yDesp[6]=1;yDesp[7]=2;
		dimTableroidx0=dimTablero-1;
		numLlamadas=0;
		
	}
	
	public EstadoPartida evalPartida()
	{
		int x;
		int xdesptemp;
		int ydesptemp;
		int xActualAnt;
		int yActualAnt;
		
		numLlamadas++;
		if ((numLlamadas % 100000000)==0)
		{
			miConsola.println("Número de llamadas: " + Long.toString(numLlamadas) );
			miConsola.println("Longitud actual: " + Integer.toString(lCamino));
		}
		//SI ESTOY EN MODO TRAZA SIEMPRE IMPRIMO TABLERO, INDEPENDIENTEMENTE DE QUE ME ENCUENTRE CON UNA SOLUCIÓN O NO
		if (modoTraza)
		{
			miConsola.println("------------------------------------------------------------------");
			PintarTablero();
			miConsola.println();
		}
		
		
		//ENCONTRAMOS UNA SOLUCIÓN
		if (lCamino==dimTablero*dimTablero)
		{
			numSoluciones++;
			miConsola.println("Solución encontrada número: "+ Integer.toString(numSoluciones));
			//IMPRIMO EL TABLERO ÚNICAMENTE SI NO ESTOY EN MODO TRAZA, YA QUE SI NO YA LO TENDRÍA EN PANTALLA
			if (modoTraza==false)
			{
				miConsola.println("Número de soluciones encontradas: "+ Integer.toString(numSoluciones));
				miConsola.println("------------------------------------------------------------------");
				PintarTablero();
				miConsola.println();
			}
						
			esSolucion=true;
		}
		//OBTENCIÓN DE NUEVAS SOLUCIONES
		else
		{
			for (x=0;x<8;x++)
			{
				//COMPROBAR SI HAY QUE SEGUIR GENERANDO
				//GENERAREMOS SIEMPRE QUE, O BIEN NO HAYAMOS ENCONTRADO UNA SOLUCIÓN, O BIEN TENGAMOS QUE ENCONTRAR
				//TODAS LAS SOLUCIONES
				if (((modotodasSoluciones==false) && (esSolucion==true))==false)
				{
					xdesptemp=xActual+xDesp[x];
					ydesptemp=yActual+yDesp[x];
					//COMPROBAR QUE LA NUEVA POSICIÓN ESTÁ EN RANGO Y QUE ES UNA CASILLA NO VISITADA
					//if ((xdesptemp>=0) && (xdesptemp<=dimTableroidx0) && (ydesptemp>=0) && (ydesptemp<=dimTableroidx0))
					if (xdesptemp>=0)
					{
						if (xdesptemp<=dimTableroidx0)
						{
							if (ydesptemp>=0)
							{
								if (ydesptemp<=dimTableroidx0)
								{
									//COMPROBAR QUE LA CASILLA NO HA SIDO VISITADA AÚN
									if (Tablero[xdesptemp][ydesptemp]==0)
									{
										//TENGO UNA CASILLA VÁLIDA
										//ACTUALIZO TABLERO Y BAJO EN PROFUNDIDAD
										xActualAnt=xActual;
										yActualAnt=yActual;
										xActual=xdesptemp;
										yActual=ydesptemp;
										lCamino++;
										Tablero[xActual][yActual]=lCamino;
										
										//BAJO EN PROFUNDIDAD
										evalPartida(); 
										//VUELVO DE ABAJO
										//TODO PONER EL TABLERO A SU SITUACIÓN ANTERIOR
										Tablero[xdesptemp][ydesptemp]=0;
										xActual=xActualAnt;
										yActual=yActualAnt;
										lCamino--;
															
									} //CIERRE IF
								} //CIERRE IF
							} //CIERRE IF
						} //CIERRE IF
					} //CIERRE IF
				} //CIERRE IF
				
			} //CIERRE FOR
			
		} //CIERRE ELSE
			
		
		
		return this;
	} //CIERRE FUNCIÓN
	

	
	
	//TODO Limpiar y Comentar
	public void PintarTablero()
	{
		//variables temporales;
		int x,y;
		for (x=dimTableroidx0;x>=0;x--) //lo recorro al revés para representar que el eje y crece de abajo a arriba y no de arriba a abajo 
		{
			for (y=0;y<dimTablero;y++)
			{
				if (Tablero[x][y]>9)
				{
					miConsola.print(Integer.toString(Tablero[x][y]));
				}
				else
				{
					miConsola.print(" " + Integer.toString(Tablero[x][y]));
				}
				
				if (y!=dimTablero)
					{
						miConsola.print("\t");
						
					}
			}
			miConsola.println(); //Salto de Linea	
		}
	}
	

	
}

