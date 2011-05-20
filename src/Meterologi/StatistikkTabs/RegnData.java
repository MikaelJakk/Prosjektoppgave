/*
 * Skal bli skrevet av Nam Le 19 Mai 2011....
 */

package Meterologi.StatistikkTabs;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;

import Meterologi.Lista;

public class RegnData extends Lista implements ActionListener
{
	private JTextArea utskrift;
	
	private JComboBox �rvalg;
	private JComboBox �rvalg1;
	int valgtfra�r = 1970;
	int valgttil�r = 2010;
	//lager streng[] for �rvalg utifra maskinens kalender�r
	int fra�r = 1970;
	Calendar n� = Calendar.getInstance();
	int til�r = n�.get(Calendar.YEAR);
	Calendar fradato;
	Calendar tildato;
	
	private JButton ingennedb�r;
	private JButton nedb�r;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel knappepanel= new JPanel();
		
		JPanel �rutvalg = new JPanel();
		�rutvalg.add(new JLabel("Fra �r"));
		�rvalg = new JComboBox(makeYearArray());
		�rvalg.addActionListener(this);
		�rutvalg.add(�rvalg);
		
		JPanel �rutvalg1 = new JPanel();
		�rutvalg1.add(new JLabel("Til �r"));
		�rvalg1 = new JComboBox(makeYearArray());
		�rvalg1.addActionListener(this);
		�rutvalg1.add(�rvalg1);
		
		
		ingennedb�r = new JButton("vi h�yest antall sammenhengende dager uten nedb�r");
		ingennedb�r.addActionListener(this);
		nedb�r = new JButton("vi h�yest antall sammenhengende dager med nedb�r");
		nedb�r.addActionListener(this);
		
		knappepanel.add(�rutvalg);
		knappepanel.add(�rutvalg1);
		knappepanel.add(ingennedb�r);
		knappepanel.add(nedb�r);
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
			valgtfra�r = Integer.parseInt((String)�rvalg.getSelectedItem());
			valgttil�r = Integer.parseInt((String)�rvalg1.getSelectedItem());
			fradato = Calendar.getInstance();
			fradato.setTimeInMillis(0);
			tildato = fradato;
			fradato.set(valgtfra�r, 0, 1);
			tildato.set(valgttil�r,0,1);
		}
		else if(e.getSource() == ingennedb�r)
		{
			if(!stedliste.finnesI�r(valgtfra�r))
			{
				utskrift.setText("Det finnes ingen data registrert p� " +valgtfra�r);
			}
			else
			{
				utskrift.setText(stedliste.ingenNedB�r(fradato, tildato));
			}
		}
		else if(e.getSource() == nedb�r)
		{
			if(!stedliste.finnesI�r(valgtfra�r))
			{
				utskrift.setText("Det finnes ingen data registrert p� " +valgtfra�r);
			}
			else
			{
				utskrift.setText(stedliste.ingenNedB�r(fradato, tildato));
			}
		}
	}
}
