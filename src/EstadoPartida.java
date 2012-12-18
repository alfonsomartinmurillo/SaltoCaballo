
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
	public boolean esSolucion;
	public  PrintStream miConsola;
	public int numPosValidas;
	
	
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
		Camino=new Casilla[dim*dim]; //Genero un nuevo array de tipo Casilla
		//Crear todas las casillas del array Camino.
		for (idx=0;idx<(dim*dim);idx++)
		{
			Camino[idx]=new Casilla(); //Genero la lista de casillas inicial
		}
		
		// Inicializo la primera posición del camino actual
		Camino[0].xPos=xIni-1; //LO ESTABLEZCO SOBRE INDICE 0, NO ÍNDICE 1
		Camino[0].yPos=yIni-1;
		//Inicializo la longitud inicial del camino
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
	}
	
	//TODO Limpiar y Comentar
	public EstadoPartida evalPartida()
	{
		
		//VARIABLES LOCALES
		int x;
		
		//Salida en caso de camino válido. Si el camino obtenido corresponde con el tamaño del tablero, es que hemos
		//ocupado todas las posiciones.
		//SALIDA VÁLIDA
		if (lCamino==dimTablero*dimTablero)
			{
				esSolucion=true;
				//AÑADO LA SOLUCIÓN A LA LISTA DE SOLUCIONES
				SaltoCaballo.listaSoluciones.add(this);
				Date fechaHora=new Date(); 
				miConsola.println(fechaHora.toString() + " Solución - " + Integer.toString(SaltoCaballo.listaSoluciones.size()));
				miConsola.println("----------------------------------------------------------");
					
				if (modoTraza)
				{
					miConsola.println(fechaHora.toString() +  " Detalle de la traza");
					miConsola.println("----------------------------------------------------------");
					SaltoCaballo.PintarSolucion(this);
				}
				else
				{
					PintarTablero();
					miConsola.println();
				}
					miConsola.println();

				
				
			}
		else
			//OBTENCIÓN DE SIGUIENTES POSICIONES
			//
			
		{
			
				//Array para la obtención de las siguientes posiciones disponibles
				ArrayList<EstadoPartida> ListaEstadosSiguientes;
				ListaEstadosSiguientes=SaltoCaballo.genCompleciones(this);
				x=0;
				while (x<ListaEstadosSiguientes.size()) //realizamos este recorrido mientras tenga ensayos que cumplan
				{
					ListaEstadosSiguientes.get(x).evalPartida();
					
					if (SaltoCaballo.listaSoluciones.size()==1  && modotodasSoluciones==false) break;// SI HE ENCONTRADO UNA SOLUCIÓN ROMPO EL BUCLE PARA SALIR LO ANTES POSIBLE
					x++;
					
				}
				//YA HE ACABADO CON MI LISTA DE ESTADOS, ASÍ QUE ME LOS CEPILLO.
				ListaEstadosSiguientes=null;
		
			
			
		}
			return this;
	
	}
	
	//TODO Limpiar y Comentar
	public Casilla[] copiaCamino()
	{
		int x;
		Casilla tCamino[];
		tCamino=new Casilla[dimTablero*dimTablero];
		for (x=0;x<(dimTablero*dimTablero);x++)
		{
			tCamino[x]=new Casilla(Camino[x].xPos, Camino[x].yPos);
	
		}
			return tCamino;
	}
	
	//MÉTODO QUE REPLICA EL TABLERO
	public int[][] copiaTablero()
	{
		//variables temporales;
				int x,y;
				int tTablero[][];
				tTablero=new int[dimTablero][dimTablero];
				for (x=0;x<dimTablero;x++) //lo recorro al revés para representar que el eje y crece de abajo a arriba y no de arriba a abajo 
				{
					for (y=0;y<dimTablero;y++)
					{
						tTablero[x][y]=Tablero[x][y];
						
					}
					
				}
				return tTablero;
	}
	
	
	
	//TODO Limpiar y Comentar
	public void PintarTablero()
	{
		//variables temporales;
		int x,y;
		for (x=dimTablero-1;x>=0;x--) //lo recorro al revés para representar que el eje y crece de abajo a arriba y no de arriba a abajo 
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
	
	//TODO Limpiar y Comentar
	public void PintarCamino()
	{
		int x;
		for (x=0;x<(dimTablero*dimTablero);x++)
		{
			miConsola.println("Posición " + Integer.toString(x+1) + ": x=" + Integer.toString(Camino[x].xPos) + " , y=" + Integer.toString(Camino[x].yPos));

		}
	}
	

	
	
}

