/*
 * Skrevet av Thomas Nordengen den 12.april 2011
 */
package Meterologi.Lister;

import javax.swing.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class StedListe implements Serializable
{
	private static final long serialVersionUID = 1L;
	//private List<Sted> stedliste = new LinkedList<Sted>();
	//private TreeSet<Sted> stedliste = new TreeSet<Sted>(new Stedsammenlikner());
	private TreeSet<Sted> stedliste = new TreeSet<Sted>();

	//Setter inn sted bakerst i listen
	public void settInnFylke(Sted obj)
	{
		stedliste.add(obj);
	}
	
	public boolean nyData(String f, String s, Data d)
	{
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{	Sted gjeldende = iterator.next();
			if(gjeldende.getFylke().compareTo(f) == 0 
					&& gjeldende.getSted().compareTo(s) == 0)
				return gjeldende.nyData(d);
		}
		return false;
	}

	//Sorterer Sted-Lista alfabetisk basert p� Fylke
	public void sorter()
	{
		//Collections.sort(stedliste, new Stedsammenlikner());
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
	
	public String[] getFylkeArray()
	{
		int arraysize = 0;
		Iterator<Sted> iterator = stedliste.iterator();
		if(iterator.hasNext())
		{}
		while(iterator.hasNext())
		{arraysize++; iterator.next();}
		String[] returting = new Array[arraysize];
	}
	
	public String skrivUtDataListe(String f, String s)
	{
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{	Sted gjeldende = iterator.next();
			if(gjeldende.getFylke().equals(f)&& gjeldende.getSted().equals(s))
				return gjeldende.dataliste.skrivUtListe();
		}
		return "ingen data";
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
}





