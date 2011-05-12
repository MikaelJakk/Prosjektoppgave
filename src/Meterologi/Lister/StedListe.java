/*
 * Skrevet av Thomas Nordengen den 12.april 2011
 */
package Meterologi.Lister;

import java.io.*;
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
	
	public String getStedMedMinsteTemp(Calendar fra, Calendar til)
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
			denmedminstetemp = gjeldende.dataliste.getDenMedLavesteTemp(fra,til);
			returfylke = gjeldende.getFylke();
			retursted = gjeldende.getSted();
			}
			else
			{
				gjeldende = iter.next();
				if(denmedminstetemp.getMinTemp() > gjeldende.dataliste.getDenMedLavesteTemp(fra, til).getMinTemp())
				{
					denmedminstetemp = gjeldende.dataliste.getDenMedLavesteTemp(fra, til);
					returfylke = gjeldende.getFylke();
					retursted = gjeldende.getSted();
				}
			}
		}
		return returfylke +"\t" +retursted +"\t" +denmedminstetemp.getMinTemp() +"\t" + denmedminstetemp.getDatoString();
	}
	public Data getDataforStedMedMinsteTemp(Calendar fra, Calendar til)
	{
		if(stedliste.size() == 0)
			return null;
		
		Sted gjeldende = null;
		Data denmedminstetemp = null;
		
		Iterator<Sted> iter = stedliste.iterator();
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(denmedminstetemp == null)
			{
			denmedminstetemp = gjeldende.dataliste.getDenMedLavesteTemp(fra,til);
			}
			else
			{
				gjeldende = iter.next();
				if(denmedminstetemp.getMinTemp() > gjeldende.dataliste.getDenMedLavesteTemp(fra, til).getMinTemp())
				{
					denmedminstetemp = gjeldende.dataliste.getDenMedLavesteTemp(fra, til);
				}
			}
		}
		return denmedminstetemp;
	}
	
	//----------------Uferdig-------------------
	/*public String getMaksTempSted(Calendar fra, Calendar til)
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
		denmedminstetemp = gjeldende.dataliste.getDenMedMinsteTemp(fra,til);
		returfylke = gjeldende.getFylke();
		retursted = gjeldende.getSted();
		}
		else
		{
			gjeldende = iter.next();
			if(denmedminstetemp.getMinTemp() > gjeldende.dataliste.getDenMedMinsteTemp(fra, til).getMinTemp())
			{
				denmedminstetemp = gjeldende.dataliste.getDenMedMinsteTemp(fra, til);
				returfylke = gjeldende.getFylke();
				retursted = gjeldende.getSted();
			}
		}
	}
	return returfylke +"\t" +retursted +"\t" +denmedminstetemp.getMinTemp() +"\t" + denmedminstetemp.getDatoString();	
	//<går igjennom stedliste og kaller getDenMedHøyesteTemp(fra,til) for alle og returnerer sted, maxtemp og dato for den med høyest temperatur>
}

	/*
	public String getStedetMedMinstNedbør(Calendar fra, Calendar til)
	{<går igjennom stedliste og kaller getDenMedMinstNedbør for alle stedene og returnerer en streng med fylke, sted, nedbørsverdi og dato>}

	public String getStedetMedMestNedbør(Calendar fra, Calendar til)
	{<går igjennom stedliste og kaller getDenMedMestNedbør(fra,til) og returnerer en streng med fylke, sted, nedbørsverdi og dato>}
	*/
	
	/*
	//-------------------------------Statistikk Metoder-------------------------------------
	public String getStedMedMinsteTemp(Calendar fra, Calendar til)
	{
		String retur ="";
		Sted gjeldenested = null;
		Data dataMinstTempGammel;
		Calendar nullsec = Calendar.getInstance();
		nullsec.setTimeInMillis(0);
		dataMinstTempGammel = new Data(nullsec,0,0,0);
		//dataMinstTempGammel = sted.dataliste.getFørste();
		Data midlertidig = null;
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			if(dataMinstTempGammel != null && midlertidig == null)
			{
				midlertidig = dataMinstTempGammel;
			}
			else
			{
				if(gjeldenested == null)
					gjeldenested = iterator.next();
				Data dataMinsteTemp;
				dataMinsteTemp = sted.dataliste.getDenMedMinsteTemp(fra, til);	
				
				if(dataMinsteTemp.getMinTemp() > midlertidig.getMinTemp())
				{
					midlertidig = dataMinsteTemp;
				}	
			}
		}
		retur = gjeldenested.getFylke() +"\t" +gjeldenested.getSted() +"\t" +midlertidig.getMinTemp() + ", " + dataMinstTempGammel.getDato();
		return retur;

		//<går igjennom stedliste og kaller getDenMedMinsteTemp(fra,til) 
		//for alle og returnerer sted, mintemp og dato for den med minst temperatur>
	}
	//--------------------------------Statistikk Metoder-------------------------------------------
	*/
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
		return "Registrerte Fylker:" + "\n" + skrivUt() + "\n";
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
