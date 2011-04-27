package Meterologi;

import java.io.*;

import javax.swing.JOptionPane;

import Meterologi.Lister.*;

public abstract class Lista {
	public final static String fila="listedata.dat";
	public static DataListe dataliste = new DataListe();
	public static StedListe stedliste = new StedListe();

	public static void lesLista()
	{
		try{
			ObjectInputStream input = new ObjectInputStream(
								new BufferedInputStream(
									new FileInputStream(fila)));
			dataliste = (DataListe) input.readObject();
			input.close();
			System.out.println("leste lista");
		}		
		catch(ClassNotFoundException cnex)
			{System.out.println("Finner ikke klassen");return;}
		catch(FileNotFoundException fnex)
			{System.out.println("finner ikke fila");return;}
		catch(IOException ioex)
			{System.out.println("Generell feil med innlesning");return;}
	}
	
	public static void lagreLista()
	{
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fila));
			output.writeObject( dataliste );
			output.close();
			System.out.println("lagret lista");
		} 
		catch(NotSerializableException nzex)
			{System.out.println("fila har feil versjon");}
		catch(IOException ioex)
			{System.out.println("generell feil ved lagring av fil");}
	}
}
