/*
 * Skrevet av Thomas Nordengen og Mikael Jakhelln i April-Mai 2011
 */
package Meterologi.Lister;

import java.io.*;
import java.util.*;


public class StedListe
{	
	private final String datamappe = "Listedata";
	
	private TreeSet<Sted> stedliste = new TreeSet<Sted>();

	//Metoder for grunnleggende innsettning og sletting av data
	public void settInnFylke(Sted obj)
	{//Setter inn sted bakerst i listen
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
	{//returnerer noden som representerer strengene f=fylke og s=sted
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			Sted denne = iterator.next();
			if(denne.getFylke().equals(f) && denne.getSted().equals(s))
				return denne;
		}
		return null;	
	}
	
	public boolean slettStedNode(String f, String s)
	{/*sletter stedet representert av f=fylke og s=sted fra lista*/
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			Sted denne = iterator.next();
			if(denne.getFylke().equals(f) && denne.getSted().equals(s))
				iterator.remove();
		}
		return true;
	}
	
	//Gjennomløper og skriver ut lista alfabetisk
	public String skrivUt()
	{
		String output = "";
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			output += iterator.next().toString() + "\n";
		}
		return output;
	}
	//end of grunnleggende metoder
	
	//metoder for visning av fylker og steder i programmet
	public String[] getFylkeArray()
	{// returnerer en array som inneholder alle fylkene som er registerert i systemet
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
	{//returnerer en strengarray som inneholder alle stedene registrert på valgt fylke
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
	//end of metoder for visning av fylke og sted
	
	//metoder for statistisk visning av data
	public String getMinTempSted(Calendar fra, Calendar til)
	{/*skal skrive ut stedet(fylke,sted, verdi og dato) som har lavest mintemp i hele registeret mellom datoene*/
		if(stedliste.size() == 0)
			return "Ingen steder registrert";
		
		Sted gjeldende = null;
		Data gjeldendetemp = null;
		String returfylke ="";
		String retursted = "";
		
		Iterator<Sted> iter = stedliste.iterator();
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(gjeldendetemp == null)
			{
				gjeldendetemp = gjeldende.dataliste.getDenMedLavestTemp(fra,til);
				returfylke = gjeldende.getFylke();
				retursted = gjeldende.getSted();
			}
			else
			{
				gjeldende = iter.next();
				if(gjeldendetemp.getMinTemp() > gjeldende.dataliste.getDenMedLavestTemp(fra, til).getMinTemp())
				{
					gjeldendetemp = gjeldende.dataliste.getDenMedLavestTemp(fra, til);
					returfylke = gjeldende.getFylke();
					retursted = gjeldende.getSted();
				}
			}
		}
		return returfylke +"\t" +retursted +"\t" +gjeldendetemp.getMinTemp() +"\t" 
			+ gjeldendetemp.getDatoString();
	}
	
	public String getMaxTempSted(Calendar fra, Calendar til)
	{/*skal skrive ut stedet(fylke,sted, verdi og dato) som har lavest mintemp i hele registeret mellom datoene*/
		if(stedliste.size() == 0)
			return "Ingen steder registrert";
		
		Sted gjeldende = null;
		Data gjeldendetemp = null;
		String returfylke ="";
		String retursted = "";
		
		Iterator<Sted> iter = stedliste.iterator();
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(gjeldendetemp == null)
			{
				gjeldendetemp = gjeldende.dataliste.getDenMedHøyesteTemp(fra,til);
				returfylke = gjeldende.getFylke();
				retursted = gjeldende.getSted();
			}
			else
			{
				gjeldende = iter.next();
				if(gjeldendetemp.getMaxTemp() < gjeldende.dataliste.getDenMedHøyesteTemp(fra, til).getMaxTemp())
				{
					gjeldendetemp = gjeldende.dataliste.getDenMedHøyesteTemp(fra, til);
					returfylke = gjeldende.getFylke();
					retursted = gjeldende.getSted();
				}
			}
		}
		return returfylke +"\t" +retursted +"\t" +gjeldendetemp.getMaxTemp() +"\t" 
			+ gjeldendetemp.getDatoString();
	}
	
	//metode som returnerer gjennomsnittsTemp for valgt Sted fro Èn måned
	
	public String getMinTempForMåned(int måned)
	{	/*skal returnere en tekststreng som inneholder
		sted,fylke,lavest temp og dato i valgt måned*/
		
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
			return returfylke +"\t" +retursted +"\t\t" +returdata.getMinTemp()
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
			return returfylke +"\t" +retursted +"\t\t" +returdata.getMaxTemp()
			+"\t" + returdata.getDatoString();
		else return "Fant ingen data";
	}
	
	public String getMestNedbørForMåned(int måned)
	{	/*skal returnere en tekststreng som inneholder
		sted,fylke,nedbør,dato for enkeltdatoen med mest nedbør i valgt måned*/
		
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
			return returfylke +"\t" +retursted +"\t\t" +returdata.getNedbør()
			+"\t" + returdata.getDatoString();
		else return "Fant ingen data";
	}
	
	public boolean finnesIÅr(int år)
	{
		Iterator<Sted> iter = stedliste.iterator();
		Sted gjeldende = null;
		while(iter.hasNext())
		{	gjeldende = iter.next();
			if(gjeldende.dataliste.finnesIÅr(år))
				return true;
		}
		return false;
	}
	
	public String getMinTempIÅr(int år)
	{
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
			if(gjeldende.dataliste.finnesIÅr(år))
			{
				gjeldendedata = gjeldende.dataliste.getLavestTempIÅr(år);
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
	
	public String getMaxTempIÅr(int år)
	{
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
			if(gjeldende.dataliste.finnesIÅr(år))
			{
				gjeldendedata = gjeldende.dataliste.getHøyesteTempIÅr(år);
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
	
	public String getMestNedbørIÅr(int år)
	{
		if(tomListe())
			return "ingen registrerte steder";
		
		Sted gjeldende;
		
		int gjeldendenedbør = 0;
		String gjeldendested;
		String gjeldendefylke;

		int returnedbør = 0;
		String retursted = "";
		String returfylke = "";
		
		Iterator<Sted> iter = stedliste.iterator(); 
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(gjeldende.dataliste.finnesIÅr(år))
			{
				gjeldendenedbør = gjeldende.dataliste.getTotalNedbørIÅr(år);
				gjeldendested = gjeldende.getSted();
				gjeldendefylke = gjeldende.getFylke();

				if(returnedbør == 0)
				{
					returnedbør = gjeldendenedbør;
					retursted = gjeldendested;
					returfylke = gjeldendefylke;
				}
				else if(gjeldendenedbør != 0)
				{
					if(returnedbør < gjeldendenedbør)
					{
						returnedbør = gjeldendenedbør;
						retursted = gjeldendested;
						returfylke = gjeldendefylke;
					}
				}
			} 
		}
	
		if (returnedbør != 0)
			return returfylke +"\t" +retursted +"\t" +returnedbør+" mm";
		else return "Fant ingen data";
	}
	
	public String getMinstNedbørIÅr(int år)
	{/*antar at stedet med minst nedbør i et år er stedet med minst totalnedbør det året, 
	og at det har regnet minst 1 mm det året*/
		if(tomListe())
			return "ingen registrerte steder";
		
		Sted gjeldende;
		
		int gjeldendenedbør = 0;
		String gjeldendested;
		String gjeldendefylke;

		int returnedbør = 0;
		String retursted = "";
		String returfylke = "";
		
		Iterator<Sted> iter = stedliste.iterator(); 
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			if(gjeldende.dataliste.finnesIÅr(år))
			{
				gjeldendenedbør = gjeldende.dataliste.getTotalNedbørIÅr(år);
				gjeldendested = gjeldende.getSted();
				gjeldendefylke = gjeldende.getFylke();

				if(returnedbør == 0)
				{
					returnedbør = gjeldendenedbør;
					retursted = gjeldendested;
					returfylke = gjeldendefylke;
				}
				else if(gjeldendenedbør > 0)
				{
					if(returnedbør > gjeldendenedbør)
					{
						returnedbør = gjeldendenedbør;
						retursted = gjeldendested;
						returfylke = gjeldendefylke;
					}
				}
			} 
		}
	
		if (returnedbør != 0)
			return returfylke +"\t" +retursted +"\t" +returnedbør+" mm";
		else return "Fant ingen data";
	}
	
	public double getGjennomsnittMinTempIÅr(int år)
	{
		double snittemp = 0.0;
		int antall = 0;
		
		if(tomListe())
			return 0;

		Iterator<Sted> iter = stedliste.iterator();
		Sted gjeldende = null;
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			snittemp = snittemp + gjeldende.dataliste.getGjennomsnittsMinTempIÅr(år);
			antall++;
		}
		if(snittemp != 0 && antall != 0)
			return snittemp/antall;
		
		return 0;
	}
	
	public double getGjennomsnittMaxTempIÅr(int år)
	{
		double snittemp = 0.0;
		int antall = 0;
		
		if(tomListe())
			return 0;

		Iterator<Sted> iter = stedliste.iterator();
		Sted gjeldende = null;
		while(iter.hasNext())
		{
			gjeldende = iter.next();
			snittemp = snittemp + gjeldende.dataliste.getGjennomsnittsMaksTempIÅr(år);
			antall++;
		}
		if(snittemp != 0 && antall != 0)
			return snittemp/antall;
		
		return 0;
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
	
	//metoder for lagring og lesing av data (skrevet av Mikael Jakhelln)
	public void lagreLista()
	{/*skal skrive ut all dataen i registeret sortert i et mappehierarki.*/
		Iterator<Sted> iter = stedliste.iterator();
		Sted gjeldende;
		new File(datamappe).mkdirs();//passer på at mappen Listedata/ eksisterer
		
		while(iter.hasNext())
		{//skriv ut til datamappe/gjeldende.getFylke().gjeldende.getSted.data.dat
			gjeldende = iter.next();
			
			if(new File(datamappe).exists())//hvis mappen finnes
			{
				new File(datamappe+"/"+gjeldende.getFylke()+"."+gjeldende.getSted()).mkdirs();
				gjeldende.dataliste.skrivTilFil(datamappe+"/"+gjeldende.getFylke()+"."+gjeldende.getSted()+"/");
			}
			else
				System.out.println("Feil: mappen Listedata eksisterer ikke");
		}
	}
	
	public void lesLista(String filsti)
	{/*skal lage nye steder utifra mappene i datamappe, 
	og lese inne data i disse nye stedene utifra mappenavnet i datamappen*/
		File datamappepeker = new File(filsti);
		if(datamappepeker.exists())
		{/*lag ett nytt sted for hver mappe og laster inn data for hver mappe i sitt respektive sted..*/
			File[] mapper = datamappepeker.listFiles();
			for(int i=0; i<mapper.length; i++)
			{
				String mappenavn = mapper[i].getName();
				int dott = mappenavn.indexOf('.');
				String fylke = mappenavn.substring(0,dott);
				String sted = mappenavn.substring(dott+1);
				
				if(!finsStedNode(fylke, sted))
				{
					settInnFylke(new Sted(sted,fylke));
					Sted gjeldende = getStedNode(fylke, sted);
					
					//gjeldende.dataliste.lesFraFil(mappenavn);
					gjeldende.dataliste.lesFraFil(mapper[i]);
				}
			}
		}
		else
		{
			System.out.println("Feil: Mappen 'Listedata' eksisterer ikke");
		}
		
	}
	
	public boolean slettFil(String filsti)
	{
		File dir = new File(filsti);
		if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = slettFil(dir+"/"+children[i]);
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    // The directory is now empty so delete it
	    return dir.delete();
	}
}
