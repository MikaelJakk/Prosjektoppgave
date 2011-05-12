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
		if(stedliste.tomListe())
		{
			utskrift.setText("ingen steder er registrert");
			return;
		}
		String retur = "Måned\tFylke\tSted\tMinTemp\tDato";

		try{
			retur += "\nJanuar:\t"+stedliste.getMinTempForMåned(Calendar.JANUARY);
			retur += "\nFebruar:\t"+stedliste.getMinTempForMåned(Calendar.FEBRUARY);
			retur += "\nMars:\t"+stedliste.getMinTempForMåned(Calendar.MARCH);
			retur += "\nApril:\t"+stedliste.getMinTempForMåned(Calendar.APRIL);
			retur += "\nMars:\t"+stedliste.getMinTempForMåned(Calendar.MARCH);
			retur += "\nMai:\t"+stedliste.getMinTempForMåned(Calendar.MAY);
			retur += "\nJuni:\t"+stedliste.getMinTempForMåned(Calendar.JUNE);
			retur += "\nJuli:\t"+stedliste.getMinTempForMåned(Calendar.JULY);
			retur += "\nAugust:\t"+stedliste.getMinTempForMåned(Calendar.AUGUST);
			retur += "\nSeptember:\t"+stedliste.getMinTempForMåned(Calendar.SEPTEMBER);
			retur += "\nOktober:\t"+stedliste.getMinTempForMåned(Calendar.OCTOBER);
			retur += "\nNovember:\t"+stedliste.getMinTempForMåned(Calendar.NOVEMBER);
			retur += "\nDesember:\t"+stedliste.getMinTempForMåned(Calendar.DECEMBER);
		}catch(Exception ex){ex.printStackTrace();}
		utskrift.setText(retur);
	}
}
