package Meterologi.StatistikkTabs;
import java.util.Calendar;
import java.util.*;
import java.awt.FlowLayout;

import javax.swing.*;
import java.util.Calendar;
import java.util.*;

public class M�nedligeRekorder

{
	
	final int start�r = 1970;
	int slutt�r;
	int fra�r;
	int framnd; 
	int fradag;
	int til�r;
	int tilmnd;
	int tildag;


	private JTextArea utskrift;
	private JComboBox velg�r;
	


	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		utskrift = new JTextArea(25,50);
		
		panel.add(utskrift);
		
		
		return panel;
	}




	public String getMinTempRekorder()
	{/*M�nedlige rekorder, det vil si tabeller som for hver m�ned i �ret viser h�yeste 
	registrerte maksimumstemperatur (i l�pet av alle �r det er m�linger for), verdien for denne, 
	stedet der den er m�lt (inklusive fylke), samt dato og �rstall. 
	(Det kan v�re flere steder og/eller datoer for samme ekstremverdi.) 
	Tilsvarende tabeller for minimumstemperatur og nedb�r i l�pet av et d�gn.
	*/
		/*skal returnere en streng med alle �rets m�neder med rekordene for hver m�ned,dato og hvor de er.
		 */

		Calendar n� = Calendar.getInstance();
		int slutt�r = n�.get(Calendar.YEAR);
		
		for(int i=start�r; i<slutt�r ;i++)
		{
			Calendar m�nedstart = Calendar.getInstance();
			m�nedstart.setTimeInMillis(0); //hadde v�rt lettere med Date(�r, m�ned, dato)
			m�nedstart.set(fra�r,framnd-1,fradag);/*m�ned-1 fordi Calendar.set() er teit*/
			Calendar m�nedslutt = Calendar.getInstance();
			m�nedslutt.setTimeInMillis(0); //hadde v�rt lettere med Date(�r, m�ned, dato)
			m�nedslutt.set(fra�r,framnd-1,fradag);/*m�ned-1 fordi Calendar.set() er teit*/
		}
		return null;
	}
}
