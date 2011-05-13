package Meterologi.StatistikkTabs;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Meterologi.*;

public class MånedligeRekorder extends Lista implements ActionListener

{
	
	final int startår = 1970;
	Calendar nå = Calendar.getInstance();
	int sluttår = nå.get(Calendar.YEAR);
	
	int fraår,framnd,fradag,tilår,tilmnd,tildag;


	private JRadioButton makstemp, mintemp, nedbør;
	private ButtonGroup knappegruppe;
	private JTextArea utskrift;

	public JPanel ByggPanel() //utseende
	{
		
		makstemp = new JRadioButton("Høyest Temp");
		mintemp = new JRadioButton("Lavest Temp");
		nedbør = new JRadioButton("Mest Nedbør");
		makstemp.addActionListener(this);
		mintemp.addActionListener(this);
		nedbør.addActionListener(this);
		knappegruppe = new ButtonGroup();
		knappegruppe.add(makstemp);
		knappegruppe.add(mintemp);
		knappegruppe.add(nedbør);
		
		JPanel venstre = new JPanel();
		venstre.setLayout(new GridLayout(0,1));
		venstre.add(makstemp);
		venstre.add(mintemp);
		venstre.add(nedbør);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(venstre, BorderLayout.WEST);
		utskrift = new JTextArea(25,60);
		utskrift.setEditable(false);
		panel.add(utskrift,BorderLayout.CENTER);
		
		return panel;
	}
	
	public String[] makeYearArray()
	{
		String[] dagarray = new String[sluttår-startår+1];
		for(int i = startår; i <= sluttår; i++)
		{
			dagarray[i-startår] = i + "";
		}
		return dagarray;
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
		}catch(Exception ex){System.out.println("Feil: ved utregning av månedlige ekstremer " +ex);}
		utskrift.setText(retur);
	}
	public void getMaxTempRekorder()
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
		String retur = "Måned\tFylke\tSted\tMaxTemp\tDato";

		try{
			retur += "\nJanuar:\t"+stedliste.getMaxTempForMåned(Calendar.JANUARY);
			retur += "\nFebruar:\t"+stedliste.getMaxTempForMåned(Calendar.FEBRUARY);
			retur += "\nMars:\t"+stedliste.getMaxTempForMåned(Calendar.MARCH);
			retur += "\nApril:\t"+stedliste.getMaxTempForMåned(Calendar.APRIL);
			retur += "\nMars:\t"+stedliste.getMaxTempForMåned(Calendar.MARCH);
			retur += "\nMai:\t"+stedliste.getMaxTempForMåned(Calendar.MAY);
			retur += "\nJuni:\t"+stedliste.getMaxTempForMåned(Calendar.JUNE);
			retur += "\nJuli:\t"+stedliste.getMaxTempForMåned(Calendar.JULY);
			retur += "\nAugust:\t"+stedliste.getMaxTempForMåned(Calendar.AUGUST);
			retur += "\nSeptember:\t"+stedliste.getMaxTempForMåned(Calendar.SEPTEMBER);
			retur += "\nOktober:\t"+stedliste.getMaxTempForMåned(Calendar.OCTOBER);
			retur += "\nNovember:\t"+stedliste.getMaxTempForMåned(Calendar.NOVEMBER);
			retur += "\nDesember:\t"+stedliste.getMaxTempForMåned(Calendar.DECEMBER);
		}catch(Exception ex){System.out.println("Feil: ved utregning av månedlige ekstremer " +ex);}
		utskrift.setText(retur);
	}
	
	public void getNedbørRekorder()
	{
		if(stedliste.tomListe())
		{
			utskrift.setText("ingen steder er registrert");
			return;
		}
		String retur = "Måned\tFylke\tSted\tNedbør\tDato";

		try{
			retur += "\nJanuar:\t"+stedliste.getMestNedbørForMåned(Calendar.JANUARY);
			retur += "\nFebruar:\t"+stedliste.getMestNedbørForMåned(Calendar.FEBRUARY);
			retur += "\nMars:\t"+stedliste.getMestNedbørForMåned(Calendar.MARCH);
			retur += "\nApril:\t"+stedliste.getMestNedbørForMåned(Calendar.APRIL);
			retur += "\nMars:\t"+stedliste.getMestNedbørForMåned(Calendar.MARCH);
			retur += "\nMai:\t"+stedliste.getMestNedbørForMåned(Calendar.MAY);
			retur += "\nJuni:\t"+stedliste.getMestNedbørForMåned(Calendar.JUNE);
			retur += "\nJuli:\t"+stedliste.getMestNedbørForMåned(Calendar.JULY);
			retur += "\nAugust:\t"+stedliste.getMestNedbørForMåned(Calendar.AUGUST);
			retur += "\nSeptember:\t"+stedliste.getMestNedbørForMåned(Calendar.SEPTEMBER);
			retur += "\nOktober:\t"+stedliste.getMestNedbørForMåned(Calendar.OCTOBER);
			retur += "\nNovember:\t"+stedliste.getMestNedbørForMåned(Calendar.NOVEMBER);
			retur += "\nDesember:\t"+stedliste.getMestNedbørForMåned(Calendar.DECEMBER);
		}catch(Exception ex){System.out.println("Feil: ved utregning av månedlige ekstremer " +ex);}
		utskrift.setText(retur);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == makstemp )
		{
			getMaxTempRekorder();
		}
		
		if(e.getSource() == mintemp )
		{
			getMinTempRekorder();
		}
		
		if(e.getSource() == nedbør)
		{
			getNedbørRekorder();
		}
		
	}
}
