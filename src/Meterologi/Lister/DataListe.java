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
	private Data første;
	
	public boolean nyData(Data n)
	{	
		if(første == null)//hvis første er null, blir n ny første
		{
			første = n;
			return true;
		}
		if(første.getDato().after(n.getDato()))
		//hvis første.dato er etter n.dato 
		//blir n satt forrest i lista
		{
			settInnForrest(n);
			return true;
		}
		
		Data a = første;
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
		n.neste = første;
		første = n;
	}
	
	public boolean datoEksisterer(Data n)
	{
		if(første == null)
			return false;
		
		if(første.getDato().compareTo(n.getDato()) == 0)
			return true;
		
		Data a = første;
		
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
		if(første == null) //hvis første er null så er lista tom..
			return null;
		
		if(første.getDato().compareTo(n.getDato()) == 0)
			return første;
		
		Data ny = første;
		
		while(ny != null)
		{
			if(ny.getDato().compareTo(n.getDato())== 0)
				return ny;
			ny = ny.neste;
			if(ny.neste == null)
				return ny;
		}
		/*unødvendig kode.. etter while løkka er ny alltid null..
		 * if(ny.getDato().compareTo(n.getDato())== 0)
			return ny;*/
		return null;
	}
	
	public String skrivUtListe()
	{
		String retur = "Dato\tMinTemp\tMaxTemp\tNedbør\n";
		if(første == null)
			return "ingen data";
		
		if(første != null)
		{
			retur += første.toString() +"\n";
		}
		
		Data a = første;
		while(a.neste != null)
		{
			retur += a.neste.toString()+"\n";
			a = a.neste;
		}
		return retur;
	}
	
	public boolean slettData(Data d)
	{
		if(første == null)
			return false;
		if(første.getDato().equals(d.getDato()))
		{
			første = første.neste;
			return true;
		}
		else
		{
			Data a = første;
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
		if(første == null)
			return true;
		return false;
	}
	
	public Data getDenMedLavestTemp(Calendar fra, Calendar til)
	{/*<returner den noden(Data) med minst temp i lista som også har dato.after(fra) && dato.before(til)>*/
		Data a = første;
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
	public boolean finnesLavestTempMåned(int måned)
	{
		Data a = første;
		while(a != null)
		{
			
			if(a.getDato().get(Calendar.MONTH) == måned)
			{
				return true;
			}
			a=a.neste;
		}
		return false;	
	}
	
	public Data getLavestTempMåned(int måned)
	{/*returnerer den noden i valgt måned som har lavest temperatur
	 	fins det ingen noder på valgt måned returnerer den null*/
		Data a = første;
		Data retur = a;
		while(a != null)
		{
			if(a.getMinTemp()<retur.getMinTemp() && a.getDato().get(Calendar.MONTH) == måned)
			{
				retur = a;
			}
			a=a.neste;
		}
		if(retur.getDato().get(Calendar.MONTH) != måned)
			return null;
		return retur;
	}
	
	public Data getDenMedHøyesteTemp(Calendar fra, Calendar til)
	{/*<returner den noden(Data) med høyestmakstemp i lista som også har dato.after(fra) && dato.before(til)*/
		Data a = første;
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
	
	public Data getDenMedMestNedbør(Calendar fra, Calendar til)
	{/*<returnerer den noden som har mest nedbør der dato.after(fra) && dato.before(til)>*/
		Data a = første;
		Data retur = a;
		while(a != null)
		{
			
			if(a.getNedbør()>retur.getNedbør() &&
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
		String returstreng = "Dato\tMinTemp\tMaxTemp\tNedbør";
		
		Data a = første;
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
	
	public int summerNedbør(Calendar fra, Calendar til)
	{/*<returnerer summen av alle nodene.getNedbør() der dato.after(fra) && dato.before(til)>*/
		int retur = 0;
		Data a = første;
		
		while(a != null)
		{
			if(a.getDato().compareTo(fra) == 0 
					|| a.getDato().after(fra)&& a.getDato().before(til) 
					|| a.getDato().compareTo(til) == 0)
			{
				retur += a.getNedbør();
			}
			a=a.neste;
		}
		return retur;
	}
	
	public String getGjennomsnittsMinTemp(Calendar fra, Calendar til)
	{/*<returnerer gjennomsnittsmimimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = første;
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
		return format.format(sum/antall)+"ºC";
	}
	
	public String getGjennomsnittsMaksTemp(Calendar fra, Calendar til)
	{/*<returnerer gjennomsnittsmaksimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = første;
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
		return format.format(sum/antall)+"ºC";
	}
}
