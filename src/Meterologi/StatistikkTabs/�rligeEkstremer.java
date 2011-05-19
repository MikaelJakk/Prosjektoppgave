/*
 * Skrevet av Mikael Jakhelln 5.Mai 2011
 */

package Meterologi.StatistikkTabs;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

import Meterologi.Lista;

public class ÅrligeEkstremer extends Lista implements ActionListener
{
	private JTextArea utskrift;
	
	private JComboBox årvalg;
	int valgtår = 1970;
	//lager streng[] for årvalg utifra maskinens kalenderår
	int fraår = 1970;
	Calendar nå = Calendar.getInstance();
	int tilår = nå.get(Calendar.YEAR);
	Calendar fradato;
	Calendar tildato;
	
	private JButton knappen;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel knappepanel= new JPanel();
		
		JPanel årutvalg = new JPanel();
		årutvalg.add(new JLabel("År"));
		årvalg = new JComboBox(makeYearArray());
		årvalg.addActionListener(this);
		årutvalg.add(årvalg);
		
		knappen = new JButton("Vis Data");
		knappen.addActionListener(this);
		
		knappepanel.add(årutvalg);
		knappepanel.add(knappen);
		panel.add(knappepanel, BorderLayout.NORTH);
		
		utskrift = new JTextArea(25,50);
		panel.add(utskrift, BorderLayout.CENTER);
		
		return panel;
	}
	
	public String[] makeYearArray()
	{
		String[] dagarray = new String[tilår-fraår+1];
		for(int i = fraår; i <= tilår; i++)
		{
			dagarray[i-fraår] = i + "";
		}
		return dagarray;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == årvalg)
		{
			valgtår = Integer.parseInt((String)årvalg.getSelectedItem());
			fradato = Calendar.getInstance();
			fradato.setTimeInMillis(0);
			tildato = fradato;
			fradato.set(valgtår, 0, 1);
			tildato.set(valgtår+1,0,1);
		}
		else if(e.getSource() == knappen)
		{
			if(!stedliste.finnesIÅr(valgtår))
			{
				utskrift.setText("Det finnes ingen data registrert på " +valgtår);
			}
			else
			{
			utskrift.setText(
					"Viser Data for år "+valgtår+"\n\n"
					+"\t\t"+"Sted:\t"+"Fylke:\t"+"Temperatur:\t"+"Dato:"+"\n"
					+"Laveste Temperatur:\t"+stedliste.getMinTempIÅr(valgtår)+"\n"
					+"Høyeste Temperatur:\t"+stedliste.getMaxTempIÅr(valgtår)+"\n"
					+"\n\t\tSted:\t"+"Fylke:\t"+"Nedbørsmengde:\n"
					+"Mest Nedbør:\t\t"+stedliste.getMestNedbørIÅr(valgtår)+"\n"
					+"Minst Nedbør:\t\t"+stedliste.getMinstNedbørIÅr(valgtår)+"\n"
					+"\n\t\tGjennomsnitts Temperatur\n"
					+"Snitt Minimumtemperatur:\t"+stedliste.getGjennomsnittMinTempIÅr(valgtår)+"\n"
					+"Snitt Maksimumtemperatur:\t"+stedliste.getGjennomsnittMaxTempIÅr(valgtår)
					);
			}
		}
	}
}
