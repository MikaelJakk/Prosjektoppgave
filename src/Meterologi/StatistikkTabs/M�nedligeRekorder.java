package Meterologi.StatistikkTabs;
import java.util.*;
import java.awt.FlowLayout;

import javax.swing.*;

import Meterologi.Lista;
import Meterologi.*;

public class M�nedligeRekorder extends Lista

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
		utskrift = new JTextArea(25,60);
		
		panel.add(utskrift);
		
		
		return panel;
	}




	public void getMinTempRekorder()
	{/*M�nedlige rekorder, det vil si tabeller som for hver m�ned i �ret viser h�yeste 
	registrerte maksimumstemperatur (i l�pet av alle �r det er m�linger for), verdien for denne, 
	stedet der den er m�lt (inklusive fylke), samt dato og �rstall. 
	(Det kan v�re flere steder og/eller datoer for samme ekstremverdi.) 
	Tilsvarende tabeller for minimumstemperatur og nedb�r i l�pet av et d�gn.
	*/
		/*skal returnere en streng med alle �rets m�neder med rekordene for hver m�ned,dato og hvor de er.
		 */
		String retur = "";

		try{
			retur += "\nJanuar:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.JANUARY);
			retur += "\nFebruar:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.FEBRUARY);
			retur += "\nMars:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.MARCH);
			retur += "\nApril:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.APRIL);
			retur += "\nMars:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.MARCH);
			retur += "\nMai:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.MAY);
			retur += "\nJuni:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.JUNE);
			retur += "\nJuli:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.JULY);
			retur += "\nAugust:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.AUGUST);
			retur += "\nSeptember:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.SEPTEMBER);
			retur += "\nOktober:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.OCTOBER);
			retur += "\nNovember:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.NOVEMBER);
			retur += "\nDesember:\t"+stedliste.getDataogStedMedMinsteTemp(Calendar.DECEMBER);
		}catch(Exception ex){ex.printStackTrace();}
		utskrift.setText(retur);
	}
}
