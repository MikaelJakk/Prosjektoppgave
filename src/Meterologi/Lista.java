package Meterologi;

import java.io.*;

import Meterologi.Lister.*;

public abstract class Lista {
	private final String fila="listedata.dat";
	static DataListe dataliste = new DataListe();
	ObjectInputStream input;
	public Lista()
	{
		/*try{
			input = new ObjectInputStream(
								new BufferedInputStream(
									new FileInputStream(fila)));
		}catch(Exception e){System.out.println("kaos med lesing av fil");}
		*/
	}

}
