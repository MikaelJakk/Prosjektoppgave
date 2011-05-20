/*
 * Skrevet av Mikael Jakhelln den 5.Mai 2011
 */

package Meterologi.Lister;

import java.io.*;
import java.text.*;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class DataListe{

	
	private Data første;
	
	//Metoder for grunnleggende innsetting, sletting og visning av data
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

	public Data getData(Data n)
	{	
		if(første == null)
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
	//end of grunnleggende metoder
	
	
	//Metoder for statistisk visning av data
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
	public double getGjennomsnittsMinTempVerdi(Calendar fra, Calendar til)
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
		return sum/antall;
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
	
	public boolean finnesMåned(int måned)
	{/*sjekker igjennom lista og sjekker om det finnes data på valgt måned
	 	returnerer true hvis det finnes en med lavest temp, false hvis det ikke gjør det(tom liste)*/
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
	
	public Data getLavestTempIMåned(int måned)
	{/*returnerer den noden i valgt måned som har lavest temperatur
	 	fins det ingen noder på valgt måned returnerer den null*/
		Data a = første;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.MONTH) == måned && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.MONTH) == måned && retur != null)
			{
				if(retur.getMinTemp() > a.getMinTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public Data getHøyestTempIMåned(int måned)
	{/*returnerer den noden i valgt måned som har lavest temperatur
	 	fins det ingen noder på valgt måned returnerer den null*/
		Data a = første;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.MONTH) == måned && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.MONTH) == måned && retur != null)
			{
				if(retur.getMaxTemp() < a.getMaxTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public Data getMestNedbørIMåned(int måned)
	{/*returnerer den noden i valgt måned som har lavest temperatur
	 	fins det ingen noder på valgt måned returnerer den null*/
		Data a = første;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.MONTH) == måned && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.MONTH) == måned && retur != null)
			{
				if(retur.getNedbør() < a.getNedbør())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public boolean finnesIÅr(int år)
	{
		Data a = første;
		while(a != null)
		{
			
			if(a.getDato().get(Calendar.YEAR) == år)
			{
				return true;
			}
			a=a.neste;
		}
		return false;	
	}
	
	public Data getLavestTempIÅr(int år)
	{
		Data a = første;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.YEAR) == år && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.YEAR) == år && retur != null)
			{
				if(retur.getMinTemp() > a.getMinTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public Data getHøyesteTempIÅr(int år)
	{
		Data a = første;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.YEAR) == år && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.YEAR) == år && retur != null)
			{
				if(retur.getMaxTemp() < a.getMaxTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public int getTotalNedbørIÅr(int år)
	{/*returnerer den noden i valgt måned som har lavest temperatur
	 	fins det ingen noder på valgt måned returnerer den null*/
		Data a = første;
		int nedbør=0;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.YEAR) == år)
			{
				nedbør += a.getNedbør();
			}
			a = a.neste;
		}
		return nedbør;
	}
	
	public double getGjennomsnittsMinTempIÅr(int år)
	{/*<returnerer et gjennomsnitt av minimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = første;
		while(a != null)
		{
			if(a.getDato().get(Calendar.YEAR) == år)
			{
				sum += a.getMinTemp();
				antall++;
			}
			a=a.neste;
		}
		if(antall == 0)
			return 0;
		
		return sum/antall;
	}
	
	public double getGjennomsnittsMaksTempIÅr(int år)
	{/*<returnerer gjennomsnitts maksimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = første;
		while(a != null)
		{
			if(a.getDato().get(Calendar.YEAR) == år)
			{
				sum += a.getMaxTemp();
				antall++;
			}
			a=a.neste;
		}
		if(antall == 0)
			return 0;
		return sum/antall;
	}
	
	public int getSammenhengendeNullNedbørMellom(int fra, int til)
	{/*skal gå igjennom lista og returnere en int som beskriver antall sammenhengende dager uten nedbør
	 	fungerer bare når vi har sammenhengende datoer*/
		if(første == null)
			return 0;
		
		Data a = første;
		int retur = 0;
		while(a != null)
		{
			
			if(a.getDato().YEAR == fra)
			{
			 JOptionPane.showMessageDialog(null, "Velg riktig år!");
			}
			else if(a.getNedbør()== 0)
				{
				retur++;
				}
			a = a.neste;
		}
	
		return retur;
	}
	public int getMotsattNedbør()
	{/*skal gå igjennom lista og returnere en int som beskriver antall sammenhengende dager uten nedbør
	 	fungerer bare når vi har sammenhengende datoer*/
		if(første == null)
			return 0;
		
		Data a = første;
		int retur = 0;
		while(a != null)
		{
			 if(a.getNedbør()!= 0)
				{
				retur++;
				}
			a = a.neste;
		}
	
		return retur;
	}
	
	public int regnUtDagerMellom(Calendar fra, Calendar til)
	{/*skal regne ut hvor mange dager det er mellom fra og til dato*/
		return Math.round((til.getTimeInMillis() - fra.getTimeInMillis()) /(24*60*60*1000));
	}
	//end of metoder for statistisk visning
	
	//metoder for lagring og lesing (Dataoutput)
	public void skrivTilFil( String filsti )
	{
		try
		{
			if(første == null)
				return;
			
			DataOutputStream ut = null;
			Data a = første;
			
			while(a != null)
			{
				ut = new DataOutputStream( new FileOutputStream( filsti+a.getDatoString()+".dat" ) );
				ut.writeLong(a.getDato().getTimeInMillis());
				ut.writeDouble(a.getMinTemp());
				ut.writeDouble(a.getMaxTemp());
				ut.writeDouble(a.getNedbør());
				a = a.neste;
			}
			ut.close();
		}
		catch ( IOException e )
		{
		  System.out.println( "Filproblem. "+e );
		}
	}//end of skrivTilFil()

	public void lesFraFil(File datamappepeker)
	{/*skal lese inn filen Data.dat ifra filstien som er angitt..*/

		if(datamappepeker.exists())
		{
			File[] filer = datamappepeker.listFiles();
			if(filer.length != 0)
			{
				
				for(int i=0;i<filer.length;i++)
				{
					try{
						DataInputStream inn = new DataInputStream(
								new BufferedInputStream(
										new FileInputStream(
												datamappepeker+"/"+filer[i].getName()) ) );
						Calendar dato = Calendar.getInstance();
						dato.setTimeInMillis(inn.readLong());
						double mintemp = inn.readDouble();
						double maxtemp = inn.readDouble();
						double nedbør = inn.readDouble();
						inn.close();
						
						Data nydata = new Data(dato,mintemp,maxtemp,nedbør);
						nyData(nydata);
					}
					catch(Exception ex){ex.printStackTrace();}
				}
			}
		}
	}//end of lesFraFil()
}//end of class
