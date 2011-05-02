package Meterologi;

import java.io.*;

import Meterologi.Lister.*;

public abstract class Lista {
	public final static String fila="listedata.dat";
	public static StedListe stedliste = new StedListe();

	public static void lesLista()
	{
		ObjectInputStream input = null;
		try{
			input = new ObjectInputStream(
								new BufferedInputStream(
									new FileInputStream(fila)));
			stedliste = (StedListe) input.readObject();
			System.out.println("leste lista");
		}		
		catch(ClassNotFoundException cnex)
			{System.out.println("Finner ikke klassen");}
		catch(FileNotFoundException fnex)
			{System.out.println("finner ikke fila");}
		catch(IOException ioex)
			{System.out.println("In/Out feil ved innlesning");}
		try {
			input.close();
		} 
		catch (IOException e) 
		{System.out.println("feil med lesing av fil. kan ikke lukke filstrømmen");}
	}
	
	public static void lagreLista()
	{
		try {
			ObjectOutputStream output;
			output = new ObjectOutputStream(new FileOutputStream(fila));
			output.writeObject( stedliste );
			System.out.println("lagret lista");
			output.close();
		} 
		catch(NotSerializableException nzex)
			{System.out.println("fila har feil versjon");}
		catch(IOException ioex)
			{System.out.println("generell feil ved lagring av fil");}
	}
}
