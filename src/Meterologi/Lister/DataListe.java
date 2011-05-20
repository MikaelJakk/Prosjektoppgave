/*
 * Skrevet av Mikael Jakhelln den 5.Mai 2011
 */

package Meterologi.Lister;

import java.io.*;
import java.text.*;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class DataListe{

	
	private Data f�rste;
	
	//Metoder for grunnleggende innsetting, sletting og visning av data
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

	public Data getData(Data n)
	{	
		if(f�rste == null)
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
	//end of grunnleggende metoder
	
	
	//Metoder for statistisk visning av data
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
	public double getGjennomsnittsMinTempVerdi(Calendar fra, Calendar til)
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
		return sum/antall;
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
	
	public boolean finnesM�ned(int m�ned)
	{/*sjekker igjennom lista og sjekker om det finnes data p� valgt m�ned
	 	returnerer true hvis det finnes en med lavest temp, false hvis det ikke gj�r det(tom liste)*/
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
	
	public Data getLavestTempIM�ned(int m�ned)
	{/*returnerer den noden i valgt m�ned som har lavest temperatur
	 	fins det ingen noder p� valgt m�ned returnerer den null*/
		Data a = f�rste;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.MONTH) == m�ned && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.MONTH) == m�ned && retur != null)
			{
				if(retur.getMinTemp() > a.getMinTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public Data getH�yestTempIM�ned(int m�ned)
	{/*returnerer den noden i valgt m�ned som har lavest temperatur
	 	fins det ingen noder p� valgt m�ned returnerer den null*/
		Data a = f�rste;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.MONTH) == m�ned && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.MONTH) == m�ned && retur != null)
			{
				if(retur.getMaxTemp() < a.getMaxTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public Data getMestNedb�rIM�ned(int m�ned)
	{/*returnerer den noden i valgt m�ned som har lavest temperatur
	 	fins det ingen noder p� valgt m�ned returnerer den null*/
		Data a = f�rste;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.MONTH) == m�ned && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.MONTH) == m�ned && retur != null)
			{
				if(retur.getNedb�r() < a.getNedb�r())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public boolean finnesI�r(int �r)
	{
		Data a = f�rste;
		while(a != null)
		{
			
			if(a.getDato().get(Calendar.YEAR) == �r)
			{
				return true;
			}
			a=a.neste;
		}
		return false;	
	}
	
	public Data getLavestTempI�r(int �r)
	{
		Data a = f�rste;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.YEAR) == �r && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.YEAR) == �r && retur != null)
			{
				if(retur.getMinTemp() > a.getMinTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public Data getH�yesteTempI�r(int �r)
	{
		Data a = f�rste;
		Data retur = null;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.YEAR) == �r && retur == null)
				retur = a;
			else if(a.getDato().get(Calendar.YEAR) == �r && retur != null)
			{
				if(retur.getMaxTemp() < a.getMaxTemp())
					retur =a;
			}
			a = a.neste;
		}
		return retur;
	}
	
	public int getTotalNedb�rI�r(int �r)
	{/*returnerer den noden i valgt m�ned som har lavest temperatur
	 	fins det ingen noder p� valgt m�ned returnerer den null*/
		Data a = f�rste;
		int nedb�r=0;
		while(a!=null)
		{
			if(a.getDato().get(Calendar.YEAR) == �r)
			{
				nedb�r += a.getNedb�r();
			}
			a = a.neste;
		}
		return nedb�r;
	}
	
	public double getGjennomsnittsMinTempI�r(int �r)
	{/*<returnerer et gjennomsnitt av minimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = f�rste;
		while(a != null)
		{
			if(a.getDato().get(Calendar.YEAR) == �r)
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
	
	public double getGjennomsnittsMaksTempI�r(int �r)
	{/*<returnerer gjennomsnitts maksimumstemperaturen for noder mellom fra og til>*/
		double sum = 0.0;
		int antall = 0;
		Data a = f�rste;
		while(a != null)
		{
			if(a.getDato().get(Calendar.YEAR) == �r)
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
	
	public int getSammenhengendeNullNedb�rMellom(int fra, int til)
	{/*skal g� igjennom lista og returnere en int som beskriver antall sammenhengende dager uten nedb�r
	 	fungerer bare n�r vi har sammenhengende datoer*/
		if(f�rste == null)
			return 0;
		
		Data a = f�rste;
		int retur = 0;
		while(a != null)
		{
			
			if(a.getDato().YEAR == fra)
			{
			 JOptionPane.showMessageDialog(null, "Velg riktig �r!");
			}
			else if(a.getNedb�r()== 0)
				{
				retur++;
				}
			a = a.neste;
		}
	
		return retur;
	}
	public int getMotsattNedb�r()
	{/*skal g� igjennom lista og returnere en int som beskriver antall sammenhengende dager uten nedb�r
	 	fungerer bare n�r vi har sammenhengende datoer*/
		if(f�rste == null)
			return 0;
		
		Data a = f�rste;
		int retur = 0;
		while(a != null)
		{
			 if(a.getNedb�r()!= 0)
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
			if(f�rste == null)
				return;
			
			DataOutputStream ut = null;
			Data a = f�rste;
			
			while(a != null)
			{
				ut = new DataOutputStream( new FileOutputStream( filsti+a.getDatoString()+".dat" ) );
				ut.writeLong(a.getDato().getTimeInMillis());
				ut.writeDouble(a.getMinTemp());
				ut.writeDouble(a.getMaxTemp());
				ut.writeDouble(a.getNedb�r());
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
						double nedb�r = inn.readDouble();
						inn.close();
						
						Data nydata = new Data(dato,mintemp,maxtemp,nedb�r);
						nyData(nydata);
					}
					catch(Exception ex){ex.printStackTrace();}
				}
			}
		}
	}//end of lesFraFil()
}//end of class
