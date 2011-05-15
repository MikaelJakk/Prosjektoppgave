package Meterologi.StatistikkTabs;

import java.awt.FlowLayout;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class ÅrligeEkstremer
{
	private JTextArea utskrift;
	
	private JComboBox årvalg;
	//lager streng[] for årvalg utifra maskinens kalenderår
	int fraår = 1970;
	Calendar nå = Calendar.getInstance();
	int tilår = nå.get(Calendar.YEAR);
	Calendar fradato;
	Calendar tildato;
	
	private JRadioButton maxtemp, mintemp, nedbør;

	private ButtonGroup utvalggruppe;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		utskrift = new JTextArea(25,50);
		
		panel.add(utskrift);
		
		
		return panel;
	}
}
