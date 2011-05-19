/*
 * Skrevet av Mikael Jakhelln 10.Mai 2011
 */

package Meterologi;

import Meterologi.Lister.*;

public abstract class Lista {
	public final static String filnavn="listedata.dat";
	public final static String datamappe = "Listedata";
	public static StedListe stedliste = new StedListe();

	public static void lesLista()
	{
		try{
			stedliste.lesLista(datamappe);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void lagreLista()
	{
		try{
		stedliste.lagreLista();
		}catch(Exception ex){ex.printStackTrace();}
	}
}
