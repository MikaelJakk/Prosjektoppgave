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
	
	private JButton knappen;
	
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
		
		
		knappen = new JButton("Vis Data");
		knappen.addActionListener(this);
		
		knappepanel.add(�rutvalg);
		knappepanel.add(�rutvalg1);
		knappepanel.add(knappen);
		panel.add(knappepanel, BorderLayout.NORTH);
		
		utskrift = new JTextArea(25,50);
		panel.add(utskrift, BorderLayout.CENTER);
		
		return panel;
	}
	
	public boolean getDatoVerdier()
	{
		valgtfra�r = Integer.parseInt((String) �rvalg.getSelectedItem());
		valgttil�r = Integer.parseInt((String) �rvalg1.getSelectedItem());
		//m� lage test p� registrering av datoer som ikke har v�rt enn�.
		return true;
	}//end of getDatoVerdier()
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
		if(!getDatoVerdier())
			return;
		
		if(e.getSource() == �rvalg)
		{
			if(!getDatoVerdier())
				return;
			fradato = Calendar.getInstance();
			fradato.setTimeInMillis(0);
			
			fradato.set(valgtfra�r, 0, 1);
			tildato.set(valgttil�r,0,1);
		}
		else if(e.getSource() == knappen)
		{
			if(!stedliste.finnesI�r(valgtfra�r))
			{
				utskrift.setText("Det finnes ingen data registrert p� " +valgtfra�r);
			}
			else
			{

			utskrift.setText(stedliste.ingenNedB�r(valgtfra�r, valgttil�r));
			
			}
		}
	}
}
