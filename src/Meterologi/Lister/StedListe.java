/*
 * Skrevet av Thomas Nordengen den 4 april 2011
 */


import javax.swing.*;
import java.io.*; //For senere serializable
import java.text.*;
import java.util.*;

public class StedListe
{
	private List<Sted> stedliste = new LinkedList<Sted>();
	
	//Setter inn sted bakerst i listen
	public void setInnFylke(Sted obj)
	{
		stedliste.add(obj);
	}
	
	//Sorterer Sted-Lista alfabetisk basert på Fylke
	public void sorter()
	{
		Collections.sort(stedliste, new Stedsammenlikner());
	}
	
	//Gjennomløper og skriver ut lista alfabetisk
	public String skrivUt()
	{
		sorter();
		String output = "";
		Iterator<Sted> iterator = stedliste.iterator();
		while(iterator.hasNext())
		{
			output += iterator.next().toString() + "\n";
		}
		return output;
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

		result += "Dette er den totale og sorterte listen av alle stedene." + skrivUt() + "\n";

		return result;
	}
}

class Stedsammenlikner implements Comparator<Sted>
{
	//Gjennomløper og skriver ut lista alfabetisk
	
		//Definerer rekkefølgen på sorteringen
		String rekkefølge = 
			"<\0<0<1<2<3<4<5<6<7<8<9" +
            "<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j" +
           "<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t" +
           "<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";
		
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





