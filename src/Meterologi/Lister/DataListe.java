/*
 * Skrevet av Mikael Jakhelln den 8 april 2011
 */

package Meterologi.Lister;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

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
		/*un�dvendig kode.. etter while l�kka er ny alltid null..
		 * if(ny.getDato().compareTo(n.getDato())== 0)
			return ny;*/
		return null;
	}
	
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
	
	public boolean slettData(Data d)
	{
		if(f�rste == null)
			return false;
		if(f�rste.getDato().equals(d.getDato()))
		{
			f�rste = f�rste.neste;
			return true;
		}
		else
		{
			Data a = f�rste;
			 while ( a.neste!= null)
		      {  if(a.neste.getDato().equals( d.getDato() ))
		         {
		           a.neste = a.neste.neste;
		           return true;
		         }
		      	a = a.neste;
		      }
			return false;
		}
	}
	
	public boolean tomListe()
	{
		if(f�rste == null)
			return true;
		return false;
	}
	
	public Data getDenMedLavestTemp(Calendar fra, Calendar til)
	{/*<returner den noden(Data) med minst temp i lista som ogs� har dato.after(fra) && dato.before(til)>*/
		Data a = f�rste;
		Data retur = a;
		while(a != null)
		{
			
			if(a.getMinTemp()<retur.getMinTemp() &&
				(a.getDato().compareTo(fra) == 0 
				|| a.getDato().after(fra)&& a.getDato().before(til) 
				|| a.getDato().compareTo(til) == 0))
			{
				retur = a;
			}
			a=a.neste;
		}
		return retur;
	}
	public boolean finnesLavestTempM�ned(int m�ned)
	{
		Data a = f�rste;
		while(a != null)
		{
			
			if(a.getDato().get(Calendar.MONTH) == m�ned)
			{
				return true;
			}
			a=a.neste;
		}
		return false;	
	}
	
	public Data getLavestTempM�ned(int m�ned)
	{/*returnerer den noden i valgt m�ned som har lavest temperatur
	 	fins det ingen noder p� valgt m�ned returnerer den null*/
		Data a = f�rste;
		Data retur = a;
		while(a != null)
		{
			if(a.getMinTemp()<retur.getMinTemp() && a.getDato().get(Calendar.MONTH) == m�ned)
			{
				retur = a;
			}
			a=a.neste;
		}
		if(retur.getDato().get(Calendar.MONTH) != m�ned)
			return null;
		return retur;
	}
	
	public Data getDenMedH�yesteTemp(Calendar fra, Calendar til)
	{/*<returner den noden(Data) med h�yestmakstemp i lista som ogs� har dato.after(fra) && dato.before(til)*/
		Data a = f�rste;
		Data retur = a;
		while(a != null)
		{
			
			if(a.getMaxTemp()>retur.getMaxTemp() &&
				(a.getDato().compareTo(fra) == 0 
				|| a.getDato().after(fra)&& a.getDato().before(til) 
				|| a.getDato().compareTo(til) == 0))
			{
				retur = a;
			}
			a=a.neste;
		}
		return retur;
	}
	
	public Data getDenMedMestNedb�r(Calendar fra, Calendar til)
	{/*<returnerer den noden som har mest nedb�r der dato.after(fra) && dato.before(til)>*/
		Data a = f�rste;
		Data retur = a;
		while(a != null)
		{
			
			if(a.getNedb�r()>retur.getNedb�r() &&
				(a.getDato().compareTo(fra) == 0 
				|| a.getDato().after(fra)&& a.getDato().before(til) 
				|| a.getDato().compareTo(til) == 0))
			{
				retur = a;
			}
			a=a.neste;
		}
		return retur;
	}
	
	public String visData(Calendar fra,Calendar til)
	{/*<returnerer en tostring med dataen fra nodene der dato.after(fra) && dato.before(til)>*/
		String returstreng = "Dato\tMinTemp\tMaxTemp\tNedb�r";
		
		Data a = f�rste;
		while(a != null)
		{
			if(a.getDato().compareTo(fra) == 0 
					|| a.getDato().after(fra)&& a.getDato().before(til) 
					|| a.getDato().compareTo(til) == 0)
			{
				returstreng += "\n" +a.toString();
			}
			a = a.neste;
		}
		return returstreng;
	}
	
	public int summerNedb�r(Calendar fra, Calendar til)
	{/*<returnerer summen av alle nodene.getNedb�r() der dato.after(fra) && dato.before(til)>*/
		int retur = 0;
		Data a = f�rste;
		
		while(a != null)
		{
			if(a.getDato().compareTo(fra) == 0 
					|| a.getDato().after(fra)&& a.getDato().before(til) 
					|| a.getDato().compareTo(til) == 0)
			{
				retur += a.getNedb�r();
			}
			a=a.neste;
		}
		return retur;
	}
	
	public String getGjennomsnittsMinTemp(Calendar fra, Calendar til)
	{/*<returnerer gjennomsnittsmimimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = f�rste;
		while(a != null)
		{
			if(a.getDato().compareTo(fra) == 0 
					|| a.getDato().after(fra)&& a.getDato().before(til) 
					|| a.getDato().compareTo(til) == 0)
			{
				sum += a.getMinTemp();
				antall++;
			}
			a=a.neste;
		}
		NumberFormat format = new DecimalFormat("#0.00");
		return format.format(sum/antall)+"�C";
	}
	
	public String getGjennomsnittsMaksTemp(Calendar fra, Calendar til)
	{/*<returnerer gjennomsnittsmaksimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = f�rste;
		while(a != null)
		{
			if(a.getDato().compareTo(fra) == 0 
					|| a.getDato().after(fra)&& a.getDato().before(til) 
					|| a.getDato().compareTo(til) == 0)
			{
				sum += a.getMaxTemp();
				antall++;
			}
			a=a.neste;
		}
		NumberFormat format = new DecimalFormat("#0.00");
		return format.format(sum/antall)+"�C";
	}
}
