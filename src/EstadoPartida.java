import java.io.PrintStream;
import java.io.UnsupportedEncodingException;



public class EstadoPartida

{

	public int Tablero[][];
	public Casilla Camino[];
	public int lCamino;
	public int xActual;
	public int yActual;
	public int dimTablero;
	
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
	
	public EstadoPartida(int xIni,int yIni, int dim)	
	{
		//VARIABLES TEMPORALES
		int idx;
		int idy;
		
		//CUERPO
				
		xActual=xIni-1; //Posici�n x de partida. Resto -1 para adaptarlo a matriz con �ndice que empieza en 0.  
		yActual=yIni-1; //Posici�n y de partida. Resto -1 para adaptarlo a matriz con �ndice que empieza en 0.
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
		Camino=new Casilla[dim]; //Genero un nuevo array de tipo Casilla
		//Crear todas las casillas del array Camino.
		for (idx=0;idx<dim;idx++)
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
		miConsola=new PrintStream(System.out,true,"UTF-8");
		
	}
	
	//TODO Implementar la funci�n que pinta el tablero por consola
	
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
	
	
}

