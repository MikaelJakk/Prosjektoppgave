package Meterologi;

import java.io.*;

import Meterologi.Lister.*;

public abstract class Lista {
	public final static String fila="listedata.dat";
	public static DataListe dataliste = new DataListe();
	
/*
	public static void lesLista()
	{
		try{
			ObjectInputStream input = new ObjectInputStream(
								new BufferedInputStream(
									new FileInputStream(fila)));
			dataliste = (DataListe) input.readObject();
			input.close();
		}catch(Exception e){System.out.println("kaos med lesing av fil");}
	}
	
	public static void lagreLista()
	{
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fila));
			output.writeObject( dataliste );
			output.close();
		} 
		catch (IOException e) {System.out.println("Problem ved skriving til fil");}
		
	}
*/

}
