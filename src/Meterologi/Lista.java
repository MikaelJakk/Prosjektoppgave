package Meterologi;

import java.io.*;

import Meterologi.Lister.*;

public abstract class Lista {
	public final static String filnavn="listedata.dat";
	transient public static StedListe stedliste = new StedListe();

	public static void lesLista()
	{
		ObjectInputStream input = null;
		
		try{
			input = new ObjectInputStream(
								new BufferedInputStream(
									new FileInputStream(filnavn)));
			stedliste = (StedListe) input.readObject();
			System.out.println("vellykket innlesning av liste");
			input.close();
		}		
		catch(ClassNotFoundException cnex)
			{System.out.println("Finner ikke klassen "+cnex);}
		catch(FileNotFoundException fnex)
			{System.out.println("finner ikke fila " +fnex);}
		catch(IOException ioex)
			{System.out.println("In/Out feil ved innlesning "+ioex);}
		
		try {
			
		} 
		catch (Exception ex) 
		{System.out.println("feil med lesing av fil. kan ikke lukke filstrømmen");}
	}
	
	public static void lagreLista()
	{
		ObjectOutputStream output;
		
		try {
			output = new ObjectOutputStream(new FileOutputStream(filnavn));
			output.writeObject( stedliste );
			System.out.println("vellykket lagring av lista");
			output.close();
		} 
		catch(NotSerializableException nzex)
			{System.out.println("fila har feil versjon " +nzex);}
		catch(IOException ioex)
			{System.out.println("generell feil ved lagring av fil "+ioex);}
		
		try{
			
		}
		catch(Exception e){System.out.println("Feil under skriving til fil "+ e);}
	}
}
