package Meterologi.StatistikkTabs;
import java.util.*;
import java.awt.FlowLayout;

import javax.swing.*;

import Meterologi.Lista;
import Meterologi.*;

public class MånedligeRekorder extends Lista

{
	
	final int startår = 1970;
	int sluttår;
	int fraår;
	int framnd; 
	int fradag;
	int tilår;
	int tilmnd;
	int tildag;


	private JTextArea utskrift;
	private JComboBox velgår;
	


	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		utskrift = new JTextArea(25,60);
		
		panel.add(utskrift);
		
		
		return panel;
	}




	public void getMinTempRekorder()
	{/*Månedlige rekorder, det vil si tabeller som for hver måned i året viser høyeste 
	registrerte maksimumstemperatur (i løpet av alle år det er målinger for), verdien for denne, 
	stedet der den er målt (inklusive fylke), samt dato og årstall. 
	(Det kan være flere steder og/eller datoer for samme ekstremverdi.) 
	Tilsvarende tabeller for minimumstemperatur og nedbør i løpet av et døgn.
	*/
		/*skal returnere en streng med alle årets måneder med rekordene for hver måned,dato og hvor de er.
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
