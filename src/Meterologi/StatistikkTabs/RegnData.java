package Meterologi.StatistikkTabs;

import java.awt.FlowLayout;

import javax.swing.*;

public class RegnData 

{
	private JTextArea utskrift;
	private JComboBox velgår;
	
	public JPanel ByggPanel() //utseende
	{
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		utskrift = new JTextArea(25,50);
		
		panel.add(utskrift);
		
		
		
		return panel;
	}
	

}
