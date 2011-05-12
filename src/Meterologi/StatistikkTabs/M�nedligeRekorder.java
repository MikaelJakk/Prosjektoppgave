package Meterologi.StatistikkTabs;

import java.util.Calendar;
import java.util.*;

public class MånedligeRekorder {
	
	final int startår = 1970;
	int sluttår;
	int fraår;
	int framnd, fradag;
	int tilår;
	int tilmnd, tildag;

	public String getMinTempRekorder()
	{/*Månedlige rekorder, det vil si tabeller som for hver måned i året viser høyeste 
	registrerte maksimumstemperatur (i løpet av alle år det er målinger for), verdien for denne, 
	stedet der den er målt (inklusive fylke), samt dato og årstall. 
	(Det kan være flere steder og/eller datoer for samme ekstremverdi.) 
	Tilsvarende tabeller for minimumstemperatur og nedbør i løpet av et døgn.
	*/
		/*skal returnere en streng med alle årets måneder med rekordene for hver måned,dato og hvor de er.
		 */

		Calendar nå = Calendar.getInstance();
		int sluttår = nå.get(Calendar.YEAR);
		
		for(int i=startår; i<sluttår ;i++)
		{
			Calendar månedstart = Calendar.getInstance();
			månedstart.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
			månedstart.set(fraår,framnd-1,fradag);/*måned-1 fordi Calendar.set() er teit*/
			Calendar månedslutt = Calendar.getInstance();
			månedslutt.setTimeInMillis(0); //hadde vært lettere med Date(år, måned, dato)
			månedslutt.set(fraår,framnd-1,fradag);/*måned-1 fordi Calendar.set() er teit*/
		}
	}
}
