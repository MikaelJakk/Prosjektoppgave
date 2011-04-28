/*
 * Skrevet av Mikael Jakhelln den 8 april 2011
 */

package Meterologi.Lister;

import java.io.Serializable;

public class DataListe implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Data f�rste;
	
	public boolean nyData(Data n)
	{	
		if(f�rste == null)//hvis f�rste er null, blir n ny f�rste
		{
			f�rste = n;
			return true;
		}
		if(f�rste.getDato().after(n.getDato()))
		//hvis f�rste.dato er etter n.dato 
		//blir n satt forrest i lista
		{
			settInnForrest(n);
			return true;
		}
		
		Data a = f�rste;
		while(a.neste != null && !n.getDato().before(a.getDato()) )
			//hvis ny.dato er mellom noen andre i lista settes den inn riktig
		{
			if( a.neste.getDato().after(n.getDato()) )
			{
				settInnEtter(a,n);
				return true;
			}
			a = a.neste;
		}
		a.neste = n; return true;
	}
	private void settInnEtter(Data a, Data ny)
	{
		ny.neste = a.neste;
		a.neste = ny;
	}
	
	private void settInnForrest(Data n)
	{
		n.neste = f�rste;
		f�rste = n;
	}
	
	public boolean datoEksisterer(Data n)
	{
		if(f�rste == null)
			return false;
		
		if(f�rste.getDato().compareTo(n.getDato()) == 0)
			return true;
		
		Data a = f�rste;
		
		while(a.neste != null)
		{
			if(a.neste.getDato().compareTo(n.getDato())== 0)
				return true;
			a = a.neste;
		}
		return false;
	}
/*
	public Data getData(Data n)// noe galt her..
	{	
		if(f�rste == null) //hvis f�rste er null s� er lista tom..
			return null;
		
		if(f�rste.getDato().compareTo(n.getDato()) == 0)
			return f�rste;
		
		Data ny = f�rste;
		
		while(ny != null)
		{
			if(ny.getDato().compareTo(n.getDato())== 0)
				return ny;
			ny = ny.neste;
			if(ny.neste == null)
				return ny;
		}
		if(ny.getDato().compareTo(n.getDato())== 0)
			return ny;
		return null;
	}
*/
	public String skrivUtListe()
	{
		String retur = "Dato\tMinTemp\tMaxTemp\tNedb�r\n";
		if(f�rste == null)
			return "ingen data";
		
		if(f�rste != null)
		{
			retur += f�rste.toString() +"\n";
		}
		
		Data a = f�rste;
		while(a.neste != null)
		{
			retur += a.neste.toString()+"\n";
			a = a.neste;
		}
		return retur;
	}
	
	public boolean tomListe()
	{
		if(f�rste == null)
			return true;
		return false;
	}
	
	
	
}
