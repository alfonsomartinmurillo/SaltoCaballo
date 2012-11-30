import java.io.PrintStream;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;



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
	
	private  PrintStream miConsola;
	
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
		
	}
	
	public EstadoPartida(int xIni,int yIni, int dim, Boolean mTraza, Boolean tSoluciones)	
	{
		//VARIABLES TEMPORALES
		int idx;
		int idy;
		
		//CUERPO
				
		xActual=xIni-1; //Posici�n x de partida. Resto -1 para adaptarlo a matriz con �ndice que empieza en 0.  
		yActual=yIni-1; //Posici�n y de partida. Resto -1 para adaptarlo a matriz con �ndice que empieza en 0.
		modoTraza=mTraza;
		modotodasSoluciones=tSoluciones;
		Tablero=new int[dim][dim]; //Creaci�n de la matriz que representa el tablero;
		//Inicializar el Tablero todo a 0.
		for (idx=0;idx<dim;idx++)
		{
			for (idy=0;idy<dim;idy++)
			{
				Tablero[idx][idy]=0;
			}
		}
		//Establezco la primera posici�n del caballo en el tablero
		Tablero[xActual][yActual]=1;
		dimTablero=dim; //Establezco la dimensi�n del Tablero
		Camino=new Casilla[dim*dim]; //Genero un nuevo array de tipo Casilla
		//Crear todas las casillas del array Camino.
		for (idx=0;idx<(dim*dim);idx++)
		{
			Camino[idx]=new Casilla(); //Genero la lista de casillas inicial
		}
		
		// Inicializo la primera posici�n del camino actual
		Camino[0].xPos=xIni;
		Camino[0].yPos=yIni;
		//Inicializo la longitud inicial del camino
		lCamino=1;
		//llamada al constructor por defecto
		try
		{
			initEstadoPartida();
		}
		catch (UnsupportedEncodingException exc)
		{
			System.out.println(exc.toString());
		}
		
		
	}
	
	public void initEstadoPartida()	throws UnsupportedEncodingException
	{
		//Funci�n que focaliza todo lo com�n a los diferentes constructores
		esSolucion=false;
		miConsola=new PrintStream(System.out,true,"UTF-8");
	}
	
	public EstadoPartida evalPartida(ArrayList<EstadoPartida> ListaSoluciones)
	{
		//TODO implementar que la funci�n retorne cuando el par. de entrada sea el de una �nica soluci�n.
		//variables locales
		int x;
		//Salida en caso de camino v�lido. Si el camino obtenido corresponde con el tama�o del tablero, es que hemos
		//ocupado todas las posiciones.
		if (lCamino==dimTablero*dimTablero)
			{
				esSolucion=true;
				ListaSoluciones.add(this);
				return this;
			}
		else
			//Obtenci�n de siguientes ensayos
		{
			if ((ListaSoluciones.size()==1 && modotodasSoluciones==false)!=true) //He encontrado una soluci�n y solo quiero una. Ya no necesito continuar
			{
			
				//Array para la obtenci�n de las siguientes posiciones disponibles
				ArrayList<EstadoPartida> ListaEstadosSiguientes;
				ListaEstadosSiguientes=SaltoCaballo.genCompleciones(this);
				x=0;
				while (x<ListaEstadosSiguientes.size()) //realizamos este recorrido mientras tenga ensayos que cumplan
				{
					ListaEstadosSiguientes.get(x).evalPartida(ListaSoluciones);
					
					if (ListaSoluciones.size()==1  && modotodasSoluciones==false) break;// SI HE ENCONTRADO UNA SOLUCI�N ROMPO EL BUCLE PARA SALIR LO ANTES POSIBLE
						
					x++;
					
				}
			}
			
			
		}
			return this;
	
	}
	
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
	
	public int[][] copiaTablero()
	{
		//variables temporales;
				int x,y;
				int tTablero[][];
				tTablero=new int[dimTablero][dimTablero];
				for (x=0;x<dimTablero;x++) //lo recorro al rev�s para representar que el eje y crece de abajo a arriba y no de arriba a abajo 
				{
					for (y=0;y<dimTablero;y++)
					{
						tTablero[x][y]=Tablero[x][y];
						
					}
					
				}
				return tTablero;
	}
	
	
	
	public void PintarTablero()
	{
		//variables temporales;
		int x,y;
		for (x=dimTablero-1;x>=0;x--) //lo recorro al rev�s para representar que el eje y crece de abajo a arriba y no de arriba a abajo 
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
	
	public void PintarCamino()
	{
		int x;
		for (x=0;x<(dimTablero*dimTablero);x++)
		{
			miConsola.println("Posici�n " + Integer.toString(x+1) + ": x=" + Integer.toString(Camino[x].xPos) + " , y=" + Integer.toString(Camino[x].yPos));

		}
	}
	

	
	
}

