/*
 * Skrevet av Thomas Nordengen og Mikael Jakhelln 12.april 2011
 */
package Meterologi.Lister;

import java.io.Serializable;
//import java.util.*;
import java.text.*;
public class Sted implements Serializable, Comparable<Sted>{

		private final static String rekkefølge =
			"<\0<0<1<2<3<4<5<6<7<8<9" +
            "<A,a<B,b<C,c<D,d<E,e<F,f<G,g<H,h<I,i<J,j" +
           "<K,k<L,l<M,m<N,n<O,o<P,p<Q,q<R,r<S,s<T,t" +
           "<U,u<V,v<W,w<X,x<Y,y<Z,z<Æ,æ<Ø,ø<Å=AA,å=aa;AA,aa";

		private static RuleBasedCollator kollator;
		
		static {
			try
			{
				kollator = new RuleBasedCollator (rekkefølge);
			}
			catch(ParseException pe)
			{
				System.out.println("Det oppstod en feil i sorteringen!");
				System.exit(1);
			}
		}

		public int compareTo(Sted sted)
		{
			String nr1 = getFylke();
			String nr2 = sted.getFylke();
			String f1 = getSted();
			String f2 = sted.getSted();
			int d = kollator.compare(nr1, nr2);
			if( d != 0)
				return d;
			else
				return kollator.compare(f1,f2);
		}

	private static final long serialVersionUID = 1L;
	public DataListe dataliste;
	private String sted;
	private String fylke;

	public Sted(String s, String f)
	{
		fylke = f;
		sted = s;
		dataliste = new DataListe();
	}

	public String getSted()
	{
		return sted;
	}
	public String getFylke()
	{
		return fylke;
	}

	public boolean nyData(Data n)
	{	/*sjekker igjennom lista, om det er registrert data på samma dato som
		parameterens dato, skal den returnere false.
		Ved vellykket innsetting skal den returnere true
		*/
		if( !datoEksisterer(n) )
			{
				return dataliste.nyData(n);
			}
		else return false;
	}

	public boolean datoEksisterer(Data n)
	//sjekker igjennom datalista om dato er registrert ifra før
	{
		if(dataliste.datoEksisterer(n))
			return true;
		return false;
	}

	public String toString()
	{
		return fylke + "\t\t" + sted;
	}
}
