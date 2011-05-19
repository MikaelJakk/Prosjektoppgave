/*
 * Skrevet av Mikael Jakhelln 5.Mai 2011
 */

package Meterologi.StatistikkTabs;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

import Meterologi.Lista;

public class �rligeEkstremer extends Lista implements ActionListener
{
	private JTextArea utskrift;
	
	private JComboBox �rvalg;
	int valgt�r = 1970;
	//lager streng[] for �rvalg utifra maskinens kalender�r
	int fra�r = 1970;
	Calendar n� = Calendar.getInstance();
	int til�r = n�.get(Calendar.YEAR);
	Calendar fradato;
	Calendar tildato;
	
	private JButton knappen;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel knappepanel= new JPanel();
		
		JPanel �rutvalg = new JPanel();
		�rutvalg.add(new JLabel("�r"));
		�rvalg = new JComboBox(makeYearArray());
		�rvalg.addActionListener(this);
		�rutvalg.add(�rvalg);
		
		knappen = new JButton("Vis Data");
		knappen.addActionListener(this);
		
		knappepanel.add(�rutvalg);
		knappepanel.add(knappen);
		panel.add(knappepanel, BorderLayout.NORTH);
		
		utskrift = new JTextArea(25,50);
		panel.add(utskrift, BorderLayout.CENTER);
		
		return panel;
	}
	
	public String[] makeYearArray()
	{
		String[] dagarray = new String[til�r-fra�r+1];
		for(int i = fra�r; i <= til�r; i++)
		{
			dagarray[i-fra�r] = i + "";
		}
		return dagarray;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == �rvalg)
		{
			valgt�r = Integer.parseInt((String)�rvalg.getSelectedItem());
			fradato = Calendar.getInstance();
			fradato.setTimeInMillis(0);
			tildato = fradato;
			fradato.set(valgt�r, 0, 1);
			tildato.set(valgt�r+1,0,1);
		}
		else if(e.getSource() == knappen)
		{
			if(!stedliste.finnesI�r(valgt�r))
			{
				utskrift.setText("Det finnes ingen data registrert p� " +valgt�r);
			}
			else
			{
			utskrift.setText(
					"Viser Data for �r "+valgt�r+"\n\n"
					+"\t\t"+"Sted:\t"+"Fylke:\t"+"Temperatur:\t"+"Dato:"+"\n"
					+"Laveste Temperatur:\t"+stedliste.getMinTempI�r(valgt�r)+"\n"
					+"H�yeste Temperatur:\t"+stedliste.getMaxTempI�r(valgt�r)+"\n"
					+"\n\t\tSted:\t"+"Fylke:\t"+"Nedb�rsmengde:\n"
					+"Mest Nedb�r:\t\t"+stedliste.getMestNedb�rI�r(valgt�r)+"\n"
					+"Minst Nedb�r:\t\t"+stedliste.getMinstNedb�rI�r(valgt�r)+"\n"
					+"\n\t\tGjennomsnitts Temperatur\n"
					+"Snitt Minimumtemperatur:\t"+stedliste.getGjennomsnittMinTempI�r(valgt�r)+"\n"
					+"Snitt Maksimumtemperatur:\t"+stedliste.getGjennomsnittMaxTempI�r(valgt�r)
					);
			}
		}
	}
}
