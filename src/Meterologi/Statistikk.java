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
		MånedligeRekorder månedrec = new MånedligeRekorder();
		RankingListe ranking = new RankingListe();
		SnittTemp  snitttemp = new SnittTemp();
		ÅrligeEkstremer årligeks = new ÅrligeEkstremer();
		RegnData regndata = new RegnData();
		
		JPanel panelet = new JPanel();
		panelet.setLayout(new FlowLayout());

		JPanel p1 = månedrec.ByggPanel();
		JPanel p2 = ranking.ByggPanel();
		JPanel p3 = snitttemp.ByggPanel();
		JPanel p4 = årligeks.ByggPanel();
		JPanel p5 = regndata.ByggPanel();
			

		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Rankingliste", p2);
		pane.addTab("Årlige Ekstremer", p4);
		pane.addTab("Månedlige Rekorder", p1);
		pane.addTab("Snitts Tempratur",p3);
		pane.addTab("Regn Data",p5);

	

		
		
		panelet.add(pane);
		return panelet;
	}

	void RegNySted() //utføres etter knappe RegNySted er trykket på
	{
	
	}




}