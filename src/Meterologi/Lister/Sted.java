/*
 * Skrevet av Mikael Jakhelln 8.april 2011
 */

package Meterologi.Lister;

import java.io.Serializable;

public class Sted implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DataListe dataliste;
	private String sted;
	private String fylke;
	
	public Sted(String s, String f)
	{
		fylke = f;
		sted = s;
	}
	
	public String getSted()
	{
		return sted;
	}
	public String getFylke()
	{
		return fylke;
	}
	
	public boolean nyData(Data n)
	{	/*sjekker igjennom lista, om det er registrert data på samma dato som 
		parameterens dato, skal den returnere false. 
		Ved vellykket innsetting skal den returnere true
		*/
		if( !finnesILista(n) )
			{
				dataliste.nyData(n);	
				return true;
			}
		else return false;
	}
	
	public boolean finnesILista(Data n)
	//sjekker igjennom datalista om dato er registrert ifra før
	{
		if(dataliste.datoEksisterer(n))
			return true;
		return false;
	}
	
	public String toString()
	{
		return fylke;
	}
}
