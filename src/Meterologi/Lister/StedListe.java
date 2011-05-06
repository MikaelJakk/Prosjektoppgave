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
	
	public int getListeSize()
	{
		int listeSize = stedliste.size();
		
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			listeSize++;
		}
		return listeSize;
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
	public void slettStedNode(String f, String s)
	{
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			Sted denne = iterator.next();
			if(denne.getFylke().equals(f) && denne.getSted().equals(s))
				iterator.remove();
		}
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
	
	public String[] fjernDuplikater(String[] array)
	{//kode fra http://www.kodejava.org/examples/194.html
		List<String> liste = Arrays.asList(array);
		Set<String> sett = new HashSet<String>(liste);
		
		String[] returarray = new String[sett.size()];
		sett.toArray(returarray);
		
		return returarray;
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
		
		/*
		Iterator<Sted> iterator = stedliste.iterator();
		int arraysize = stedliste.size();
		String[] midlertidigarray = new String[arraysize];
		int i=0;
		while(iterator.hasNext())
		{
			Sted gjeldende = iterator.next();
			midlertidigarray[i++] = gjeldende.getFylke();
		}
		
		return fjernDuplikater(midlertidigarray);
		*/
	}
	
	// returnerere en array som inneholder alle Stedene som er registrert på hvert fylke
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
		String result = "";

		result += "Registrerete Fylker:" + "\n" + skrivUt() + "\n";

		return result;
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
/*
class Stedsammenlikner implements Comparator<Sted>
{
	//Gjennoml�per og skriver ut lista alfabetisk

	//Definerer rekkef�lgen p� sorteringen
	String rekkefølge =
		"<\0<0<1<2<3<4<5<6<7<8<9" +
        "<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j" +
       "<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t" +
       "<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,<æ,Ø<ø=AA,å=aa;AA,aa";

	private RuleBasedCollator kollator;
	public Stedsammenlikner()
	{
		try
		{
			kollator = new RuleBasedCollator (rekkefølge);
		}
		catch(ParseException pe)
		{
			JOptionPane.showMessageDialog(null, "Det oppstod en feil i sorteringen!");
			System.exit(0);
		}
	}

	public int compare(Sted p1, Sted p2)
	{
		String nr1 = p1.getFylke();
		String nr2 = p2.getFylke();
		String f1 = p1.getSted();
		String f2 = p2.getSted();
		int d = kollator.compare(nr1, nr2);
		if( d != 0)
			return d;
		else
			return kollator.compare(f1,f2);
	}
}//end of comparator klassen
*/
