package Meterologi;
////////////////////////////////////////
//Gui-vindu 4: STATISTIKK///////////////
//skrevet av Nam Le/////////////////////
////////////////////////////////////////

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import Meterologi.StatistikkTabs.*;
public class Statistikk extends JFrame
{
	
	
	
	
	public JPanel ByggPanel() //utseende
	{
		M�nedligeRekorder m�nedrec = new M�nedligeRekorder();
		RankingListe ranking = new RankingListe();
		SnittTemp  snitttemp = new SnittTemp();
		�rligeEkstremer �rligeks = new �rligeEkstremer();
		RegnData regndata = new RegnData();
		
		JPanel panelet = new JPanel();
		panelet.setLayout(new FlowLayout());

		JPanel p1 = m�nedrec.ByggPanel();
		JPanel p2 = ranking.ByggPanel();
		JPanel p3 = snitttemp.ByggPanel();
		JPanel p4 = �rligeks.ByggPanel();
		JPanel p5 = regndata.ByggPanel();
			

		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Rankingliste", p2);
		pane.addTab("�rlige Ekstremer", p4);
		pane.addTab("M�nedlige Rekorder", p1);
		pane.addTab("Snitts Tempratur",p3);
		pane.addTab("Regn Data",p5);

	

		
		
		panelet.add(pane);
		return panelet;
	}

	void RegNySted() //utf�res etter knappe RegNySted er trykket p�
	{
	
	}




}