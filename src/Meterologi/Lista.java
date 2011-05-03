package Meterologi;

import java.io.*;

import Meterologi.Lister.*;

public abstract class Lista {
	public final static String filnavn="listedata.dat";
	public static StedListe stedliste = new StedListe();

	public static void lesLista()
	{
		ObjectInputStream input = null;
		
		try{
			input = new ObjectInputStream(
								new BufferedInputStream(
									new FileInputStream(filnavn)));
			stedliste = (StedListe) input.readObject();
			System.out.println("Suksess: innlesning av liste");
			
		}		
		catch(ClassNotFoundException cnex)
			{System.out.println("Feil: Finner ikke klassen "+cnex);}
		catch(FileNotFoundException fnex)
			{System.out.println("Feil: finner ikke fila " +fnex);}
		catch(IOException ioex)
			{System.out.println("Feil: ved innlesning "+ioex);}
		
		try {
			input.close();
		} 
		catch (Exception ex) 
		{System.out.println("Feil: ved lesing av fil. kan ikke lukke filstrømmen");}
	}
	
	public static void lagreLista()
	{
		ObjectOutputStream output = null;
		
		try {
			output = new ObjectOutputStream(new FileOutputStream(filnavn));
			output.writeObject( stedliste );
			System.out.println("Suksess: lagring av liste");
			
		} 
		catch(NotSerializableException nzex)
			{System.out.println("Feil: fila kan ikke serializeres" +nzex);}
		catch(IOException ioex)
			{System.out.println("Feil: ved lagring av fil"+ioex);}
		
		try{
			output.close();
		}
		catch(Exception e){System.out.println("Feil under skriving til fil "+ e);}
	}
}
