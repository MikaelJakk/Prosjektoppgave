/*
 * Skrevet av Thomas Nordengen den 12.april 2011
 */
package Meterologi.Lister;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class StedListe implements Serializable
{
	private static final long serialVersionUID = 1L;	
	private TreeSet<Sted> stedliste = new TreeSet<Sted>();

	//Setter inn sted bakerst i listen
	public void settInnFylke(Sted obj)
	{
		stedliste.add(obj);
	}
	
	public boolean finsStedNode(String f, String s)
	{
		Iterator<Sted> iter = stedliste.iterator();
		while(iter.hasNext())
		{	Sted denne = iter.next();
			if(denne.getFylke().compareTo(f) == 0 && denne.getSted().compareTo(s) ==0)
				return true;
		}
		
		return false;
	}
	
	public Sted getStedNode(String f, String s)
	{
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			Sted denne = iterator.next();
			if(denne.getFylke().equals(f) && denne.getSted().equals(s))
				return denne;
		}
		return null;	
	}
	
	//Metoden er kanskje unødvendig da det sikkert går ann å implementere denne i "getStedNode" p åen eller annen måte.
	public boolean slettStedNode(String f, String s)
	{
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			Sted denne = iterator.next();
			if(denne.getFylke().equals(f) && denne.getSted().equals(s))
				iterator.remove();
		}
		return true;
	}
	
	//Gjennoml�per og skriver ut lista alfabetisk
	public String skrivUt()
	{
		//sorter();
		String output = "";
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			output += iterator.next().toString() + "\n";
		}
		return output;
	}
	
	// returnerer en array som inneholder alle fylkene som er registerert i systemet
	public String[] getFylkeArray()

	{
		if(tomListe())
		{
			String[] retur= {"Ingen registrerte Fylker"};
			return retur;
		}
		
		Iterator<Sted> iterator = stedliste.iterator();
		TreeSet<String> utenDuplikater = new TreeSet<String>();
		while(iterator.hasNext())
		{
			Sted gjeldende = iterator.next();
			utenDuplikater.add(gjeldende.getFylke());
		}
		return (String[]) utenDuplikater.toArray(new String[0]);
	}
		
	public String[] getStedArray(String f)
	{
		Iterator<Sted> iterator = stedliste.iterator();
		LinkedList<String> b = new LinkedList<String>();
		while(iterator.hasNext())
		{
			Sted gjeldende = iterator.next();
			if(gjeldende.getFylke().equals(f))
			{
				b.addLast(gjeldende.getSted());
			}
		}
		if (b.size() == 0)
		{
			String[]retur={"Ingen steder opprettet"};
			return retur;
		}
		else
		{
			return (String[]) b.toArray(new String[0]);
		}
	}
	
	public String getMinTempSted(Calendar fra, Calendar til)
	{
		if(stedliste.size() == 0)
			return "Ingen steder registrert";
		
		Sted gjeldende = null;
		Data denmedminstetemp = null;
		String returfylke ="";
		String retursted = "";
		
		Iterator<Sted> iter = stedliste.iterator();
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(denmedminstetemp == null)
			{
			denmedminstetemp = gjeldende.dataliste.getDenMedLavestTemp(fra,til);
			returfylke = gjeldende.getFylke();
			retursted = gjeldende.getSted();
			}
			else
			{
				gjeldende = iter.next();
				if(denmedminstetemp.getMinTemp() > gjeldende.dataliste.getDenMedLavestTemp(fra, til).getMinTemp())
				{
					denmedminstetemp = gjeldende.dataliste.getDenMedLavestTemp(fra, til);
					returfylke = gjeldende.getFylke();
					retursted = gjeldende.getSted();
				}
			}
		}
		return returfylke +"\t" +retursted +"\t" +denmedminstetemp.getMinTemp() +"\t" + denmedminstetemp.getDatoString();
	}
	
	public String getMinTempForMåned(int måned)
	{	/*skal returnere en tekststreng som inneholder
		sted,fylke,mintemp,dato for laveste mintemp i valgt måned*/
		
		if(tomListe())
			return "ingen registrerte steder";
		
		Sted gjeldende;
		
		Data gjeldendedata;
		String gjeldendested;
		String gjeldendefylke;
		
		Data returdata = null;
		String retursted = "";
		String returfylke = "";
		
		Iterator<Sted> iter = stedliste.iterator(); 
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(gjeldende.dataliste.finnesMåned(måned))
			{
				gjeldendedata = gjeldende.dataliste.getLavestTempIMåned(måned);
				gjeldendested = gjeldende.getSted();
				gjeldendefylke = gjeldende.getFylke();

				if(returdata == null)
				{
					returdata = gjeldendedata;
					retursted = gjeldendested;
					returfylke = gjeldendefylke;
				}
				else if(gjeldendedata != null)
				{
					if(returdata.getMinTemp() > gjeldendedata.getMinTemp())
					{
						returdata = gjeldendedata;
						retursted = gjeldendested;
						returfylke = gjeldendefylke;
					}
				}
			} 
		}
		
		if (returdata != null)
			return returfylke +"\t" +retursted +"\t" +returdata.getMinTemp()
			+"\t" + returdata.getDatoString();
		else return "Fant ingen data";
	}
	
	public String getMaxTempForMåned(int måned)
	{	/*skal returnere en tekststreng som inneholder
		sted,fylke,maxtemp,dato for høyeste maxtemp i valgt måned*/
		
		if(tomListe())
			return "ingen registrerte steder";
		
		Sted gjeldende;
		
		Data gjeldendedata;
		String gjeldendested;
		String gjeldendefylke;
		
		Data returdata = null;
		String retursted = "";
		String returfylke = "";
		
		Iterator<Sted> iter = stedliste.iterator(); 
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(gjeldende.dataliste.finnesMåned(måned))
			{
				gjeldendedata = gjeldende.dataliste.getHøyestTempIMåned(måned);
				gjeldendested = gjeldende.getSted();
				gjeldendefylke = gjeldende.getFylke();

				if(returdata == null)
				{
					returdata = gjeldendedata;
					retursted = gjeldendested;
					returfylke = gjeldendefylke;
				}
				else if(gjeldendedata != null)
				{
					if(returdata.getMaxTemp() < gjeldendedata.getMaxTemp())
					{
						returdata = gjeldendedata;
						retursted = gjeldendested;
						returfylke = gjeldendefylke;
					}
				}
			} 
		}
		
		if (returdata != null)
			return returfylke +"\t" +retursted +"\t" +returdata.getMaxTemp()
			+"\t" + returdata.getDatoString();
		else return "Fant ingen data";
	}
	
	public String getMestNedbørForMåned(int måned)
	{	/*skal returnere en tekststreng som inneholder
		sted,fylke,nedbør,dato for mest nedbør i valgt måned*/
		
		if(tomListe())
			return "ingen registrerte steder";
		
		Sted gjeldende;
		
		Data gjeldendedata;
		String gjeldendested;
		String gjeldendefylke;
		
		Data returdata = null;
		String retursted = "";
		String returfylke = "";
		
		Iterator<Sted> iter = stedliste.iterator(); 
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(gjeldende.dataliste.finnesMåned(måned))
			{
				gjeldendedata = gjeldende.dataliste.getMestNedbørIMåned(måned);
				gjeldendested = gjeldende.getSted();
				gjeldendefylke = gjeldende.getFylke();

				if(returdata == null)
				{
					returdata = gjeldendedata;
					retursted = gjeldendested;
					returfylke = gjeldendefylke;
				}
				else if(gjeldendedata != null)
				{
					if(returdata.getNedbør() < gjeldendedata.getNedbør())
					{
						returdata = gjeldendedata;
						retursted = gjeldendested;
						returfylke = gjeldendefylke;
					}
				}
			} 
		}
		
		if (returdata != null)
			return returfylke +"\t" +retursted +"\t" +returdata.getNedbør()
			+"\t" + returdata.getDatoString();
		else return "Fant ingen data";
	}
	
	public String getGjennomsnittsMinTempForAlleSteder(Calendar fra, Calendar til)
	{
		double snittnedbør = 0;
		int antall = 0;
		Sted gjeldende = null;
		
		Iterator<Sted> iter = stedliste.iterator();
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			snittnedbør = gjeldende.dataliste.getGjennomsnittsMinTempVerdi(fra, til);
			antall++;
		}
		NumberFormat format = new DecimalFormat("#0.00");
		return format.format(snittnedbør/antall)+"ºC";
	}
	
	public Object[] getRangertSnittMinTemp(Calendar fra, Calendar til)
	{
		Object [] kolonner = new Object[8];
		
		Iterator<Sted> iter = stedliste.iterator();
		while(iter.hasNext())
		{
			/*
			 * skal gå igjennom stedliste og finne de 8 stednodene med lavest gjennomsnittstemperatur
			 * og legge til fylke,sted,gjennomsnittstemp og dato sortert stigende etter gjennomsnittstemperatur
			 */
		}
		
		
		return kolonner;
	}
	public boolean tomListe()
	{
		Iterator<Sted> iter = stedliste.iterator();
		if(!iter.hasNext())
			return true;
		else
			return false;
	}

	public String toString()
	{
		return "Registrerte Fylker:\tRegistrerte Steder" + "\n" + skrivUt() + "\n";
	}
	
	public boolean fylkeStedEksisterer(Sted n)
	{
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{	Sted gjeldende = iterator.next();
			if(gjeldende.getFylke().compareTo(n.getFylke()) == 0 
					&& gjeldende.getSted().compareTo(n.getSted()) == 0)
				return true;
		}
		return false;
	}
}
