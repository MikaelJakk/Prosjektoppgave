package Meterologi.StatistikkTabs;

import java.awt.FlowLayout;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class �rligeEkstremer
{
	private JTextArea utskrift;
	
	private JComboBox �rvalg;
	//lager streng[] for �rvalg utifra maskinens kalender�r
	int fra�r = 1970;
	Calendar n� = Calendar.getInstance();
	int til�r = n�.get(Calendar.YEAR);
	Calendar fradato;
	Calendar tildato;
	
	private JRadioButton maxtemp, mintemp, nedb�r;

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
