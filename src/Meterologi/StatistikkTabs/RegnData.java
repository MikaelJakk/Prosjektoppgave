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
	
	private JComboBox årvalg;
	private JComboBox årvalg1;
	int valgtfraår = 1970;
	int valgttilår = 2010;
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
		årutvalg.add(new JLabel("Fra år"));
		årvalg = new JComboBox(makeYearArray());
		årvalg.addActionListener(this);
		årutvalg.add(årvalg);
		
		JPanel årutvalg1 = new JPanel();
		årutvalg1.add(new JLabel("Til år"));
		årvalg1 = new JComboBox(makeYearArray());
		årvalg1.addActionListener(this);
		årutvalg1.add(årvalg1);
		
		
		knappen = new JButton("Vis Data");
		knappen.addActionListener(this);
		
		knappepanel.add(årutvalg);
		knappepanel.add(årutvalg1);
		knappepanel.add(knappen);
		panel.add(knappepanel, BorderLayout.NORTH);
		
		utskrift = new JTextArea(25,50);
		panel.add(utskrift, BorderLayout.CENTER);
		
		return panel;
	}
	
	public boolean getDatoVerdier()
	{
		valgtfraår = Integer.parseInt((String) årvalg.getSelectedItem());
		valgttilår = Integer.parseInt((String) årvalg1.getSelectedItem());
		//må lage test på registrering av datoer som ikke har vært ennå.
		return true;
	}//end of getDatoVerdier()
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
		if(!getDatoVerdier())
			return;
		
		if(e.getSource() == årvalg)
		{
			if(!getDatoVerdier())
				return;
			fradato = Calendar.getInstance();
			fradato.setTimeInMillis(0);
			
			fradato.set(valgtfraår, 0, 1);
			tildato.set(valgttilår,0,1);
		}
		else if(e.getSource() == knappen)
		{
			if(!stedliste.finnesIÅr(valgtfraår))
			{
				utskrift.setText("Det finnes ingen data registrert på " +valgtfraår);
			}
			else
			{

			utskrift.setText(stedliste.ingenNedBør(valgtfraår, valgttilår));
			
			}
		}
	}
}
